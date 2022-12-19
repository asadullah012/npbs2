package com.galib.natorepbs2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.galib.natorepbs2.sync.*
import com.galib.natorepbs2.viewmodel.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope{
    private val TAG = "MainActivity"
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    private val employeeViewModel by lazy { ViewModelProvider(this)[EmployeeViewModel::class.java] }
    private val achievementViewModel by lazy { ViewModelProvider(this)[AchievementViewModel::class.java] }
    private val informationViewModel by lazy { ViewModelProvider(this)[InformationViewModel::class.java] }
    private val complainCentreViewModel by lazy { ViewModelProvider(this)[ComplainCentreViewModel::class.java] }
    private val officeInfoViewModel  by lazy { ViewModelProvider(this)[OfficeInformationViewModel::class.java] }
    private val tenderInformationViewModel  by lazy { ViewModelProvider(this)[NoticeInformationViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        syncIfRequired()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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

                }
                syncUsingCoroutine()
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
        launch(Dispatchers.IO) {
            Sync.syncAtAGlance(informationViewModel)
            Sync.syncAchievement(achievementViewModel)
            Sync.syncComplainCentre(complainCentreViewModel)
            Sync.syncOfficerList(employeeViewModel)
            Sync.syncJuniorOfficers(employeeViewModel)
            Sync.syncBoardMember(employeeViewModel)
            Sync.syncPowerOutageContact(employeeViewModel)
            Sync.syncOfficeData(officeInfoViewModel, assets)
            Sync.syncTenderData(tenderInformationViewModel)
            Sync.syncBankInformation(informationViewModel, assets)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}