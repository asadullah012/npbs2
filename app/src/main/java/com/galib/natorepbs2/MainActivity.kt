package com.galib.natorepbs2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.galib.natorepbs2.sync.Sync
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
            Sync.syncNoticeData(tenderInformationViewModel)
            Sync.syncNewsData(tenderInformationViewModel)
            Sync.syncJobData(tenderInformationViewModel)
            Sync.syncBankInformation(informationViewModel, assets)
            Sync.syncBanners(applicationContext)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}