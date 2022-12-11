package com.galib.natorepbs2.sync

import android.util.Log
import com.galib.natorepbs2.constants.Selectors
import com.galib.natorepbs2.constants.URLs
import com.galib.natorepbs2.utils.Utility
import org.jsoup.Jsoup
import java.io.IOException

class Sync {
    companion object{
        private const val TAG = "Sync"
        fun getLastUpdateTime(): Long{
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

        @JvmStatic
        fun syncAchievement() : ArrayList<ArrayList<String>> {
            var trtd = ArrayList<ArrayList<String>>()
            try {
                val document = Jsoup.connect(URLs.BASE + URLs.ACHIEVEMENTS).get()
                val tables = document.select(Selectors.ACHIEVEMENTS)
                for (table in tables) {
                    val trs = table.select("tr")
                    for (i in trs.indices) {
                        val tds = trs[i].select("td")
                        var tdList = ArrayList<String>()
                        for (j in tds.indices) {
                            tdList.add(tds[j].text())
                        }
                        trtd.add(tdList)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return trtd
        }
    }

}