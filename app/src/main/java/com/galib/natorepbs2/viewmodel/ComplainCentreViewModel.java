package com.galib.natorepbs2.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.galib.natorepbs2.db.ComplainCentre;
import com.galib.natorepbs2.db.NPBS2Repository;

import java.util.ArrayList;
import java.util.List;

public class ComplainCentreViewModel extends AndroidViewModel {
    private NPBS2Repository mRepository;

    public ComplainCentreViewModel(Application application){
        super(application);
        mRepository = new NPBS2Repository(application);
    }

    public LiveData<List<ComplainCentre>> getAllComplainCentre(){
        return mRepository.getAllComplainCentre();
    }

    public void insertFromTable(List<List<String>> trtd){
        List<ComplainCentre> complainCentreList = new ArrayList<>();
        for(int i =1; i<trtd.size(); i++){
            complainCentreList.add(new ComplainCentre(Integer.parseInt(trtd.get(i).get(0)), trtd.get(i).get(1), trtd.get(i).get(2)));
        }
        mRepository.insertAllComplainCentre(complainCentreList);
    }

    public void deleteAll() {
        mRepository.deleteAllComplainCentre();
    }
}
