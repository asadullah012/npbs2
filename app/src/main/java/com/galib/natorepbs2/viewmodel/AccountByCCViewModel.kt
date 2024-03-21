package com.galib.natorepbs2.viewmodel

import androidx.lifecycle.*
import com.galib.natorepbs2.db.NPBS2Repository
import kotlinx.coroutines.launch

class AccountByCCViewModel(private val mRepository: NPBS2Repository) : ViewModel() {

    fun getAllCCByAccount(account : String): LiveData<List<String>>{
        return mRepository.getCCByAccount(account).asLiveData()
    }

    fun deleteAll() = viewModelScope.launch{
        mRepository.deleteAllAccountByCC()
    }
}

class AccountByCCViewModelFactory(private val repository: NPBS2Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountByCCViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountByCCViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}