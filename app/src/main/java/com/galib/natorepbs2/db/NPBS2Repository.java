package com.galib.natorepbs2.db;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NPBS2Repository {

    private InformationDao informationDao;
    private static final String TAG = NPBS2Repository.class.getName();
    public NPBS2Repository(Application application) {
        NPBS2DB db = NPBS2DB.getDatabase(application);
        informationDao = db.informationDao();
    }

    public LiveData<List<Information>> getInformationByCategory(String category) {
        return informationDao.getInformationByCategory(category);
    }

    public void insertInformation(Information information) {
        NPBS2DB.databaseWriteExecutor.execute(() -> {
            informationDao.insert(information);
        });
    }

    public int insertInformations(List<Information> informationList) {
        Log.d(TAG, "insertInformations: " + informationList.size());
        NPBS2DB.databaseWriteExecutor.execute(() -> {
            informationDao.insertInfos(informationList);
        });
        return getInformationCount();
    }

    public int getInformationCount(){
        AtomicInteger count = new AtomicInteger();
        NPBS2DB.databaseWriteExecutor.execute(() -> count.set(informationDao.getCount()));
        Log.d(TAG, "getInformationCount: " + count);
        return count.intValue();
    }

    public void deleteAllByCategory(String category) {
        NPBS2DB.databaseWriteExecutor.execute(() -> informationDao.deleteAllByCategory(category));
    }
}