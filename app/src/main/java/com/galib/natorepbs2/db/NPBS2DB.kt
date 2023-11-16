package com.galib.natorepbs2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.galib.natorepbs2.constants.ConstantValues
import com.galib.natorepbs2.models.*

@Database(
    entities = [Information::class, Achievement::class, ComplainCentre::class, Employee::class, OfficeInformation::class, NoticeInformation::class, MyMenuItem::class, Interruption::class],
    version = 4,
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
    abstract fun interruptionDao():InterruptionDao

    companion object {
        @Volatile
        private var INSTANCE: NPBS2DB? = null
        fun getDatabase(context: Context): NPBS2DB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NPBS2DB::class.java,
                    ConstantValues.DATABASE_NAME
                )
                    .createFromAsset(ConstantValues.DATABASE_PATH) // db file database version must be same as current db version
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}


