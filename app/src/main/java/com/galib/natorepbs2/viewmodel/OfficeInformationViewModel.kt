package com.galib.natorepbs2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.db.OfficeInformation

class OfficeInformationViewModel(private val mRepository: NPBS2Repository) : ViewModel() {

    private val TAG = "OfficeInfoViewModel"

    fun getAllOfficeInfo() : LiveData<List<OfficeInformation>>? {
        return mRepository.allOfficeInformation
    }

    fun insertAllOfficeInformation(data: ArrayList<OfficeInformation>){
        mRepository.insertAllOfficeInfo(data)
    }
}

class OfficeViewModelFactory(private val repository: NPBS2Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OfficeInformationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OfficeInformationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}