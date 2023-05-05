package com.galib.natorepbs2.viewmodel

import androidx.lifecycle.*
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.models.MyMenuItem
import kotlinx.coroutines.launch

class SettingsViewModel(private val mRepository: NPBS2Repository) : ViewModel() {

    val allMenu: LiveData<List<MyMenuItem>>
        get() = mRepository.allMenu.asLiveData()

    fun getMyMenuCount(): Int {
        return mRepository.getMyMenuCount()
    }
    fun addMenus(allMenu: List<MyMenuItem>) = viewModelScope.launch {
        mRepository.deleteAllMyMenu()
        mRepository.insertAllMenu(allMenu)
    }
    fun updateMyMenu(name:String, isFavorite:Boolean) = viewModelScope.launch {
        mRepository.updateMyMenu(name, isFavorite)
    }
}

class SettingsViewModelFactory(private val repository: NPBS2Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}