package com.galib.natorepbs2

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.galib.natorepbs2.constants.Selectors
import com.galib.natorepbs2.sync.*
import com.galib.natorepbs2.utils.Utility
import com.galib.natorepbs2.viewmodel.AchievementViewModel
import com.galib.natorepbs2.viewmodel.ComplainCentreViewModel
import com.galib.natorepbs2.viewmodel.EmployeeViewModel
import com.galib.natorepbs2.viewmodel.InformationViewModel
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope{
    private val TAG = "MainActivity"
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        syncIfRequired()
    }

    private fun syncIfRequired() {
        job = launch(Dispatchers.IO) {
            var result = getLastUpdateTime()
            if(result != 0L){
                val prevVal = getLastUpdateTimeFromPref()
                Log.d(TAG, "syncIfRequired: prev value- " + prevVal + " cur value- " + result)
                if(prevVal < result){
                    setLastUpdateTimeToPref(result)
                    sync()
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

    private fun getLastUpdateTime(): Long{
        var lastUpdateTime = 0L
        try {
            val document = Jsoup.connect("http://pbs2.natore.gov.bd/").get()
            val element = document.select(Selectors.LAST_UPDATE_TIME).first()
            if (element != null) {
                lastUpdateTime = Utility.dateStringToEpoch(Utility.bnDigitToEnDigit(element.text()), "YYYY-MM-DD HH:mm:ss")
                Log.d(TAG, "getLastUpdateTime: " + element.text() + " " + lastUpdateTime)
            }
        } catch (e: IOException) {
            Log.e(TAG, e.message.toString())
        }
        return lastUpdateTime
    }

    fun sync() {
        Log.d(TAG, "sync: sync started")
        SyncAtAGlance(ViewModelProvider(this)[InformationViewModel::class.java]).execute()
        SyncAchievement(ViewModelProvider(this)[AchievementViewModel::class.java]).execute()
        SyncComplainCentre(ViewModelProvider(this)[ComplainCentreViewModel::class.java]).execute()
        SyncOfficerList(ViewModelProvider(this).get(EmployeeViewModel::class.java)).execute()
        SyncJuniorOfficers(ViewModelProvider(this).get(EmployeeViewModel::class.java)).execute()
        SyncBoardMember(ViewModelProvider(this).get(EmployeeViewModel::class.java)).execute()
        SyncPowerOutageContact(ViewModelProvider(this).get(EmployeeViewModel::class.java)).execute()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}