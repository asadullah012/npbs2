package com.galib.natorepbs2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Information::class, Achievement::class, ComplainCentre::class, Employee::class, OfficeInformation::class, NoticeInformation::class],
    version = 19,
    exportSchema = false
)
abstract class NPBS2DB : RoomDatabase() {
    abstract fun informationDao(): InformationDao
    abstract fun achievementDao(): AchievementDao
    abstract fun complainCentreDao(): ComplainCentreDao
    abstract fun employeeDao(): EmployeeDao
    abstract fun officeInformationDao(): OfficeInformationDao
    abstract fun noticeInformationDao(): NoticeInformationDao

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
                    .createFromAsset("npbs2_database") //
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}