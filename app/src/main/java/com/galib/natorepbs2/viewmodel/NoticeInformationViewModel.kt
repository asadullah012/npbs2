package com.galib.natorepbs2.viewmodel

import androidx.lifecycle.*
import com.galib.natorepbs2.constants.Category
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.models.NoticeInformation
import kotlinx.coroutines.launch

class NoticeInformationViewModel(private val mRepository: NPBS2Repository) : ViewModel() {

    fun getAllNotice() : LiveData<List<NoticeInformation>> {
        return mRepository.getAllNoticeByCategory(Category.NOTICE).asLiveData()
    }

    fun getAllTender() : LiveData<List<NoticeInformation>> {
        return mRepository.getAllNoticeByCategory(Category.TENDER).asLiveData()
    }

    fun getAllNews() : LiveData<List<NoticeInformation>> {
        return mRepository.getAllNoticeByCategory(Category.NEWS).asLiveData()
    }

    fun getAllJob() : LiveData<List<NoticeInformation>> {
        return mRepository.getAllNoticeByCategory(Category.JOB).asLiveData()
    }

}

class NoticeViewModelFactory(private val repository: NPBS2Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoticeInformationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoticeInformationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
