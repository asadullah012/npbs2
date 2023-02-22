package com.galib.natorepbs2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.models.MyMenuItem
import com.galib.natorepbs2.sync.Sync
import com.galib.natorepbs2.ui.*
import com.galib.natorepbs2.utils.Utility
import com.galib.natorepbs2.viewmodel.*
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope,
    NavigationView.OnNavigationItemSelectedListener {
    private var syncJob: Job? = null
    private lateinit var drawerLayout:DrawerLayout
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    var repository: NPBS2Repository? = null

    private val employeeViewModel: EmployeeViewModel by viewModels {
        EmployeeViewModelFactory((application as NPBS2Application).repository)
    }
    private val achievementViewModel: AchievementViewModel by viewModels {
        AchievementViewModelFactory((application as NPBS2Application).repository)
    }
    private val informationViewModel: InformationViewModel by viewModels {
        InformationViewModelFactory((application as NPBS2Application).repository)
    }
    private val complainCentreViewModel: ComplainCentreViewModel by viewModels {
        ComplainCentreViewModelFactory((application as NPBS2Application).repository)
    }
    private val officeInfoViewModel: OfficeInformationViewModel by viewModels {
        OfficeViewModelFactory((application as NPBS2Application).repository)
    }
    private val tenderInformationViewModel: NoticeInformationViewModel by viewModels {
        NoticeViewModelFactory((application as NPBS2Application).repository)
    }
    private val settingsViewModel:SettingsViewModel by viewModels {
        SettingsViewModelFactory((application as NPBS2Application).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.repository = (application as NPBS2Application).repository
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        syncIfRequired()
        checkForMyMenuItems()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        updateMenu(navigationView)

        drawerLayout = findViewById(R.id.drawer_layout)
        val drawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        val callback = onBackPressedDispatcher.addCallback(this, false) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerOpened(drawerView: View) {
                callback.isEnabled = true
            }
            override fun onDrawerClosed(drawerView: View) {
                callback.isEnabled = false
            }
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) = Unit
            override fun onDrawerStateChanged(newState: Int) = Unit
        })
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun updateMenu(navigationView: NavigationView) {
        val menu = navigationView.menu
        val subMenuFavorites = menu.addSubMenu(R.string.menu_favorites)
        updateSubMenus(subMenuFavorites)
        val subMenu = menu.addSubMenu(R.string.menu_others)
        subMenu.add(R.string.menu_settings)
        subMenu.add(R.string.menu_about_app)
        navigationView.invalidate()
    }

    private fun updateSubMenus(subMenuFavorites: SubMenu?) {
        if(subMenuFavorites == null) return
        if(repository != null){
            repository!!.favoriteMenu.asLiveData().observe(this) { menuList ->
                menuList?.let {
                    subMenuFavorites.clear()
                    for(i in it){
                        subMenuFavorites.add(i.name)
                    }
                }
            }
        } else{
            Log.e(TAG, "updateSubMenus: repository is null")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: ${item.title}")
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            R.id.force_sync -> {
                if(syncJob == null || !syncJob!!.isActive)
                    Toast.makeText(applicationContext, "Force sync selected", Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(applicationContext, "Sync is already running", Toast.LENGTH_LONG).show()
                syncUsingCoroutine(System.currentTimeMillis())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun syncIfRequired() {
        launch(Dispatchers.IO) {
            val result = Sync.getLastUpdateTime()
            if(result != 0L){
                val prevVal = getLastUpdateTimeFromPref()
                Log.d(TAG, "syncIfRequired: prev value- $prevVal cur value- $result")
                if(prevVal < result){
                    syncUsingCoroutine(result)
                }
            } else{
                Log.d(TAG, "syncIfRequired: unable to get last updated time")
            }
        }
    }

    private fun setLastUpdateTimeToPref(result: Long) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putLong(getString(R.string.last_update_time), result)
            apply()
        }
    }

    private fun getLastUpdateTimeFromPref(): Long {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getLong(getString(R.string.last_update_time), 0L)
    }

    private fun syncUsingCoroutine(updatedOn: Long) {
        if(syncJob != null && syncJob!!.isActive){
            Log.e(TAG, "sync: sync is running")
            return
        }
        Log.d(TAG, "sync: sync started")
        syncJob = launch(Dispatchers.IO) {
            Utility.downloadContent()
            Sync.syncAtAGlance(informationViewModel)
            Sync.syncAchievement(achievementViewModel)
            Sync.syncComplainCentre(complainCentreViewModel)
            Sync.syncOfficerList(employeeViewModel)
            Sync.syncJuniorOfficers(employeeViewModel)
            Sync.syncBoardMember(employeeViewModel)
            Sync.syncPowerOutageContact(employeeViewModel)
            Sync.syncOfficeData(officeInfoViewModel, assets)
            Sync.syncTenderData(tenderInformationViewModel)
            Sync.syncNoticeData(tenderInformationViewModel)
            Sync.syncNewsData(tenderInformationViewModel)
            Sync.syncJobData(tenderInformationViewModel)
            Sync.syncBankInformation(informationViewModel, assets)
            Sync.syncOtherOfficeInformation(employeeViewModel, assets)
            Sync.syncBREBContacts(employeeViewModel)
            setLastUpdateTimeToPref(updatedOn)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onNavigationItemSelected: ${item.title}")

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val fragment = navHostFragment.childFragmentManager.fragments[0]
        navigateToMenu(item.title.toString(), fragment, navController)
        item.isChecked = true
        drawerLayout.close()
        return true
    }

    private fun navigateToMenu(title: String, fragment:Fragment, navController:NavController) {
        when {
            title == getString(R.string.menu_home) && fragment !is MainFragment -> {
                navController.navigate(R.id.mainFragment)
            }
            title == getString(R.string.menu_awareness) && fragment !is AwarenessFragment -> {
                navController.navigate(R.id.awarenessFragment)
            }
            title == getString(R.string.menu_officers) && fragment !is OfficersFragment -> {
                navController.navigate(R.id.officersFragment)
            }
            title == getString(R.string.menu_complain_centres) && fragment !is ComplainCentreFragment -> {
                navController.navigate(R.id.complainCentreFragment)
            }
            title == getString(R.string.menu_bill_from_home) && fragment !is BillFromHomeFragment -> {
                navController.navigate(R.id.billFromHomeFragment)
            }
            title == getString(R.string.menu_communication) && fragment !is CommunicationFragment -> {
                navController.navigate(R.id.communicationFragment)
            }
            title == getString(R.string.menu_junior_officers) && fragment !is JuniorOfficerFragment -> {
                navController.navigate(R.id.juniorOfficerFragment)
            }
            title == getString(R.string.menu_electricity_connection) && fragment !is ElectricityConnectionFragment -> {
                navController.navigate(R.id.electricityConnectionFragment)
            }
            title == getString(R.string.menu_notice) && fragment !is NoticeInformationFragment -> {
                val bundle = bundleOf("title" to getString(R.string.menu_notice))
                navController.navigate(R.id.noticeInformationFragment, bundle)
            }
            title == getString(R.string.menu_settings) && fragment !is SettingsFragment -> {
                navController.navigate(R.id.settingsFragment)
            }
            title == getString(R.string.menu_about_app) && fragment !is AboutAppFragment -> {
                navController.navigate(R.id.aboutAppFragment)
            }
        }
    }

    private fun checkForMyMenuItems() {
        val menuList = listOf(
            MyMenuItem(getString(R.string.menu_home), true),
            MyMenuItem(getString(R.string.menu_officers), true),
            MyMenuItem(getString(R.string.menu_junior_officers), false),
            MyMenuItem(getString(R.string.menu_complain_centres),true),
            MyMenuItem(getString(R.string.menu_bill_from_home), false),
            MyMenuItem(getString(R.string.menu_electricity_connection),true),
            MyMenuItem(getString(R.string.menu_notice), true),
            MyMenuItem(getString(R.string.menu_communication), true),
            MyMenuItem(getString(R.string.menu_awareness),  true)
        )
        val count = settingsViewModel.getMyMenuCount()
        Log.d(TAG, "checkForMyMenuItems: $count ${menuList.size}")
        if(count != menuList.size){
            settingsViewModel.addMenus(menuList)
        }

    }

    companion object {
        private const val TAG = "MainActivity"
    }

}