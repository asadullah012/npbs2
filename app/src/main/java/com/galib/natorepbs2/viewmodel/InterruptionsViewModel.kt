package com.galib.natorepbs2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.galib.natorepbs2.constants.ConstantValues
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.gsheet.GSheetDataFetcher
import com.galib.natorepbs2.logger.LogUtils
import com.galib.natorepbs2.models.Interruption
import kotlinx.coroutines.launch

class InterruptionsViewModel(private val mRepository: NPBS2Repository) : ViewModel() {
    val TAG = "InterruptionsViewModel"

    val allInteruption: LiveData<List<Interruption>>
        get() = mRepository.allInterruption.asLiveData()

    fun onRefreshClicked() {
        LogUtils.d(TAG, "onRefreshClicked: On refresh clicked")
        GSheetDataFetcher.fetchData(ConstantValues.GSheetIDInterruptions, ConstantValues.GSheetNameInterruptions) {
            if (it != null) {
                LogUtils.d(TAG, "onResponse: $it")
                val interruptions: MutableList<Interruption> = ArrayList()
                val size = it.size - 1
                for (i in 1..size){
                    interruptions.add(Interruption(it[i][0],it[i][1],it[i][2],it[i][3],it[i][4],it[i][5],it[i][6],it[i][7], it[i][8], false))
                }
                viewModelScope.launch {
                    mRepository.deleteAllInterruption()
                    mRepository.insertAllInterruption(interruptions)
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