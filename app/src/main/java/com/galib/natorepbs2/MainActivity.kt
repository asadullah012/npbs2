package com.galib.natorepbs2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import com.galib.natorepbs2.sync.Sync
import com.galib.natorepbs2.ui.AboutAppFragment
import com.galib.natorepbs2.ui.AwarenessFragment
import com.galib.natorepbs2.ui.OfficersFragment
import com.galib.natorepbs2.ui.SettingsFragment
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
        setSupportActionBar(findViewById(R.id.toolbar))
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        syncIfRequired()

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
        subMenuFavorites.add(R.string.menu_awareness)
        subMenuFavorites.add(R.string.menu_officers)
        val subMenu = menu.addSubMenu(R.string.menu_others)
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
        }
        setLastUpdateTimeToPref(updatedOn)
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
        } else if(item.title == getString(R.string.menu_settings) && fragment !is SettingsFragment){
            navController.navigate(R.id.settingsFragment)
        } else if(item.title == getString(R.string.menu_about_app) && fragment !is AboutAppFragment){
            navController.navigate(R.id.aboutAppFragment)
        }
        item.isChecked = true
        drawerLayout.close()
        return true
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}