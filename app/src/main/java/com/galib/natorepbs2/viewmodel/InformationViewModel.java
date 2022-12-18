package com.galib.natorepbs2.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.galib.natorepbs2.constants.Category;
import com.galib.natorepbs2.db.Information;
import com.galib.natorepbs2.db.NPBS2Repository;

import java.util.ArrayList;
import java.util.List;

public class InformationViewModel extends AndroidViewModel {
    private NPBS2Repository mRepository;

    public InformationViewModel (Application application) {
        super(application);
        mRepository = new NPBS2Repository(application);
    }

    public LiveData<List<Information>> getInformationByCategory(String category) {
        return mRepository.getInformationByCategory(category);
    }

    public void setMonth(String month){
        mRepository.setMonth(month);
    }

    public LiveData<Information> getMonth(){
        return  mRepository.getMonth();
    }

    public void insertFromAtAGlance(List<List<String>> trtd) {
        List<Information> informationList = new ArrayList<>();
        for(int i =1; i<trtd.size(); i++){
            informationList.add(new Information(Integer.parseInt(trtd.get(i).get(0)), trtd.get(i).get(1), trtd.get(i).get(2), Category.atAGlance));
        }
        mRepository.insertInformations(informationList);
    }

    public void insertAll(List<Information> data){
        mRepository.insertInformations(data);
    }

    public void deleteAllByCategory(String atAGlance) {
        mRepository.deleteAllByCategory(Category.atAGlance);
    }

    public void insertVisionMission(String vision, String mission) {
        String s[] = vision.split(": ");
        mRepository.insertInformation(new Information(0, s[0], s[1], Category.visionMission));
        s = mission.split(": ");
        mRepository.insertInformation(new Information(1, s[0], s[1], Category.visionMission));
    }
}
