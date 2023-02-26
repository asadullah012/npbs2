package com.galib.natorepbs2.sync

import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.constants.Category
import com.galib.natorepbs2.constants.Selectors
import com.galib.natorepbs2.constants.URLs
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.models.*
import com.galib.natorepbs2.utils.Utility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup


class Sync {
    companion object{
        private const val TAG = "Sync"

        fun getLastUpdateTime(): Long{
            var lastUpdateTime = 0L
            try {
                val document = Jsoup.connect("http://pbs2.natore.gov.bd/").get()
                val element = document.select(Selectors.LAST_UPDATE_TIME).first()
                if (element != null) {
                    lastUpdateTime = Utility.dateStringToEpoch(Utility.bnDigitToEnDigit(element.text()), "yyyy-MM-DD HH:mm:ss")
                    Log.d(TAG, "getLastUpdateTime: " + element.text() + " " + lastUpdateTime)
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
            }
            return lastUpdateTime
        }

        suspend fun syncAchievement(repository: NPBS2Repository) {
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
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if(data.size > 0) {
                Log.d(TAG, "syncAchievement: " + data.size)
                repository.deleteAllAchievements()
                val achievements: MutableList<Achievement> = ArrayList()
                for (i in data.indices) {
                    if (i == 1) continue
                    achievements.add(
                        Achievement(data[i][0]!!, data[i][1]!!, data[i][2]!!, data[i][3]!!, data[i][4]!!, i)
                    )
                }
                repository.insertAchievementAll(achievements)
            } else{
                Log.e(TAG, "sync: unable to get achievement data")
            }
        }

        suspend fun syncAtAGlance(repository: NPBS2Repository) {
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
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if(data.size > 0){
                Log.d(TAG, "syncAtAGlance: " + data.size)
                //informationViewModel.deleteAllByCategory(Category.atAGlance)
//                informationViewModel.insertFromAtAGlance(data as List<MutableList<String>>)
                val informationList: MutableList<Information> = ArrayList()
                for (i in 1 until data.size) {
                    informationList.add(
                        Information(data[i][0].toInt(), data[i][1], data[i][2], Category.atAGlance)
                    )
                }
                repository.insertInformations(informationList)
//                informationViewModel.setMonth(month)
                repository.setMonth(month)
            } else{
                Log.e(TAG, "syncAtAGlance: unable to get data from website")
            }
        }

        suspend fun syncComplainCentre(repository: NPBS2Repository) {
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
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if(data.size > 0) {
                Log.d(TAG, "syncComplainCentre: " + data.size)
                //complainCentreViewModel.deleteAll()
//                complainCentreViewModel.insertFromTable(data as List<MutableList<String>>)
                val complainCentreList: MutableList<ComplainCentre> = ArrayList()
                for (i in 1 until data.size) {
                    complainCentreList.add(ComplainCentre(data[i][0].toInt(), data[i][1], data[i][2]))
                }
                repository.insertAllComplainCentre(complainCentreList)
            } else{
                Log.e(TAG, "sync: unable to get complain center data")
            }
        }

        suspend fun syncOfficerList(repository: NPBS2Repository) {
            val data = ArrayList<ArrayList<String>>()
            try {
                val document = Jsoup.connect(URLs.BASE + URLs.OFFICER_LIST).get()
                val tables = document.select(Selectors.OFFICERS_LIST)
                for (table in tables) {
                    val trs = table.select("tr")
                    for (i in 1 until trs.size) {
                        val tds = trs[i].select("td")
                        val tdList = ArrayList<String>()
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
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if(data.size > 0) {
                Log.d(TAG, "syncOfficerList: " + data.size)
//                employeeViewModel.insertOfficersFromTable(data as List<MutableList<String>>)
                val officersList: MutableList<Employee> = ArrayList()
                for (i in data.indices) {
                    val s = data[i][2].split(", ").toTypedArray()
                    val designation = s[0]
                    val office: String = if (s.size == 1) "সদর দপ্তর" else s[1]
                    officersList.add(
                        Employee(
                            i, data[i][0], data[i][1], designation, office,
                            data[i][4], data[i][5], data[i][6], Category.OFFICERS
                        )
                    )
                    //Log.d(TAG, "insertFromTable: " + i + " " + tableData[i][0] + " " + tableData[i][1] + " " + designation + " " + office + " " + tableData[i][4] + " " + tableData[i][5] + " " + tableData[i][6] + " " + Category.OFFICERS);
                }
                repository.deleteEmployeeByType(Category.OFFICERS)
                repository.insertEmployeeList(officersList)
            } else{
                Log.e(TAG, "syncOfficerList: unable to get officer data")
            }
        }

        suspend fun syncJuniorOfficers(repository: NPBS2Repository) {
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
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if(data.size > 0) {
                Log.d(TAG, "syncJuniorOfficer: " + data.size)
//                employeeViewModel.insertJuniorOfficerFromTable(data as List<MutableList<String>>)
                val employeeList: MutableList<Employee> = ArrayList()
                var office: String? = null
                var i = 0
                while (i < data.size) {
                    //Log.d(TAG, "insertJuniorOfficerFromTable: " + Utility.arrayToString(tableData[i]));
                    if (data[i].size == 1) {
                        office = data[i][0]
                        i++ // ignore header row
                    } else employeeList.add(
                        Employee(
                            i, data[i][1], data[i][2], data[i][3],
                            office!!, data[i][4], data[i][5], "", Category.JUNIOR_OFFICER
                        )
                    )
                    i++
                }
                repository.deleteEmployeeByType(Category.JUNIOR_OFFICER)
                repository.insertEmployeeList(employeeList)
            } else{
                Log.e(TAG, "syncJuniorOfficer: unable to get junior officer data")
            }
        }

        suspend fun syncBoardMember(repository: NPBS2Repository) {
            val data = ArrayList<ArrayList<String>>()
            try {
                val document = Jsoup.connect(URLs.BASE + URLs.BOARD_MEMBER).get()
                val tables = document.select(Selectors.BOARD_MEMBER)
                for (table in tables) {
                    val trs = table.select("tr")
                    for (i in 1 until trs.size) {
                        val tds = trs[i].select("td")
                        val tdList = ArrayList<String>()
                        for (j in 1 until tds.size) {
                            if (j == 1)
                                if (tds[j].select("img").first() != null)
                                    tdList.add(tds[j].select("img").first()!!.absUrl("src"))
                                else tdList.add(tds[j].text())
                            else
                                tdList.add(tds[j].text())
                        }
                        if (tdList.size > 0) data.add(tdList)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if(data.size > 0) {
                Log.d(TAG, "syncBoardMember: " + data.size)
//                employeeViewModel.insertBoardMembersFromTable(data as List<MutableList<String>>)
                val employeeList: MutableList<Employee> = ArrayList()
                for (i in data.indices) {
                    //Log.d(TAG, "insertBoardMembersFromTable: " + Utility.arrayToString(tableData[i]));
                    employeeList.add(
                        Employee(
                            i, data[i][0], data[i][1], data[i][2],
                            data[i][3], "", data[i][4], "", Category.BOARD_MEMBER
                        )
                    )
                }
                repository.deleteEmployeeByType(Category.BOARD_MEMBER)
                repository.insertEmployeeList(employeeList)
            } else{
                Log.e(TAG, "syncBoardMember: unable to get board member data")
            }
        }

        suspend fun syncPowerOutageContact(repository: NPBS2Repository) {
            val data = ArrayList<ArrayList<String>>()
            try {
                val document = Jsoup.connect(URLs.BASE + URLs.POWER_OUTAGE_CONTACT).get()
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
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if(data.size > 0) {
                Log.d(TAG, "syncBoardMember: " + data.size)
//                employeeViewModel.insertPowerOutageContactFromTable(data as List<MutableList<String>>)
                val employeeList: MutableList<Employee> = ArrayList()
                for (i in data.indices) {
                    //Log.d(TAG, "insertPowerOutageContactFromTable: " + Utility.arrayToString(tableData[i]));
                    employeeList.add(
                        Employee(i, data[i][1], data[i][2], data[i][3],
                            "", "", data[i][4], "", Category.POWER_OUTAGE_CONTACT)
                    )
                }
                repository.deleteEmployeeByType(Category.POWER_OUTAGE_CONTACT)
                repository.insertEmployeeList(employeeList)
            } else{
                Log.e(TAG, "syncBoardMember: unable to get board member data")
            }
        }

        suspend fun syncOfficeData(repository: NPBS2Repository, context: Context){
            var json: String? = null
            try {
                val assetManager = context.assets
                val inputStream = assetManager.open("init_data.json")
                val size = withContext(Dispatchers.IO) {
                    inputStream.available()
                }
                val buffer = ByteArray(size)
                withContext(Dispatchers.IO) {
                    inputStream.read(buffer)
                }
                withContext(Dispatchers.IO) {
                    inputStream.close()
                }
                json = buffer.toString(Charsets.UTF_8)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            val data = ArrayList<OfficeInformation>()
            if(json != null){
                val jsonRootObject = JSONObject(json)
                val jsonArray: JSONArray? = jsonRootObject.optJSONArray("officeInformation")
                if(jsonArray != null){
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        data.add(
                            OfficeInformation(jsonObject.optString("name").toString(),
                            jsonObject.optString("address").toString(),
                            jsonObject.optString("mobile").toString(),
                            jsonObject.optString("telephone").toString(),
                            jsonObject.optString("email").toString(),
                            jsonObject.optString("google_map_url").toString(),
                            i)
                        )
                    }
                }
            }
            if(data.size > 0) {
                Log.d(TAG, "syncOfficeData: " + data.size)
//                officeInformationViewModel.insertAllOfficeInformation(data)
                repository.insertAllOfficeInfo(data)
            } else{
                Log.e(TAG, "syncOfficeData: unable to get office data")
            }
        }

        suspend fun syncTenderData(repository: NPBS2Repository){
            val data = ArrayList<ArrayList<String>>()
            for(page in 1..10){
                try {
                    var url = URLs.BASE + URLs.TENDER
                    if(page > 1)
                        url = "$url?page=$page"
                    val document = Jsoup.connect(url).timeout(SyncConfig.TIMEOUT).get()
                    val tables = document.select(Selectors.TENDER)
                    for (table in tables) {
                        val trs = table.select("tr")
                        for (i in 1 until trs.size) {
                            val tds = trs[i].select("td")
                            val tdList = ArrayList<String>()
                            tdList.add(tds[0].text())
                            if(tds.size > 0 && tds[0].select("a").first() != null){
                                tdList.add(tds[0].select("a").first()!!.absUrl("href"))
                            } else {
                                tdList.add("")
                            }
                            tdList.add(tds[1].text())
                            if(tds.size > 2 && tds[2].select("a").first() != null)
                                tdList.add(tds[2].select("a").first()!!.attr("href"))
                            else
                                tdList.add("")

                            if (tdList.size > 0) data.add(tdList)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    break
                }
            }
            if(data.size > 0) {
                Log.d(TAG, "syncTenderData: " + data.size)
//                tenderInformationViewModel.insertAllByCategory(data, Category.TENDER)
                insertAllNoticeByCategory(data, repository, Category.TENDER)
            } else{
                Log.e(TAG, "syncTenderData: unable to get tender data")
            }
        }

        suspend fun syncNoticeData(repository: NPBS2Repository){
            val data = ArrayList<ArrayList<String>>()
            for(page in 1..10){
                try {
                    var url = URLs.BASE + URLs.NOTICE
                    if(page > 1)
                        url = "$url?page=$page"
                    val document = Jsoup.connect(url).timeout(SyncConfig.TIMEOUT).get()
                    val tables = document.select(Selectors.NOTICE)
                    for (table in tables) {
                        val trs = table.select("tr")
                        for (i in 1 until trs.size) {
                            val tds = trs[i].select("td")
                            val tdList = ArrayList<String>()
                            tdList.add(tds[0].text())
                            if(tds.size > 0 && tds[0].select("a").first() != null){
                                tdList.add(tds[0].select("a").first()!!.absUrl("href"))
                            } else {
                                tdList.add("")
                            }
                            tdList.add(tds[1].text())
                            if(tds.size > 2 && tds[2].select("a").first() != null)
                                tdList.add(tds[2].select("a").first()!!.attr("href"))
                            else
                                tdList.add("")

                            if (tdList.size > 0) data.add(tdList)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    break
                }
            }
            if(data.size > 0) {
                Log.d(TAG, "syncNoticeData: " + data.size)
//                noticeInformationViewModel.insertAllByCategory(data, Category.NOTICE)
                insertAllNoticeByCategory(data, repository, Category.NOTICE)
            } else{
                Log.e(TAG, "syncNoticeData: unable to get notice data")
            }
        }

        suspend fun syncNewsData(repository: NPBS2Repository){
            val data = ArrayList<ArrayList<String>>()
            for(page in 1..10){
                try {
                    var url = URLs.BASE + URLs.NEWS
                    if(page > 1)
                        url = "$url?page=$page"
                    val document = Jsoup.connect(url).timeout(SyncConfig.TIMEOUT).get()
                    val tables = document.select(Selectors.NEWS)
                    for (table in tables) {
                        val trs = table.select("tr")
                        for (i in 1 until trs.size) {
                            val tds = trs[i].select("td")
                            val tdList = ArrayList<String>()
                            tdList.add(tds[0].text())
                            if(tds.size > 0 && tds[0].select("a").first() != null){
                                tdList.add(tds[0].select("a").first()!!.absUrl("href"))
                            } else {
                                tdList.add("")
                            }
                            tdList.add(tds[1].text())

                            if (tdList.size > 0) data.add(tdList)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    break
                }
            }
            if(data.size > 0) {
                Log.d(TAG, "syncNewsData: " + data.size)
//                noticeInformationViewModel.insertAllByCategory(data, Category.NEWS)
                insertAllNoticeByCategory(data, repository, Category.NEWS)
            } else{
                Log.e(TAG, "syncNewsData: unable to get news data")
            }
        }

        suspend fun syncJobData(repository: NPBS2Repository){
            val data = ArrayList<ArrayList<String>>()
            try {
                val url = URLs.BASE + URLs.JOB
                val document = Jsoup.connect(url).timeout(SyncConfig.TIMEOUT).get()
                val tables = document.select(Selectors.JOB)
                for (table in tables) {
                    val trs = table.select("tr")
                    for (i in 1 until trs.size) {
                        val tds = trs[i].select("td")
                        val tdList = ArrayList<String>()
                        tdList.add(tds[0].text())
                        if(tds.size > 0 && tds[0].select("a").first() != null){
                            tdList.add(tds[0].select("a").first()!!.absUrl("href"))
                        } else {
                            tdList.add("")
                        }
                        tdList.add(tds[1].text())
                        if(tds.size > 2 && tds[2].select("a").first() != null){
                            tdList.add(tds[2].select("a").first()!!.absUrl("href"))
                        } else {
                            tdList.add("")
                        }

                        if (tdList.size > 0) data.add(tdList)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()

            }

            if(data.size > 0) {
                Log.d(TAG, "syncJobData: " + data.size)
//                noticeInformationViewModel.insertAllByCategory(data, Category.JOB)
                insertAllNoticeByCategory(data, repository, Category.JOB)
            } else{
                Log.e(TAG, "syncJobData: unable to get job data")
            }
        }

        suspend fun syncBankInformation(repository: NPBS2Repository,context: Context){
            val json: String? = Utility.getJsonFromAssets("init_data.json", context.assets)
            val data = ArrayList<Information>()
            if(json != null){
                val jsonRootObject = JSONObject(json)
                val jsonArray: JSONArray? = jsonRootObject.optJSONArray("bankInformation")
                if(jsonArray != null) {
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        data.add(Information(i + 1, jsonObject.getString("name"), jsonObject.getString("branch"), Category.bank))
                    }
                }
            }
            if(data.size > 0) {
                Log.d(TAG, "syncBankInformation: " + data.size)
//                informationViewModel.insertAll(data)
                repository.insertInformations(data)
            } else{
                Log.e(TAG, "syncBankInformation: unable to get bank information")
            }
        }

        fun syncBanners(repository: NPBS2Repository) {
            val list:MutableList<String> = ArrayList()
            try {
                val url = URLs.BASE + URLs.BANNERS
                val document = Jsoup.connect(url).timeout(SyncConfig.TIMEOUT).get()
                val tables = document.select(Selectors.BANNERS)
                for (table in tables) {
                    val trs = table.select("tr")
                    for (i in 0 until trs.size) {
                        val tds = trs[i].select("td")
                        var bannerUrl: String? = null
                        if(tds.size > 1 && tds[0].select("img").first() != null){
                            bannerUrl = tds[0].select("img").first()?.absUrl("src")
                        }
                        if(bannerUrl != null)
                            list.add(bannerUrl)
                    }
                }
                repository.setBannerUrls(list)
            } catch (e : Exception ){
                e.printStackTrace()
            }
        }

        suspend fun syncOtherOfficeInformation(repository: NPBS2Repository, context: Context) {
            val json: String? = Utility.getJsonFromAssets("init_data.json", context.assets)
            if(json != null){
                val jsonRootObject = JSONObject(json)
                val jsonArray: JSONArray? = jsonRootObject.optJSONArray("otherOffices")
                if(jsonArray != null) {
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        syncOfficerListFromURL(jsonObject.getString("name"), jsonObject.getString("url"), repository)
                    }
                }
            }
        }

        private suspend fun syncOfficerListFromURL(officeName:String, url: String, repository: NPBS2Repository){
            val absoluteUrl = "$url/bn/site${URLs.OFFICER_LIST}"
            Log.d(TAG, "syncOfficerListFromURL: $officeName, $absoluteUrl")
            val data = ArrayList<Employee>()
            try {
                val document = Jsoup.connect(absoluteUrl).get()
                val tables = document.select(Selectors.OFFICERS_LIST)
                for (table in tables) {
                    val trs = table.select("tr")
                    for (i in 1 until trs.size) {
                        val tds = trs[i].select("td")
                        val tdList = ArrayList<String>()
                        for (j in tds.indices) {
                            if (j == 0)
                                tdList.add( tds[j].select("img").first()!!.absUrl("src"))
                            else
                                tdList.add(tds[j].text())
                        }
                        if (tdList.size > 0)
                            data.add(Employee(i, tdList[0], tdList[1], tdList[2], officeName, tdList[4], tdList[5], tdList[6], Category.OTHER_OFFICES))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if(data.size > 0) {
                Log.d(TAG, "syncOfficerListFromURL: $officeName, ${data.size}")
//                repository.insertEmployeeList(data)
                repository.insertEmployeeList(data)
            } else{
                Log.e(TAG, "syncOfficerList: unable to get officer data")
            }
        }

        suspend fun syncBREBContacts(repository: NPBS2Repository){
            val url = "https://reb.portal.gov.bd/site/view/officer_list/-"
            val data = ArrayList<Employee>()
            try {
                val document = Jsoup.connect(url).get()
                val tables = document.select("#with-pic > table")
                for (table in tables) {
                    val trs = table.select("tr")
                    for (i in 0 until trs.size) {
                        var imageUrl:String? = trs[i].select("td:nth-child(2) > img").first()?.absUrl("src")
                        var name: String? = trs[i].select("td:nth-child(3) > table > tbody > tr > td:nth-child(1) > table > tbody > tr:nth-child(1) > td:nth-child(2)").first()?.text()
                        var designation: String? = trs[i].select("td:nth-child(3) > table > tbody > tr > td:nth-child(1) > table > tbody > tr:nth-child(2) > td:nth-child(2)").first()?.text()
                        var office: String? = trs[i].select("td:nth-child(3) > table > tbody > tr > td:nth-child(1) > table > tbody > tr:nth-child(3) > td:nth-child(2)").first()?.text()
                        var email: String? = trs[i].select("td:nth-child(3) > table > tbody > tr > td:nth-child(1) > table > tbody > tr:nth-child(4) > td:nth-child(2) > a").first()?.text()
                        var mobile: String? = trs[i].select("td:nth-child(3) > table > tbody > tr > td:nth-child(2) > table > tbody > tr:nth-child(1) > td:nth-child(2)").first()?.text()
                        var phone: String? = trs[i].select("td:nth-child(3) > table > tbody > tr > td:nth-child(2) > table > tbody > tr:nth-child(2) > td:nth-child(2)").first()?.text()
                        if(imageUrl == null) imageUrl = ""
                        if(mobile == null && phone != null) mobile = phone
                        if(phone == null) phone = ""
                        if(email == null) email = ""
                        if(name != null && designation != null && office != null && mobile != null && mobile.isNotEmpty()){
                            data.add(Employee(i, imageUrl, name, designation, office, email, mobile, phone, Category.REB))
//                            Log.d(TAG, "syncBREBContacts: $imageUrl $name $designation $office $email $mobile $phone")
//                            Log.d(TAG, "syncBREBContacts: ${data.last()}")
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if(data.size > 0) {
                Log.d(TAG, "syncBREBContacts: ${data.size}")
//                employeeViewModel.insertEmployeeList(data)
                repository.insertEmployeeList(data)
            } else{
                Log.e(TAG, "syncBREBContacts: unable to get breb contacts")
            }
        }
        private suspend fun insertAllNoticeByCategory(data: ArrayList<ArrayList<String>>, repository: NPBS2Repository, category:String){
            repository.deleteAllNoticeByType(category)
            val tenderList = ArrayList<NoticeInformation>()
            for((i, d) in data.withIndex()){
//            Log.d(TAG, "insertAllTender: $i $d")
                if(d.size > 3)
                    tenderList.add(NoticeInformation(i, d[0], d[1], d[2], d[3], category))
                else
                    tenderList.add(NoticeInformation(i, d[0], d[1], d[2], "", category))
            }
            repository.insertAllNotice(tenderList)
        }
    }
}