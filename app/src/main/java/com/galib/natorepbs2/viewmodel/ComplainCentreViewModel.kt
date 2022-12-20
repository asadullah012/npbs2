package com.galib.natorepbs2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.galib.natorepbs2.db.ComplainCentre
import com.galib.natorepbs2.db.NPBS2Repository

class ComplainCentreViewModel(private val mRepository: NPBS2Repository) : ViewModel() {

    val allComplainCentre: LiveData<List<ComplainCentre>>
        get() = mRepository.allComplainCentre

    fun insertFromTable(trtd: List<List<String>>) {
        val complainCentreList: MutableList<ComplainCentre?> = ArrayList()
        for (i in 1 until trtd.size) {
            complainCentreList.add(ComplainCentre(trtd[i][0].toInt(), trtd[i][1], trtd[i][2]))
        }
        mRepository.insertAllComplainCentre(complainCentreList)
    }

    fun deleteAll() {
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