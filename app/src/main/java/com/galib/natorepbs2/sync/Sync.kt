package com.galib.natorepbs2.sync

import android.content.Context
import com.galib.natorepbs2.constants.Category
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.logger.LogUtils
import com.galib.natorepbs2.models.AccountByCC
import com.galib.natorepbs2.models.Achievement
import com.galib.natorepbs2.models.ComplainCentre
import com.galib.natorepbs2.models.Employee
import com.galib.natorepbs2.models.Information
import com.galib.natorepbs2.models.NoticeInformation
import com.galib.natorepbs2.models.OfficeInformation
import com.galib.natorepbs2.utils.Utility
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Connection
import org.jsoup.Jsoup


object Sync {
    private const val TAG = "Sync"

    fun getLastUpdateTime(context: Context): Long{
        var lastUpdateTime = 0L
        val url = SyncConfig.getUrl("NPBS2", context)
        val selector = SyncConfig.getSelector("LAST_UPDATE_TIME", context)
        if(url.isEmpty() || selector.isEmpty()) {
            LogUtils.e(TAG, "getLastUpdateTime: empty Url or selector $url $selector")
            return 0L
        }
        try {
            val document = Jsoup.connect(url).get()
            val element = document.select(selector).first()
            if (element != null) {
                lastUpdateTime = Utility.dateStringToEpoch(Utility.bnDigitToEnDigit(element.text()), "yyyy-MM-DD HH:mm:ss")
                LogUtils.d(TAG, "getLastUpdateTime: " + element.text() + " " + lastUpdateTime)
            }
        } catch (e: Exception) {
            LogUtils.e(TAG, e.message.toString())
        }
        return lastUpdateTime
    }

    suspend fun syncImportantNotice(repository: NPBS2Repository, context: Context){
        val url = SyncConfig.getUrl("NPBS2", context)
        val selector = SyncConfig.getSelector("IMPORTANT_NOTICE", context)
        if(url.isEmpty() || selector.isEmpty()) {
            LogUtils.e(TAG, "syncImportantNotice: empty Url or selector $url $selector")
            return
        }
        try {
            val document = Jsoup.connect(url).get()
            val importantNotice = document.select(selector).first()
            if (importantNotice != null) {
                repository.setImportantNotice(importantNotice.text())
                LogUtils.d(TAG, "syncImportantNotice: " + importantNotice.text())
            }
        } catch (e: Exception) {
            LogUtils.e(TAG, e.message.toString())
        }
    }

    suspend fun syncAchievement(repository: NPBS2Repository, context: Context) {
        val url = SyncConfig.getUrl("BASE", context) + SyncConfig.getUrl("ACHIEVEMENTS", context)
        val selector = SyncConfig.getSelector("ACHIEVEMENTS", context)
        if(url.isEmpty() || selector.isEmpty()) {
            LogUtils.e(TAG, "syncAchievement: empty Url or selector $url $selector")
            return
        }
        val data = ArrayList<ArrayList<String>>()
        try {
            val document = Jsoup.connect(url).get()
            val tables = document.select(selector)
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
//            throw SyncException("Sync achievement failed $url $selector")
        }
        if(data.size > 0) {
            LogUtils.d(TAG, "syncAchievement: " + data.size)
            repository.deleteAllAchievements()
            val achievements: MutableList<Achievement> = ArrayList()
            for (i in data.indices) {
                if (i == 1) continue
                achievements.add(
                    Achievement(data[i][0], data[i][1], data[i][2], data[i][3], data[i][4], i)
                )
            }
            repository.insertAchievementAll(achievements)
        } else{
            LogUtils.e(TAG, "sync: unable to get achievement data $url")
//            throw SyncException("Sync achievement failed $url $selector")
        }

    }

    suspend fun syncAtAGlance(repository: NPBS2Repository, context: Context) {
        val url = SyncConfig.getUrl("BASE", context) + SyncConfig.getUrl("AT_A_GLANCE", context)
        val selector = SyncConfig.getSelector("AT_A_GLANCE", context)
        val selectorMonth = SyncConfig.getSelector("AT_A_GLANCE_MONTH", context)
        if(url.isEmpty() || selector.isEmpty() || selectorMonth.isEmpty()) {
            LogUtils.e(TAG, "syncAtAGlance: Invalid urls $url $selector $selectorMonth")
            return
        }
        val data = ArrayList<ArrayList<String>>()
        var month = ""
        try {
            val document = Jsoup.connect(url).get()
            month = document.select(selectorMonth).text()

            val tables = document.select(selector)
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
            LogUtils.d(TAG, "syncAtAGlance: " + data.size)
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
            LogUtils.e(TAG, "syncAtAGlance: unable to get data from website $url")
        }
    }

    suspend fun syncComplainCentre(repository: NPBS2Repository, context: Context) {
        val url = "https://api.github.com/gists/75616e5027e4d1942bddbcf489da17d2"
        val complainCentreList: MutableList<ComplainCentre> = ArrayList()
        try {
            val response: Connection.Response = Jsoup.connect(url).ignoreContentType(true).execute()
            val jsonRootObject = JSONObject(response.body())
            val content = jsonRootObject.optJSONObject("files")?.optJSONObject("complain_center_description.json")
                ?.getString("content")
            val jsonArray = JSONArray(content)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.getString("name")
                val address = jsonObject.getString("address")
                val mobile = jsonObject.getString("mobileNo")
                val telephone = jsonObject.getString("telephone_no")
                val email = jsonObject.getString("email")
                val latitude = jsonObject.getDouble("latitude")
                val longitude = jsonObject.getDouble("longitude")

                val complainCentre = ComplainCentre(
                    priority = i,
                    name = name,
                    address = address,
                    mobileNo = mobile,
                    telephoneNo = telephone,
                    email = email,
                    latitude = latitude,
                    longitude = longitude
                )
                complainCentreList.add(complainCentre)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if(complainCentreList.size > 0) {
            LogUtils.d(TAG, "syncComplainCentre: " + complainCentreList.size)
            repository.deleteAllComplainCentre()
            repository.insertAllComplainCentre(complainCentreList)
        } else{
            LogUtils.e(TAG, "syncComplainCentre: unable to get complain center data  $url")
        }
    }

    suspend fun syncAccountByCC(repository: NPBS2Repository, context: Context) {
        val url = "https://api.github.com/gists/c144cf50ee1e50be949e30671901fc39"
        val accountByCCList: MutableList<AccountByCC> = ArrayList()
        try {
            val response: Connection.Response = Jsoup.connect(url).ignoreContentType(true).execute()
            val jsonRootObject = JSONObject(response.body())
            val content = jsonRootObject.optJSONObject("files")?.optJSONObject("acc_no_complain_center.json")
                ?.getString("content")
            val jsonArray = JSONArray(content)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val ccName = jsonObject.getString("complain_center")
                val zoneCode = jsonObject.getInt("zone_code")
                val completeBlocks = jsonObject.getJSONArray("complete_blocks")
                for(j in 0 until  completeBlocks.length()){
                    val block = String.format("%03d", completeBlocks.getInt(j))
                    accountByCCList.add(AccountByCC(ccName, "$zoneCode$block"))
                }
                val partialBlocks = jsonObject.getJSONArray("partial_blocks")
                for(j in 0 until  partialBlocks.length()){
                    val block = String.format("%03d", partialBlocks.getJSONObject(j).getInt("block"))
                    val accounts = partialBlocks.getJSONObject(j).getJSONArray("acc_no")
                    for(k in 0 until accounts.length()) {
                        val account = String.format("%04d", accounts.getInt(k))
                        accountByCCList.add(AccountByCC(ccName, "$zoneCode$block$account"))
                    }
                }
                LogUtils.d(TAG, "syncAccountByCC: $accountByCCList")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if(accountByCCList.size > 0) {
            LogUtils.d(TAG, "syncAccountByCC: " + accountByCCList.size)
            repository.deleteAllAccountByCC()
            repository.insertAllAccountByCC(accountByCCList)
        } else{
            LogUtils.e(TAG, "syncAccountByCC: unable to get account by cc data  $url")
        }
    }

    suspend fun syncOfficerList(repository: NPBS2Repository, context: Context) {
        val url = SyncConfig.getUrl("BASE", context) + SyncConfig.getUrl("OFFICER_LIST", context)
        val selector = SyncConfig.getSelector("OFFICERS_LIST", context)
        if(url.isEmpty() || selector.isEmpty()) {
            LogUtils.e(TAG, "syncOfficerList: empty Url or selector $url $selector")
            return
        }
        val data = ArrayList<ArrayList<String>>()
        try {
            val document = Jsoup.connect(url).get()
            val tables = document.select(selector)
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
            LogUtils.d(TAG, "syncOfficerList: " + data.size)
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
                //LogUtils.d(TAG, "insertFromTable: " + i + " " + tableData[i][0] + " " + tableData[i][1] + " " + designation + " " + office + " " + tableData[i][4] + " " + tableData[i][5] + " " + tableData[i][6] + " " + Category.OFFICERS);
            }
            repository.deleteEmployeeByType(Category.OFFICERS)
            repository.insertEmployeeList(officersList)
        } else{
            LogUtils.e(TAG, "syncOfficerList: unable to get officer data  $url")
        }
    }

    suspend fun syncJuniorOfficers(repository: NPBS2Repository, context: Context) {
        val url = SyncConfig.getUrl("BASE", context) + SyncConfig.getUrl("JUNIOR_OFFICER_LIST", context)
        val selector = SyncConfig.getSelector("JUNIOR_OFFICER_LIST", context)
        if(url.isEmpty() || selector.isEmpty()) {
            LogUtils.e(TAG, "syncJuniorOfficers: empty Url or selector $url $selector")
            return
        }
        val data = ArrayList<ArrayList<String>>()
        try {
            //Connect to the website
            val document = Jsoup.connect(url).get()
            val tables = document.select(selector)
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
            LogUtils.d(TAG, "syncJuniorOfficer: " + data.size)
//                employeeViewModel.insertJuniorOfficerFromTable(data as List<MutableList<String>>)
            val employeeList: MutableList<Employee> = ArrayList()
            var office: String? = null
            var i = 0
            while (i < data.size) {
                //LogUtils.d(TAG, "insertJuniorOfficerFromTable: " + Utility.arrayToString(tableData[i]));
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
            LogUtils.e(TAG, "syncJuniorOfficer: unable to get junior officer data $url")
        }
    }

    suspend fun syncBoardMember(repository: NPBS2Repository, context: Context) {
        val url = SyncConfig.getUrl("BASE", context) + SyncConfig.getUrl("BOARD_MEMBER", context)
        val selector = SyncConfig.getSelector("BOARD_MEMBER", context)
        if(url.isEmpty() || selector.isEmpty()) {
            LogUtils.e(TAG, "syncBoardMember: empty Url or selector $url $selector")
            return
        }
        val data = ArrayList<ArrayList<String>>()
        try {
            val document = Jsoup.connect(url).get()
            val tables = document.select(selector)
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
            LogUtils.d(TAG, "syncBoardMember: " + data.size)
//                employeeViewModel.insertBoardMembersFromTable(data as List<MutableList<String>>)
            val employeeList: MutableList<Employee> = ArrayList()
            for (i in data.indices) {
                //LogUtils.d(TAG, "insertBoardMembersFromTable: " + Utility.arrayToString(tableData[i]));
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
            LogUtils.e(TAG, "syncBoardMember: unable to get board member data  $url")
        }
    }

    suspend fun syncPowerOutageContact(repository: NPBS2Repository, context: Context) {
        val url = SyncConfig.getUrl("BASE", context) + SyncConfig.getUrl("POWER_OUTAGE_CONTACT", context)
        val selector = SyncConfig.getSelector("POWER_OUTAGE_CONTACT", context)
        if(url.isEmpty() || selector.isEmpty()) {
            LogUtils.e(TAG, "syncPowerOutageContact: empty Url or selector $url $selector")
            return
        }
        val data = ArrayList<ArrayList<String>>()
        try {
            val document = Jsoup.connect(url).get()
            val tables = document.select(selector)
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
            LogUtils.d(TAG, "syncBoardMember: " + data.size)
//                employeeViewModel.insertPowerOutageContactFromTable(data as List<MutableList<String>>)
            val employeeList: MutableList<Employee> = ArrayList()
            for (i in data.indices) {
                //LogUtils.d(TAG, "insertPowerOutageContactFromTable: " + Utility.arrayToString(tableData[i]));
                employeeList.add(
                    Employee(i, data[i][1], data[i][2], data[i][3],
                        "", "", data[i][4], "", Category.POWER_OUTAGE_CONTACT)
                )
            }
            repository.deleteEmployeeByType(Category.POWER_OUTAGE_CONTACT)
            repository.insertEmployeeList(employeeList)
        } else{
            LogUtils.e(TAG, "syncBoardMember: unable to get board member data  $url")
        }
    }

    suspend fun syncOfficeData(repository: NPBS2Repository, context: Context){
        val jsonRootObject = SyncConfig.getSyncDataJson(context)
        val data = ArrayList<OfficeInformation>()
        if(jsonRootObject != null){
            val jsonArray: JSONArray? = jsonRootObject.optJSONArray("office_information_table")
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
            LogUtils.d(TAG, "syncOfficeData: " + data.size)
//                officeInformationViewModel.insertAllOfficeInformation(data)
            repository.insertAllOfficeInfo(data)
        } else{
            LogUtils.e(TAG, "syncOfficeData: unable to get office data")
//            throw SyncException("office_information_table")
        }
    }

    suspend fun syncTenderData(repository: NPBS2Repository, context: Context) {
        val url = SyncConfig.getUrl("BASE", context) + SyncConfig.getUrl("TENDER", context)
        val selector = SyncConfig.getSelector("TENDER", context)
        if (url.isEmpty() || selector.isEmpty()) {
            LogUtils.e(TAG, "syncTenderData: $url $selector")
            return
        }
        val data = ArrayList<ArrayList<String>>()
        for (page in 1..10) {
            try {
                val document = Jsoup.connect(if (page == 1) url else "$url?page=$page")
                    .timeout(SyncConfig.TIMEOUT).get()
                val tables = document.select(selector)
                for (table in tables) {
                    val trs = table.select("tr")
                    for (i in 1 until trs.size) {
                        val tds = trs[i].select("td")
                        val tdList = ArrayList<String>()
                        tdList.add(tds[0].text())
                        if (tds.size > 0 && tds[0].select("a").first() != null) {
                            tdList.add(tds[0].select("a").first()!!.absUrl("href"))
                        } else {
                            tdList.add("")
                        }
                        tdList.add(tds[1].text())
                        if (tds.size > 2 && tds[2].select("a").first() != null)
                            tdList.add(tds[2].select("a").first()!!.absUrl("href"))
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
        if (data.size > 0) {
            LogUtils.d(TAG, "syncTenderData: ${data.size}")
//                tenderInformationViewModel.insertAllByCategory(data, Category.TENDER)
            insertAllNoticeByCategory(data, repository, Category.TENDER)
        } else {
            LogUtils.e(TAG, "syncTenderData: unable to get tender data $url")
        }
    }

    suspend fun syncNoticeData(repository: NPBS2Repository, context: Context){
        val url = SyncConfig.getUrl("BASE", context) + SyncConfig.getUrl("NOTICE", context)
        val selector = SyncConfig.getSelector("NOTICE", context)
        if(url.isEmpty() || selector.isEmpty()) {
            LogUtils.e(TAG, "syncNoticeData: empty Url or selector $url $selector")
            return
        }
        val data = ArrayList<ArrayList<String>>()
        for(page in 1..10){
            try {
                val document = Jsoup.connect(if (page == 1) url else "$url?page=$page").timeout(SyncConfig.TIMEOUT).get()
                val tables = document.select(selector)
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
                            tdList.add(tds[2].select("a").first()!!.absUrl("href"))
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
            LogUtils.d(TAG, "syncNoticeData: " + data.size)
//                noticeInformationViewModel.insertAllByCategory(data, Category.NOTICE)
            insertAllNoticeByCategory(data, repository, Category.NOTICE)
        } else{
            LogUtils.e(TAG, "syncNoticeData: unable to get notice data $url")
        }
    }

    suspend fun syncNewsData(repository: NPBS2Repository, context: Context){
        val url = SyncConfig.getUrl("BASE", context) + SyncConfig.getUrl("NEWS", context)
        val selector = SyncConfig.getSelector("NEWS", context)
        if(url.isEmpty() || selector.isEmpty()) {
            LogUtils.e(TAG, "syncNewsData: empty Url or selector $url $selector")
            return
        }
        val data = ArrayList<ArrayList<String>>()
        for(page in 1..10){
            try {
                val document = Jsoup.connect(if (page == 1) url else "$url?page=$page").timeout(SyncConfig.TIMEOUT).get()
                val tables = document.select(selector)
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
            LogUtils.d(TAG, "syncNewsData: " + data.size)
//                noticeInformationViewModel.insertAllByCategory(data, Category.NEWS)
            insertAllNoticeByCategory(data, repository, Category.NEWS)
        } else{
            LogUtils.e(TAG, "syncNewsData: unable to get news data $url")
        }
    }

    suspend fun syncJobData(repository: NPBS2Repository, context: Context){
        val url = SyncConfig.getUrl("BASE", context) + SyncConfig.getUrl("JOB", context)
        val selector = SyncConfig.getSelector("JOB", context)
        if(url.isEmpty() || selector.isEmpty()) {
            LogUtils.e(TAG, "syncJobData: empty Url or selector $url $selector")
            return
        }
        val data = ArrayList<ArrayList<String>>()
        try {
            val document = Jsoup.connect(url).timeout(SyncConfig.TIMEOUT).get()
            val tables = document.select(selector)
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
            LogUtils.d(TAG, "syncJobData: " + data.size)
//                noticeInformationViewModel.insertAllByCategory(data, Category.JOB)
            insertAllNoticeByCategory(data, repository, Category.JOB)
        } else{
            LogUtils.e(TAG, "syncJobData: unable to get job data $url")
        }
    }

    suspend fun syncBankInformation(repository: NPBS2Repository,context: Context){
        val jsonRootObject = SyncConfig.getSyncDataJson(context)
        val data = ArrayList<Information>()
        if(jsonRootObject != null){
            val jsonArray: JSONArray? = jsonRootObject.optJSONArray("bankInformation")
            if(jsonArray != null) {
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    data.add(Information(i + 1, jsonObject.getString("name"), jsonObject.getString("branch"), Category.bank))
                }
            }
        }
        if(data.size > 0) {
            LogUtils.d(TAG, "syncBankInformation: " + data.size)
//                informationViewModel.insertAll(data)
            repository.insertInformations(data)
        } else{
            LogUtils.e(TAG, "syncBankInformation: unable to get bank information")
        }
    }

    suspend fun syncBanners(repository: NPBS2Repository, context: Context) {
        val url = SyncConfig.getUrl("BASE", context) + SyncConfig.getUrl("BANNERS", context)
        val selector = SyncConfig.getSelector("BANNERS", context)
        if(url.isEmpty() || selector.isEmpty()) {
            LogUtils.e(TAG, "syncBanners: empty Url or selector $url $selector")
            return
        }
        val list:MutableList<String> = ArrayList()
        try {
            val document = Jsoup.connect(url).timeout(SyncConfig.TIMEOUT).get()
            val tables = document.select(selector)
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
            if(list.size > 0){
                repository.setBannerUrls(list)
            } else {
                LogUtils.e(TAG, "unable to get banner image $url $selector")
            }
        } catch (e : Exception ){
            e.printStackTrace()
        }
    }

    suspend fun syncOtherOfficeInformation(repository: NPBS2Repository, context: Context) {
        val jsonRootObject = SyncConfig.getSyncDataJson(context)
        if(jsonRootObject != null){
            val jsonArray: JSONArray? = jsonRootObject.optJSONArray("otherOffices")
            if(jsonArray != null) {
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    syncOfficerListFromURL(jsonObject.getString("name"), jsonObject.getString("url"), repository, context)
                }
            }
        }
    }

    private suspend fun syncOfficerListFromURL(officeName:String, officeUrl: String, repository: NPBS2Repository, context: Context){
        val url = SyncConfig.getUrl("OFFICER_LIST", context)
        val selector = SyncConfig.getSelector("OFFICERS_LIST", context)
        if(url.isEmpty() || selector.isEmpty() || officeUrl.isEmpty()){
            LogUtils.e(TAG, "syncOfficerListFromURL: Invalid urls $url $selector $officeUrl")
            return
        }
        val absoluteUrl = "$officeUrl/bn/site$url"
        LogUtils.d(TAG, "syncOfficerListFromURL: $officeName, $absoluteUrl")
        val data = ArrayList<Employee>()
        try {
            val document = Jsoup.connect(absoluteUrl).get()
            val tables = document.select(selector)
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
            LogUtils.d(TAG, "syncOfficerListFromURL: $officeName, ${data.size}")
//                repository.insertEmployeeList(data)
            repository.insertEmployeeList(data)
        } else{
            LogUtils.e(TAG, "syncOfficerList: unable to get officer data $url")
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
                    val name: String? = trs[i].select("td:nth-child(3) > table > tbody > tr > td:nth-child(1) > table > tbody > tr:nth-child(1) > td:nth-child(2)").first()?.text()
                    val designation: String? = trs[i].select("td:nth-child(3) > table > tbody > tr > td:nth-child(1) > table > tbody > tr:nth-child(2) > td:nth-child(2)").first()?.text()
                    val office: String? = trs[i].select("td:nth-child(3) > table > tbody > tr > td:nth-child(1) > table > tbody > tr:nth-child(3) > td:nth-child(2)").first()?.text()
                    var email: String? = trs[i].select("td:nth-child(3) > table > tbody > tr > td:nth-child(1) > table > tbody > tr:nth-child(4) > td:nth-child(2) > a").first()?.text()
                    var mobile: String? = trs[i].select("td:nth-child(3) > table > tbody > tr > td:nth-child(2) > table > tbody > tr:nth-child(1) > td:nth-child(2)").first()?.text()
                    var phone: String? = trs[i].select("td:nth-child(3) > table > tbody > tr > td:nth-child(2) > table > tbody > tr:nth-child(2) > td:nth-child(2)").first()?.text()
                    if(imageUrl == null) imageUrl = ""
                    if(mobile == null && phone != null) mobile = phone
                    if(phone == null) phone = ""
                    if(email == null) email = ""
                    if(name != null && designation != null && office != null && !mobile.isNullOrEmpty()){
                        data.add(Employee(i, imageUrl, name, designation, office, email, mobile, phone, Category.REB))
//                            LogUtils.d(TAG, "syncBREBContacts: $imageUrl $name $designation $office $email $mobile $phone")
//                            LogUtils.d(TAG, "syncBREBContacts: ${data.last()}")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if(data.size > 0) {
            LogUtils.d(TAG, "syncBREBContacts: ${data.size}")
//                employeeViewModel.insertEmployeeList(data)
            repository.insertEmployeeList(data)
        } else{
            LogUtils.e(TAG, "syncBREBContacts: unable to get breb contacts $url")
        }
    }
    private suspend fun insertAllNoticeByCategory(data: ArrayList<ArrayList<String>>, repository: NPBS2Repository, category:String){
        repository.deleteAllNoticeByType(category)
        val tenderList = ArrayList<NoticeInformation>()
        for((i, d) in data.withIndex()){
//            LogUtils.d(TAG, "insertAllTender: $i $d")
            if(d.size > 3)
                tenderList.add(NoticeInformation(i, d[0], d[1], d[2], d[3], category))
            else
                tenderList.add(NoticeInformation(i, d[0], d[1], d[2], "", category))
        }
        repository.insertAllNotice(tenderList)
    }
}