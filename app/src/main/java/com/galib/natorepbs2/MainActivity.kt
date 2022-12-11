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
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope{
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        syncIfRequired()
    }

    private fun syncIfRequired() {
        job = launch(Dispatchers.IO) {
            val result = withContext(Dispatchers.Default) {
                getLastUpdateTime()
            }

        }
//        val sharedPref = getPreferences(Context.MODE_PRIVATE)
//        with (sharedPref.edit()) {
//            putInt(getString(R.string.saved_high_score_key), newHighScore)
//            apply()
//        }
//        val defaultValue = resources.getInteger(R.integer.saved_high_score_default_key)
//        val highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), defaultValue)
    }

    private fun getLastUpdateTime(){
        var lastUpdateTime:String
        try {
            val document = Jsoup.connect("http://pbs2.natore.gov.bd/").get()
            val element = document.select("#footer-menu > div > span").first()
            if (element != null) {
                lastUpdateTime = element.text()
                Log.d("MainActivity", "getLastUpdateTime: " + element.text())
            }
        } catch (e: IOException) {
            Log.e("MainActivity", e.message.toString())
        }
    }

    fun sync() {
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