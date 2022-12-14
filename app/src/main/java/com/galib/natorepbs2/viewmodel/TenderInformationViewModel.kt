package com.galib.natorepbs2.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.db.TenderInformation
import com.galib.natorepbs2.utils.Utility
import java.text.SimpleDateFormat

class TenderInformationViewModel(application: Application) :AndroidViewModel(application) {
    private val mRepository: NPBS2Repository
    private val TAG = "TenderInfoViewModel"
    init {
        mRepository = NPBS2Repository(application)
    }

    fun getAllTender() : LiveData<MutableList<TenderInformation>>? {
        return mRepository.allTender
    }

    fun insertAllTender(data: ArrayList<ArrayList<String>>){
        val tenderList = ArrayList<TenderInformation>()
        for((i, d) in data.withIndex()){
//            Log.d(TAG, "insertAllTender: $i $d")
            tenderList.add(TenderInformation(i, d[0], d[1], d[2], d[3]))
        }
        mRepository.insertAllTender(tenderList)
    }
}
