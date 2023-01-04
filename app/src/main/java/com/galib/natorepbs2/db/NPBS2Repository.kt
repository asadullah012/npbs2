package com.galib.natorepbs2.db

import android.util.Log
import androidx.annotation.WorkerThread
import com.galib.natorepbs2.constants.Category
import com.galib.natorepbs2.models.*
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class NPBS2Repository(db: NPBS2DB) {
    private val informationDao: InformationDao
    private val achievementDao: AchievementDao
    private val complainCentreDao: ComplainCentreDao
    private val employeeDao: EmployeeDao
    private val officeInformationDao: OfficeInformationDao
    private val noticeInformationDao: NoticeInformationDao
    private val myMenuItemDao: MyMenuItemDao
    private val bannerUrls : MutableList<String>

    init {
        informationDao = db.informationDao()
        achievementDao = db.achievementDao()
        complainCentreDao = db.complainCentreDao()
        employeeDao = db.employeeDao()
        officeInformationDao = db.officeInformationDao()
        noticeInformationDao = db.noticeInformationDao()
        myMenuItemDao = db.myMenuItemDao()
        bannerUrls = ArrayList()
    }

    val allAchievement: Flow<List<Achievement>>
        get() = achievementDao.all

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

    suspend fun deleteEmployeeByType(type: Int){
        employeeDao.deleteByCategory(type)
    }

    val officerList: Flow<List<Employee>>
        get() = employeeDao.getAllByType(Category.OFFICERS)
    val juniorOfficerList: Flow<List<Employee>>
        get() = employeeDao.getAllByType(Category.JUNIOR_OFFICER)
    val boardMemberList: Flow<List<Employee>>
        get() = employeeDao.getAllByType(Category.BOARD_MEMBER)
    val powerOutageContactList: Flow<List<Employee>>
        get() = employeeDao.getAllByType(Category.POWER_OUTAGE_CONTACT)
    val officeHead: Flow<Employee>
        get() = employeeDao.getOfficeHead(Category.OFFICERS)

    @WorkerThread
    suspend fun insertAllOfficeInfo(officeInformationList: List<OfficeInformation>) {
        officeInformationDao.insertAll(officeInformationList)
    }

    fun getOfficerListByOffice(office: String): Flow<List<Employee>> {
        if(office == "বাংলাদেশ পল্লী বিদ্যুতায়ন বোর্ড")
            return employeeDao.getAllByType(Category.REB)
        return employeeDao.getOfficerListByOffice(office, Category.OTHER_OFFICES)
    }

    fun getOfficerListREB():  Flow<List<Employee>> {
        return employeeDao.getAllByType(Category.OTHER_OFFICES)
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

    fun getBannerUrls(): List<String> {
        return bannerUrls
    }

    fun setBannerUrls(list : List<String>){
        bannerUrls.clear()
        bannerUrls.addAll(list)
    }

    fun getInstructionByType(type: Int): MutableList<Instruction> {
        val list : MutableList<Instruction> = ArrayList()
        when (type) {
            Category.BKASH_APP -> {
                list.add(Instruction("bKash App/1.PNG", "1. বিকাশ অ্যাপের হোম স্ক্রিন থেকে পে বিল সিলেক্ট করুন"))
                list.add(Instruction("bKash App/2.PNG", "2. বিদ্যুৎ ট্যাপ করে Palli Bidyut (Postpaid) সিলেক্ট করুন"))
                list.add(Instruction("bKash App/3.PNG", "3. বিলের সময়সীমা এবং এসএমএস একাউন্ট নাম্বার দিন। পরবর্তী বিল পেমেন্টের জন্য একাউন্টটি সেভ করে রাখতে পারেন"))
                list.add(Instruction("bKash App/4.PNG", "4. বিলের মাস ও বছর দিয়ে পরের স্ক্রিনে যেতে ট্যাপ করুন"))
                list.add(Instruction("bKash App/5.PNG", "5. আপনার বিকাশ একাউন্টের পিন নাম্বার দিন"))
                list.add(Instruction("bKash App/6.PNG", "6. ’পে বিল’ সম্পন্ন করতে স্ক্রিনের নিচের অংশ ট্যাপ করে ধরে রাখুন"))
                list.add(Instruction("bKash App/7.PNG", "7. ‘পে বিল’ সম্পন্ন হলে কনফার্মেশন পাবেন"))
                list.add(Instruction("bKash App/8.PNG", "8. অ্যাপে দেখে নিতে পারেন বিলের ডিজিটাল রিসিট"))
            }
            Category.BKASH_USSD -> {
                list.add(Instruction("bKash USSD/1.PNG", "1. 247# ডায়াল করুন"))
                list.add(Instruction("bKash USSD/2.PNG", "2. পে বিল সিলেক্ট করতে ৬ চাপুন"))
                list.add(Instruction("bKash USSD/3.PNG", "3. ইলেক্ট্রিসিটি (পোস্টপেইড) সিলেক্ট করতে ২ চাপুন"))
                list.add(Instruction("bKash USSD/4.PNG", "4. পল্লী বিদ্যুৎ (পোস্টপেইড) সিলেক্ট করতে ১ চাপুন"))
                list.add(Instruction("bKash USSD/5.PNG", "5. মেক পেমেন্ট সিলেক্ট করতে ২ চাপুন"))
                list.add(Instruction("bKash USSD/6.PNG", "6. একাউন্ট নাম্বার সিলেক্ট করতে ১ চাপুন"))
                list.add(Instruction("bKash USSD/7.PNG", "7. পল্লী বিদ্যুৎ বিলে উল্লিখিত এসএমএস একাউন্ট নাম্বারটি দিন"))
                list.add(Instruction("bKash USSD/8.PNG", "8. বিলের মাস এবং বছর দিন"))
                list.add(Instruction("bKash USSD/9.PNG", "9. পল্লী বিদ্যুৎ বিলে উল্লিখিত বিল এমাউন্টটি দিন"))
                list.add(Instruction("bKash USSD/10.PNG", "10. পে বিলের সামারি দেখে পিন নাম্বারটি দিন"))
                list.add(Instruction("bKash USSD/11.PNG", "11. কনফার্মেশন মেসেজ ও বিল পেমেন্ট মেসেজ পাবেন"))
            }
            Category.ROCKET_APP -> {
                list.add(Instruction("Rocket App/1.PNG", ""))
                list.add(Instruction("Rocket App/2.PNG", ""))
                list.add(Instruction("Rocket App/3.PNG", ""))
            }
            Category.ROCKET_USSD -> {
                list.add(Instruction("Rocket USSD/1.PNG", ""))
                list.add(Instruction("Rocket USSD/2.PNG", ""))
                list.add(Instruction("Rocket USSD/3.PNG", ""))
                list.add(Instruction("Rocket USSD/4.PNG", ""))
                list.add(Instruction("Rocket USSD/5.PNG", ""))
                list.add(Instruction("Rocket USSD/6.PNG", ""))
                list.add(Instruction("Rocket USSD/7.PNG", ""))
                list.add(Instruction("Rocket USSD/8.PNG", ""))
                list.add(Instruction("Rocket USSD/9.PNG", ""))
            }
            Category.GPAY_APP -> {
                list.add(Instruction("GPay App/1.PNG", ""))
                list.add(Instruction("GPay App/2.PNG", ""))
                list.add(Instruction("GPay App/3.PNG", ""))
                list.add(Instruction("GPay App/4.PNG", ""))
                list.add(Instruction("GPay App/5.PNG", ""))
                list.add(Instruction("GPay App/6.PNG", ""))
            }
            Category.GPAY_USSD -> {
                list.add(Instruction("GPay USSD/1.PNG", ""))
                list.add(Instruction("GPay USSD/2.PNG", ""))
                list.add(Instruction("GPay USSD/3.PNG", ""))
                list.add(Instruction("GPay USSD/4.PNG", ""))
                list.add(Instruction("GPay USSD/5.PNG", ""))
                list.add(Instruction("GPay USSD/6.PNG", ""))
                list.add(Instruction("GPay USSD/7.PNG", ""))
                list.add(Instruction("GPay USSD/8.PNG", ""))
                list.add(Instruction("GPay USSD/9.PNG", ""))
                list.add(Instruction("GPay USSD/10.PNG", ""))
            }
            Category.UPAY_APP -> {
                list.add(Instruction("Upay App/1.PNG", ""))
                list.add(Instruction("Upay App/2.PNG", ""))
                list.add(Instruction("Upay App/3.PNG", ""))
                list.add(Instruction("Upay App/4.PNG", ""))
                list.add(Instruction("Upay App/5.PNG", ""))
                list.add(Instruction("Upay App/6.PNG", ""))
                list.add(Instruction("Upay App/7.PNG", ""))
                list.add(Instruction("Upay App/8.PNG", ""))
            }
            Category.UCASH_USSD -> {
                list.add(Instruction("UKash USSD/1.PNG", ""))
                list.add(Instruction("UKash USSD/2.PNG", ""))
                list.add(Instruction("UKash USSD/3.PNG", ""))
                list.add(Instruction("UKash USSD/4.PNG", ""))
                list.add(Instruction("UKash USSD/5.PNG", ""))
                list.add(Instruction("UKash USSD/6.PNG", ""))
                list.add(Instruction("UKash USSD/7.PNG", ""))
                list.add(Instruction("UKash USSD/8.PNG", ""))
                list.add(Instruction("UKash USSD/9.PNG", ""))
            }
        }
        return list
    }

    val favoriteMenu : Flow<List<MyMenuItem>>
        get() = myMenuItemDao.favoriteMenu

    val availableMenu : Flow<List<MyMenuItem>>
        get() = myMenuItemDao.availableMenu

    fun getMyMenuCount(): Int = runBlocking {
        val count = async {
            myMenuItemDao.countMyMenuItem()
        }
        count.start()
        count.await()
    }

    @WorkerThread
    suspend fun insertAllMenu(myMenuItemList : List<MyMenuItem>){
        myMenuItemDao.insertAll(myMenuItemList)
    }

    @WorkerThread
    suspend fun updateMyMenu(name:String, isFavorite : Int){
        myMenuItemDao.updateFavorite(name, isFavorite)
    }

    @WorkerThread
    suspend fun deleteAllMyMenu(){
        myMenuItemDao.deleteAll()
    }

    companion object {
        private val TAG = NPBS2Repository::class.java.name
    }
}