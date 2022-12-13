package com.galib.natorepbs2.sync

import android.util.Log
import com.galib.natorepbs2.constants.Selectors
import com.galib.natorepbs2.constants.URLs
import com.galib.natorepbs2.utils.Utility
import com.galib.natorepbs2.viewmodel.AchievementViewModel
import com.galib.natorepbs2.viewmodel.ComplainCentreViewModel
import com.galib.natorepbs2.viewmodel.EmployeeViewModel
import com.galib.natorepbs2.viewmodel.InformationViewModel
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

        fun syncAchievement(achievementViewModel: AchievementViewModel) {
            val data = ArrayList<ArrayList<String>>()
            try {
                val document = Jsoup.connect(URLs.BASE + URLs.ACHIEVEMENTS).get()
                val tables = document.select(Selectors.ACHIEVEMENTS)
                for (table in tables) {
                    val trs = table.select("tr")
                    for (i in trs.indices) {
                        val tds = trs[i].select("td")
                        val tdList = ArrayList<String>()
                        for (j in tds.indices) {
                            tdList.add(tds[j].text())
                        }
                        data.add(tdList)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if(data.size > 0) {
                Log.d(TAG, "syncAchievement: " + data.size)
                //achievementViewModel.deleteAllAchievements()
                achievementViewModel.insertFromArray(data as List<MutableList<String>>)
            } else{
                Log.e(TAG, "sync: unable to get achievement data")
            }
        }

        fun syncAtAGlance(informationViewModel: InformationViewModel) {
            val data = ArrayList<ArrayList<String>>()
            var month = ""
            try {
                val document = Jsoup.connect(URLs.BASE + URLs.AT_A_GLANCE).get()
                month = document.select(Selectors.AT_A_GLANCE_MONTH).text()

                val tables = document.select(Selectors.AT_A_GLANCE)
                for (table in tables) {
                    val trs = table.select("tr")
                    for (i in trs.indices) {
                        val tds = trs[i].select("td")
                        val tdList = ArrayList<String>()
                        for (j in tds.indices) {
                            tdList.add(tds[j].text())
                        }
                        data.add(tdList)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if(data.size > 0){
                Log.d(TAG, "syncAtAGlance: " + data.size)
                //informationViewModel.deleteAllByCategory(Category.atAGlance)
                informationViewModel.insertFromArray(data as List<MutableList<String>>?)
                informationViewModel.setMonth(month)
            } else{
                Log.e(TAG, "syncAtAGlance: unable to get data from website")
            }
        }

        fun syncComplainCentre(complainCentreViewModel: ComplainCentreViewModel) {
            val data = ArrayList<ArrayList<String>>()
            try {
                val document = Jsoup.connect(URLs.BASE + URLs.COMPLAIN_CENTRE).get()
                val tables = document.select(Selectors.COMPLAIN_CENTRE)
                for (table in tables) {
                    val trs = table.select("tr")
                    for (i in trs.indices) {
                        val tds = trs[i].select("td")
                        val tdList = ArrayList<String>()
                        for (j in tds.indices) {
                            tdList.add(tds[j].text())
                        }
                        data.add(tdList)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if(data.size > 0) {
                Log.d(TAG, "syncComplainCentre: " + data.size)
                //complainCentreViewModel.deleteAll()
                complainCentreViewModel.insertFromTable(data as List<MutableList<String>>)
            } else{
                Log.e(TAG, "sync: unable to get complain center data")
            }
        }

        fun syncOfficerList(employeeViewModel: EmployeeViewModel) {
            val data = ArrayList<ArrayList<String>>()
            try {
                val document = Jsoup.connect(URLs.BASE + URLs.OFFICER_LIST).get()
                val tables = document.select(Selectors.OFFICERS_LIST)
                for (table in tables) {
                    val trs = table.select("tr")
                    for (i in 1 until trs.size) {
                        val tds = trs[i].select("td")
                        val tdList = ArrayList<String>()
                        //Log.d("SyncOfficerList", "th: " + tds.select("th").html());
                        for (j in tds.indices) {
                            if (j == 0)
                                tdList.add( tds[j].select("img").first()!!.absUrl("src"))
                            else
                                tdList.add(tds[j].text())
                        }
                        if (tdList.size > 0)
                            data.add(tdList)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if(data.size > 0) {
                Log.d(TAG, "syncOfficerList: " + data.size)
                employeeViewModel.insertOfficersFromTable(data as List<MutableList<String>>)
            } else{
                Log.e(TAG, "syncOfficerList: unable to get officer data")
            }
        }

        fun syncJuniorOfficers(employeeViewModel: EmployeeViewModel) {
            val data = ArrayList<ArrayList<String>>()
            try {
                //Connect to the website
                val document = Jsoup.connect(URLs.BASE + URLs.JUNIOR_OFFICER_LIST).get()
                val tables = document.select(Selectors.JUNIOR_OFFICER_LIST)
                for (table in tables) {
                    val trs = table.select("tr")
                    for (i in trs.indices) {
                        val tds = trs[i].select("td")
                        val tdList = ArrayList<String>()
                        for (j in tds.indices) {
                            if (j == 1) if (tds[j].select("img").first() != null) tdList.add(
                                tds[j].select("img").first()!!.absUrl("src")
                            ) else tdList.add(tds[j].text()) else tdList.add(
                                tds[j].text()
                            )
                        }
                        if (tdList.size > 0) data.add(tdList)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if(data.size > 0) {
                Log.d(TAG, "syncJuniorOfficer: " + data.size)
                employeeViewModel.insertJuniorOfficerFromTable(data as List<MutableList<String>>)
            } else{
                Log.e(TAG, "syncJuniorOfficer: unable to get junior officer data")
            }
        }

        fun syncBoardMember(employeeViewModel: EmployeeViewModel) {
            val data = ArrayList<ArrayList<String>>()
            try {
                val document = Jsoup.connect(URLs.BASE + URLs.BOARD_MEMBER).get()
                val tables = document.select(Selectors.BOARD_MEMBER)
                for (table in tables) {
                    val trs = table.select("tr")
                    for (i in 1 until trs.size) {
                        val tds = trs[i].select("td")
                        val tdList = ArrayList<String>()
                        for (j in tds.indices) {
                            tdList.add(tds[j].text())
                        }
                        if (tdList.size > 0) data.add(tdList)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if(data.size > 0) {
                Log.d(TAG, "syncBoardMember: " + data.size)
                employeeViewModel.insertBoardMembersFromTable(data as List<MutableList<String>>)
            } else{
                Log.e(TAG, "syncBoardMember: unable to get board member data")
            }
        }

        fun syncPowerOutageContact(employeeViewModel: EmployeeViewModel) {
            val data = ArrayList<ArrayList<String>>()
            var headerText = ""
            var footerText = ""
            try {
                val document = Jsoup.connect(URLs.BASE + URLs.POWER_OUTAGE_CONTACT).get()
                headerText = document.select(Selectors.POWER_OUTAGE_CONTACT_HEADER).text()
                footerText = document.select(Selectors.POWER_OUTAGE_CONTACT_FOOTER).text()
                val tables = document.select(Selectors.POWER_OUTAGE_CONTACT)
                for (table in tables) {
                    val trs = table.select("tr")
                    for (i in 1 until trs.size) {
                        val tds = trs[i].select("td")
                        val tdList = ArrayList<String>()
                        for (j in tds.indices) {
                            if (j == 1) if (tds[j].select("img").first() != null) tdList.add(
                                tds[j].select("img").first()!!.absUrl("src")
                            ) else tdList.add(tds[j].text()) else tdList.add(
                                tds[j].text()
                            )
                        }
                        if (tdList.size > 0) data.add(tdList)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if(data.size > 0) {
                Log.d(TAG, "syncBoardMember: " + data.size)
                employeeViewModel.insertPowerOutageContactFromTable(data as List<MutableList<String>>, headerText, footerText)
            } else{
                Log.e(TAG, "syncBoardMember: unable to get board member data")
            }
        }
    }

}