package com.galib.natorepbs2

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.galib.natorepbs2.sync.*
import com.galib.natorepbs2.viewmodel.AchievementViewModel
import com.galib.natorepbs2.viewmodel.ComplainCentreViewModel
import com.galib.natorepbs2.viewmodel.EmployeeViewModel
import com.galib.natorepbs2.viewmodel.InformationViewModel
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        syncIfRequired()
    }

    private fun syncIfRequired() {
        job = launch(Dispatchers.IO) {
            var result = Sync.getLastUpdateTime()
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

    fun syncUsingCoroutine() {
        Log.d(TAG, "sync: sync started")
        launch(Dispatchers.IO) {
            Sync.syncAtAGlance(informationViewModel)
            Sync.syncAchievement(achievementViewModel)
            Sync.syncComplainCentre(complainCentreViewModel)
            Sync.syncOfficerList(employeeViewModel)
            Sync.syncJuniorOfficers(employeeViewModel)
            Sync.syncBoardMember(employeeViewModel)
            Sync.syncPowerOutageContact(employeeViewModel)
        }
    }

//    fun sync(){
//        SyncAtAGlance(informationViewModel).execute()
//        SyncAchievement(achievementViewModel).execute()
//        SyncComplainCentre(complainCentreViewModel).execute()
//        SyncOfficerList(employeeViewModel).execute()
//        SyncJuniorOfficers(employeeViewModel).execute()
//        SyncBoardMember(employeeViewModel).execute()
//        SyncPowerOutageContact(employeeViewModel).execute()
//    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}