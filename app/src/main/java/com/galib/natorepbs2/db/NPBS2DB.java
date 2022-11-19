package com.galib.natorepbs2.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Information.class, Achievement.class, ComplainCentre.class, Employee.class}, version = 5, exportSchema = false)
public abstract class NPBS2DB extends RoomDatabase {

    public abstract InformationDao informationDao();
    public abstract AchievementDao achievementDao();
    public abstract ComplainCentreDao complainCentreDao();
    public abstract EmployeeDao employeeDao();

    private static volatile NPBS2DB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static NPBS2DB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NPBS2DB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    NPBS2DB.class, "npbs2_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
