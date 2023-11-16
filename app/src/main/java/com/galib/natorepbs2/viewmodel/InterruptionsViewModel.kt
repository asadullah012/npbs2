package com.galib.natorepbs2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.galib.natorepbs2.constants.ConstantValues
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.gsheet.GSheetDataFetcher
import com.galib.natorepbs2.logger.LogUtils
import com.galib.natorepbs2.models.Interruption

class InterruptionsViewModel(private val mRepository: NPBS2Repository) : ViewModel() {
    val TAG = "InterruptionsViewModel"

    val allInteruption: LiveData<List<Interruption>>
        get() = mRepository.allInterruption.asLiveData()

    fun onRefreshClicked() {
        LogUtils.d(TAG, "onRefreshClicked: On refresh clicked")
        GSheetDataFetcher.fetchData(ConstantValues.GSheetID, ConstantValues.GSheetName) {
            if (it != null) {
                for (row in it) {
                    LogUtils.d(TAG, "onResponse: $row")

                }
            }
        }
    }

}

class InterruptionsViewModelFactory(private val repository: NPBS2Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InterruptionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InterruptionsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}