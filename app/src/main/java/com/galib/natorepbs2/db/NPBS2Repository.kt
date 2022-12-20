package com.galib.natorepbs2.db

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.galib.natorepbs2.constants.Category
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.atomic.AtomicInteger

class NPBS2Repository(db: NPBS2DB) {
    private val informationDao: InformationDao
    private val achievementDao: AchievementDao
    private val complainCentreDao: ComplainCentreDao
    private val employeeDao: EmployeeDao
    private val officeInformationDao: OfficeInformationDao
    private val noticeInformationDao: NoticeInformationDao

    init {
        informationDao = db.informationDao()
        achievementDao = db.achievementDao()
        complainCentreDao = db.complainCentreDao()
        employeeDao = db.employeeDao()
        officeInformationDao = db.officeInformationDao()
        noticeInformationDao = db.noticeInformationDao()
    }

    val allAchievement: LiveData<List<Achievement>>
        get() = achievementDao.all

    fun insertAchievement(achievement: Achievement?) {
        achievementDao.insert(achievement)
    }

    fun insertAchievementAll(achievements: List<Achievement?>?) {
        NPBS2DB.databaseWriteExecutor.execute { achievementDao.insertAll(achievements) }
    }

    fun deleteAllAchievements() {
        NPBS2DB.databaseWriteExecutor.execute { achievementDao.deleteAll() }
    }

    fun getInformationByCategory(category: String?): LiveData<List<Information>> {
        return informationDao.getInformationsByCategory(category)
    }

    fun insertInformation(information: Information) {
        NPBS2DB.databaseWriteExecutor.execute {
            Log.d(TAG, "insertInformation: " + information.category + " " + information.description)
            informationDao.insert(information)
        }
    }

    fun setMonth(month: String?) {
        insertInformation(Information(0, month, "", Category.atAGlanceMonth))
    }

    val month: LiveData<Information>
        get() = informationDao.getInformationByCategory(Category.atAGlanceMonth)

    fun insertInformations(informationList: List<Information?>) {
        Log.d(TAG, "insertInformations: " + informationList.size)
        NPBS2DB.databaseWriteExecutor.execute { informationDao.insertInfos(informationList) }
    }

    val informationCount: Int
        get() {
            val count = AtomicInteger()
            NPBS2DB.databaseWriteExecutor.execute { count.set(informationDao.count) }
            Log.d(TAG, "getInformationCount: $count")
            return count.toInt()
        }

    fun deleteAllByCategory(category: String?) {
        NPBS2DB.databaseWriteExecutor.execute { informationDao.deleteAllByCategory(category) }
    }

    fun insertComplainCentre(complainCentre: ComplainCentre?) {
        NPBS2DB.databaseWriteExecutor.execute { complainCentreDao.insert(complainCentre) }
    }

    fun insertAllComplainCentre(complainCentreList: List<ComplainCentre?>?) {
        NPBS2DB.databaseWriteExecutor.execute { complainCentreDao.insertAll(complainCentreList) }
    }

    val allComplainCentre: LiveData<List<ComplainCentre>>
        get() = complainCentreDao.all

    fun deleteAllComplainCentre() {
        NPBS2DB.databaseWriteExecutor.execute { complainCentreDao.deleteAll() }
    }

    //Employee
    fun insertEmployeeList(employeeList: List<Employee?>?) {
        NPBS2DB.databaseWriteExecutor.execute { employeeDao.insertList(employeeList) }
    }

    val officerList: LiveData<List<Employee>>
        get() = employeeDao.getAllByType(Category.OFFICERS)
    val juniorOfficerList: LiveData<List<Employee>>
        get() = employeeDao.getAllByType(Category.JUNIOR_OFFICER)
    val boardMemberList: LiveData<List<Employee>>
        get() = employeeDao.getAllByType(Category.BOARD_MEMBER)
    val powerOutageContactList: LiveData<List<Employee>>
        get() = employeeDao.getAllByType(Category.POWER_OUTAGE_CONTACT)
    val powerOutageHeaderText: LiveData<Information>
        get() = informationDao.getInformationByCategory(Category.powerOutageContactHeader)
    val powerOutageFooterText: LiveData<Information>
        get() = informationDao.getInformationByCategory(Category.powerOutageContactFooter)
    val officeHead: LiveData<Employee>
        get() = employeeDao.getOfficeHead(Category.OFFICERS)

    fun insertAllOfficeInfo(officeInformationList: List<OfficeInformation>) {
        NPBS2DB.databaseWriteExecutor.execute { officeInformationDao.insertAll(officeInformationList) }
    }

    val allOfficeInformation: LiveData<List<OfficeInformation>>
        get() = officeInformationDao.getAllOffice()

    @WorkerThread
    suspend fun insertAllNotice(noticeInformationList: List<NoticeInformation>) {
        noticeInformationDao.insertAll(noticeInformationList)
    }

    @WorkerThread
    suspend fun deleteAllNoticeByType(type: String) {
        noticeInformationDao.deleteAllByCategory(type)
    }

    fun getAllNoticeByCategory(category: String): Flow<List<NoticeInformation>> {
        return noticeInformationDao.getAllNoticeByCategory(category)
    }

    companion object {
        private val TAG = NPBS2Repository::class.java.name
    }
}