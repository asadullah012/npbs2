package com.galib.natorepbs2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.galib.natorepbs2.models.AccountByCC
import com.galib.natorepbs2.models.Achievement
import com.galib.natorepbs2.models.ComplainCentre
import com.galib.natorepbs2.models.Employee
import com.galib.natorepbs2.models.Information
import com.galib.natorepbs2.models.MyMenuItem
import com.galib.natorepbs2.models.NoticeInformation
import com.galib.natorepbs2.models.OfficeInformation

@Database(
    entities = [Information::class, Achievement::class, ComplainCentre::class, AccountByCC::class, Employee::class, OfficeInformation::class, NoticeInformation::class, MyMenuItem::class],
    version = 4,
    exportSchema = false
)
abstract class NPBS2DB : RoomDatabase() {
    abstract fun informationDao(): InformationDao
    abstract fun achievementDao(): AchievementDao
    abstract fun complainCentreDao(): ComplainCentreDao
    abstract fun accountByCCDao(): AccountByCCDao
    abstract fun employeeDao(): EmployeeDao
    abstract fun officeInformationDao(): OfficeInformationDao
    abstract fun noticeInformationDao(): NoticeInformationDao
    abstract fun myMenuItemDao(): MyMenuItemDao

    companion object {
        @Volatile
        private var INSTANCE: NPBS2DB? = null
        fun getDatabase(context: Context): NPBS2DB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NPBS2DB::class.java,
                    "npbs2_database"
                )
                    .createFromAsset("database/npbs2_database") // db file database version must be same as current db version
//                    .setQueryCallback({ sqlQuery, bindArgs ->
//                        Log.d("NPBS2DB", "SQL Query: $sqlQuery SQL Args: $bindArgs")
//                    }, Executors.newSingleThreadExecutor())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}


