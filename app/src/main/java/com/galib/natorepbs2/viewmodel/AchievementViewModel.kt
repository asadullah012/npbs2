package com.galib.natorepbs2.viewmodel

import androidx.lifecycle.*
import com.galib.natorepbs2.models.Achievement
import com.galib.natorepbs2.db.NPBS2Repository
import kotlinx.coroutines.launch

class AchievementViewModel(private val mRepository: NPBS2Repository) : ViewModel() {

    val allAchievement: LiveData<List<Achievement>>
        get() = mRepository.allAchievement.asLiveData()

    fun insertFromArray(trtd: List<List<String?>>) = viewModelScope.launch{
        mRepository.deleteAllAchievements()
        val achievements: MutableList<Achievement> = ArrayList()
        for (i in trtd.indices) {
            if (i == 1) continue
            achievements.add(
                Achievement(
                    trtd[i][0]!!, trtd[i][1]!!,
                    trtd[i][2]!!, trtd[i][3]!!, trtd[i][4]!!, i
                )
            )
        }
        mRepository.insertAchievementAll(achievements)
    }

    fun deleteAllAchievements() = viewModelScope.launch{
        mRepository.deleteAllAchievements()
    }
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