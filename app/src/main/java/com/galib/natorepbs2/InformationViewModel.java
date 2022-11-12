package com.galib.natorepbs2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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

    LiveData<List<Information>> getInformationByCategory(String category) {
        return mRepository.getInformationByCategory(category);
    }

    public void insertFromArray(String[][] trtd) {
        List<Information> informationList = new ArrayList<>();
        for(int i =1; i<trtd.length; i++){
            informationList.add(new Information(Integer.parseInt(trtd[i][0]), trtd[i][1], trtd[i][2], Category.atAGlance));
        }
        mRepository.insertInformations(informationList);
    }

    public void deleteAllByCategory(String atAGlance) {
        mRepository.deleteAllByCategory(Category.atAGlance);
    }
}
