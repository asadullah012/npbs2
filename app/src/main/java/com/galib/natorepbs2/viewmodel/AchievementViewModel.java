package com.galib.natorepbs2.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.galib.natorepbs2.db.Achievement;
import com.galib.natorepbs2.db.NPBS2Repository;

import java.util.ArrayList;
import java.util.List;

public class AchievementViewModel extends AndroidViewModel {
    private NPBS2Repository mRepository;

    public AchievementViewModel(Application application) {
        super(application);
        mRepository = new NPBS2Repository(application);
    }

    public LiveData<List<Achievement>> getAllAchievement() {
        return mRepository.getAllAchievement();
    }

    public void insertFromArray(List<List<String>> trtd) {
        List<Achievement> achievements = new ArrayList<>();
        for(int i =0; i<trtd.size(); i++){
            if(i == 1) continue;
            achievements.add(new Achievement(trtd.get(i).get(0), trtd.get(i).get(1),
                    trtd.get(i).get(2), trtd.get(i).get(3), trtd.get(i).get(4), i));
        }
        mRepository.insertAchievementAll(achievements);
    }

    public void deleteAllAchievements() {
        mRepository.deleteAllAchievements();
    }
}
