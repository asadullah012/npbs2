package com.galib.natorepbs2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.db.OfficeInformation

class OfficeInformationViewModel(application: Application) : AndroidViewModel(application)  {
    private val mRepository: NPBS2Repository
    private val TAG = "OfficeInfoViewModel"
    init {
        mRepository = NPBS2Repository(application)
    }

    fun getAllOfficeInfo() : LiveData<MutableList<OfficeInformation>>? {
        return mRepository.allOfficeInformation
    }

    fun insertAllOfficeInformation(data: ArrayList<OfficeInformation>){
        mRepository.insertAllOfficeInfo(data)
    }
}