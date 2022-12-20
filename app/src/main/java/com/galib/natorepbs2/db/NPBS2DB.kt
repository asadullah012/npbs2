package com.galib.natorepbs2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(
    entities = [Information::class, Achievement::class, ComplainCentre::class, Employee::class, OfficeInformation::class, NoticeInformation::class],
    version = 18,
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

        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getDatabase(context: Context): NPBS2DB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NPBS2DB::class.java,
                    "npbs2_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}