package com.galib.natorepbs2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import com.galib.natorepbs2.sync.Sync
import com.galib.natorepbs2.ui.AwarenessFragment
import com.galib.natorepbs2.ui.OfficersFragment
import com.galib.natorepbs2.viewmodel.*
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope,
    NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout:DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private val TAG = "MainActivity"
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        syncIfRequired()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        updateMenu(navigationView)

        drawerLayout = findViewById(R.id.drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun updateMenu(navigationView: NavigationView) {
        val menu = navigationView.menu

        val subMenuFavorites = menu.addSubMenu(R.string.menu_favorites)
        subMenuFavorites.add(R.string.menu_awareness)
        subMenuFavorites.add(R.string.menu_officers)
        val subMenu = menu.addSubMenu(R.string.menu_settings)
        subMenu.add(R.string.menu_settings)
        subMenu.add(R.string.menu_about_app)
        navigationView.invalidate()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: ${item.title}")
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return when (item.itemId) {
            R.id.force_sync -> {
                Toast.makeText(applicationContext, "Force sync selected", Toast.LENGTH_LONG).show()
                syncUsingCoroutine()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun syncIfRequired() {
        job = launch(Dispatchers.IO) {
            val result = Sync.getLastUpdateTime()
            if(result != 0L){
                val prevVal = getLastUpdateTimeFromPref()
                Log.d(TAG, "syncIfRequired: prev value- $prevVal cur value- $result")
                if(prevVal < result){
                    setLastUpdateTimeToPref(result)
                    syncUsingCoroutine()
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

    private fun syncUsingCoroutine() {
        Log.d(TAG, "sync: sync started")
        if(job.isActive){
            Log.e(TAG, "sync: sync is running")
            return
        }
        job = launch(Dispatchers.IO) {
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
            Sync.syncBanners(applicationContext)
            Sync.syncOtherOfficerList(employeeViewModel)
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
        if(item.title == getString(R.string.menu_awareness) && fragment !is AwarenessFragment) {
            navController.navigate(R.id.awarenessFragment)
        } else if(item.title == getString(R.string.menu_officers) && fragment !is OfficersFragment){
            navController.navigate(R.id.officersFragment)
        }
        drawerLayout.closeDrawers()
        return true
    }

}