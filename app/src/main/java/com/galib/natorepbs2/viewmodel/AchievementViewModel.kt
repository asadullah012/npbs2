package com.galib.natorepbs2.viewmodel

import androidx.lifecycle.*
import com.galib.natorepbs2.models.Achievement
import com.galib.natorepbs2.db.NPBS2Repository
import kotlinx.coroutines.launch

class AchievementViewModel(private val mRepository: NPBS2Repository) : ViewModel() {

    val allAchievement: LiveData<List<Achievement>>
        get() = mRepository.allAchievement.asLiveData()

}

class AchievementViewModelFactory(private val repository: NPBS2Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AchievementViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AchievementViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}