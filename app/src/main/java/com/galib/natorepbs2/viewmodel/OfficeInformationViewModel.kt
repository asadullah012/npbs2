package com.galib.natorepbs2.viewmodel

import androidx.lifecycle.*
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.models.OfficeInformation
import kotlinx.coroutines.launch

class OfficeInformationViewModel(private val mRepository: NPBS2Repository) : ViewModel() {

    fun getAllOfficeInfo() : LiveData<List<OfficeInformation>> {
        return mRepository.allOfficeInformation.asLiveData()
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