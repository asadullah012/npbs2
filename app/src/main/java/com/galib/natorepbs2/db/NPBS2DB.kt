package com.galib.natorepbs2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.galib.natorepbs2.models.*

@Database(
    entities = [Information::class, Achievement::class, ComplainCentre::class, Employee::class, OfficeInformation::class, NoticeInformation::class, MyMenuItem::class],
    version = 1,
    exportSchema = false
)
abstract class NPBS2DB : RoomDatabase() {
    abstract fun informationDao(): InformationDao
    abstract fun achievementDao(): AchievementDao
    abstract fun complainCentreDao(): ComplainCentreDao
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
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}


