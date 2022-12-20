package com.galib.natorepbs2.db

import android.util.Log
import androidx.annotation.WorkerThread
import com.galib.natorepbs2.constants.Category
import kotlinx.coroutines.flow.Flow

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

    val allAchievement: Flow<List<Achievement>>
        get() = achievementDao.all

    @WorkerThread
    suspend fun insertAchievement(achievement: Achievement) {
        achievementDao.insert(achievement)
    }

    @WorkerThread
    suspend fun insertAchievementAll(achievements: List<Achievement>) {
        achievementDao.insertAll(achievements)
    }

    @WorkerThread
    suspend fun deleteAllAchievements() {
        achievementDao.deleteAll()
    }

    fun getInformationByCategory(category: String?): Flow<List<Information>> {
        return informationDao.getInformationsByCategory(category)
    }

    @WorkerThread
    suspend fun insertInformation(information: Information) {
            Log.d(TAG, "insertInformation: " + information.category + " " + information.description)
            informationDao.insert(information)
    }

    @WorkerThread
    suspend fun setMonth(month: String) {
        insertInformation(Information(0, month, "", Category.atAGlanceMonth))
    }

    val month: Flow<Information>
        get() = informationDao.getInformationByCategory(Category.atAGlanceMonth)

    @WorkerThread
    suspend fun insertInformations(informationList: List<Information>) {
        informationDao.insertInfos(informationList)
    }
    
    fun deleteAllByCategory(category: String) {
        informationDao.deleteAllByCategory(category)
    }

    @WorkerThread
    suspend fun insertComplainCentre(complainCentre: ComplainCentre) {
        complainCentreDao.insert(complainCentre)
    }

    @WorkerThread
    suspend fun insertAllComplainCentre(complainCentreList: List<ComplainCentre>) {
        complainCentreDao.insertAll(complainCentreList)
    }

    val allComplainCentre: Flow<List<ComplainCentre>>
        get() = complainCentreDao.allComplainCentre

    @WorkerThread
    suspend fun deleteAllComplainCentre() {
        complainCentreDao.deleteAll()
    }

    //Employee
    @WorkerThread
    suspend fun insertEmployeeList(employeeList: List<Employee>) {
        employeeDao.insertList(employeeList)
    }

    val officerList: Flow<List<Employee>>
        get() = employeeDao.getAllByType(Category.OFFICERS)
    val juniorOfficerList: Flow<List<Employee>>
        get() = employeeDao.getAllByType(Category.JUNIOR_OFFICER)
    val boardMemberList: Flow<List<Employee>>
        get() = employeeDao.getAllByType(Category.BOARD_MEMBER)
    val powerOutageContactList: Flow<List<Employee>>
        get() = employeeDao.getAllByType(Category.POWER_OUTAGE_CONTACT)
    val powerOutageHeaderText: Flow<Information>
        get() = informationDao.getInformationByCategory(Category.powerOutageContactHeader)
    val powerOutageFooterText: Flow<Information>
        get() = informationDao.getInformationByCategory(Category.powerOutageContactFooter)
    val officeHead: Flow<Employee>
        get() = employeeDao.getOfficeHead(Category.OFFICERS)

    @WorkerThread
    suspend fun insertAllOfficeInfo(officeInformationList: List<OfficeInformation>) {
        officeInformationDao.insertAll(officeInformationList)
    }

    val allOfficeInformation: Flow<List<OfficeInformation>>
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