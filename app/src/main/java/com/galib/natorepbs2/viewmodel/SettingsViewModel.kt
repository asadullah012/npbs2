package com.galib.natorepbs2.viewmodel

import androidx.lifecycle.*
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.models.MyMenuItem
import kotlinx.coroutines.launch

class SettingsViewModel(private val mRepository: NPBS2Repository) : ViewModel() {
    val favoriteMenu: LiveData<List<MyMenuItem>>
        get() = mRepository.favoriteMenu.asLiveData()

    val availableMenu: LiveData<List<MyMenuItem>>
        get() = mRepository.availableMenu.asLiveData()

    fun addToFavoriteMenu(name:String) = viewModelScope.launch {
        mRepository.updateMyMenu(name, 1)
    }

    fun removeFromFavoriteMenu(name: String) = viewModelScope.launch {
        mRepository.updateMyMenu(name, 0)
    }

    fun deleteAllMyMenu() = viewModelScope.launch {
        mRepository.deleteAllMyMenu()
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