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

    public void insertFromTable(String[][] trtd){
        List<ComplainCentre> complainCentreList = new ArrayList<>();
        for(int i =1; i<trtd.length; i++){
            complainCentreList.add(new ComplainCentre(Integer.parseInt(trtd[i][0]), trtd[i][1], trtd[i][2]));
        }
        mRepository.insertAllComplainCentre(complainCentreList);
    }
}
