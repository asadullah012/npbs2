package com.galib.natorepbs2

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
import com.galib.natorepbs2.sync.SyncManager
import com.galib.natorepbs2.ui.*
import com.galib.natorepbs2.updater.UpdateManager
import com.galib.natorepbs2.viewmodel.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout:DrawerLayout

    var repository: NPBS2Repository? = null

    private val settingsViewModel:SettingsViewModel by viewModels {
        SettingsViewModelFactory((application as NPBS2Application).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UpdateManager.checkForUpdatesAndNotify(this)
        this.repository = (application as NPBS2Application).repository
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

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
                if(!SyncManager.isSyncRunning()) {
                    showSyncDialog(false)
                }
                else {
                    showSyncDialog(true)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSyncDialog(isSyncRunning: Boolean) {
        val syncDialog = MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_App_MaterialAlertDialog)
            .setTitle(resources.getString(R.string.force_sync))
            .setMessage(if (isSyncRunning) resources.getString(R.string.sync_running) else resources.getString(R.string.sync_confirmation))
        if(isSyncRunning)
            syncDialog.setPositiveButton(resources.getString(R.string.dismiss)) { dialog, _ ->
//                Toast.makeText(applicationContext, "Sync is already running", Toast.LENGTH_LONG)
//                    .show()
                dialog.dismiss()
            }
        else {
            syncDialog.setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }.setPositiveButton(resources.getString(R.string.confirm)) { dialog, _ ->
                SyncManager.startSync(applicationContext, (application as NPBS2Application).repository, true)
                Toast.makeText(applicationContext, "Force sync selected", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
        }
        syncDialog.show()
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