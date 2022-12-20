package com.galib.natorepbs2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.galib.natorepbs2.db.NPBS2Repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.db.Achievement
import java.util.ArrayList

class AchievementViewModel(private val mRepository: NPBS2Repository) : ViewModel() {

    val allAchievement: LiveData<List<Achievement>>
        get() = mRepository.allAchievement

    fun insertFromArray(trtd: List<List<String?>>) {
        val achievements: MutableList<Achievement?> = ArrayList()
        for (i in trtd.indices) {
            if (i == 1) continue
            achievements.add(
                Achievement(
                    trtd[i][0], trtd[i][1],
                    trtd[i][2], trtd[i][3], trtd[i][4], i
                )
            )
        }
        mRepository.insertAchievementAll(achievements)
    }

    fun deleteAllAchievements() {
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