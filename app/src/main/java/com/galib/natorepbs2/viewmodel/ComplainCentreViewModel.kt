package com.galib.natorepbs2.viewmodel

import androidx.lifecycle.*
import com.galib.natorepbs2.models.ComplainCentre
import com.galib.natorepbs2.db.NPBS2Repository
import kotlinx.coroutines.launch

class ComplainCentreViewModel(private val mRepository: NPBS2Repository) : ViewModel() {

    val allComplainCentre: LiveData<List<ComplainCentre>>
        get() = mRepository.allComplainCentre.asLiveData()

    fun deleteAll() = viewModelScope.launch{
        mRepository.deleteAllComplainCentre()
    }
}

class ComplainCentreViewModelFactory(private val repository: NPBS2Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComplainCentreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ComplainCentreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}