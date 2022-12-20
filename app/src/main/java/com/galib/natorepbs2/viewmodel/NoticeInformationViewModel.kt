package com.galib.natorepbs2.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.galib.natorepbs2.constants.Category
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.db.NoticeInformation
import kotlinx.coroutines.launch

class NoticeInformationViewModel(private val mRepository: NPBS2Repository) : ViewModel() {
    private val TAG = "NoticeInfoViewModel"

    fun getAllNotice() : LiveData<List<NoticeInformation>>? {
        return mRepository.getAllNoticeByCategory(Category.NOTICE).asLiveData()
    }

    fun getAllTender() : LiveData<List<NoticeInformation>>? {
        return mRepository.getAllNoticeByCategory(Category.TENDER).asLiveData()
    }

    fun insertAllTender(data: ArrayList<ArrayList<String>>) = viewModelScope.launch{
        mRepository.deleteAllNoticeByType(Category.TENDER)
        val tenderList = ArrayList<NoticeInformation>()
        for((i, d) in data.withIndex()){
//            Log.d(TAG, "insertAllTender: $i $d")
            tenderList.add(NoticeInformation(i, d[0], d[1], d[2], d[3], Category.TENDER))
        }
        mRepository.insertAllNotice(tenderList)
    }

    fun insertAllNotice(data: ArrayList<ArrayList<String>>) = viewModelScope.launch{
        mRepository.deleteAllNoticeByType(Category.NOTICE)
        val noticeList = ArrayList<NoticeInformation>()
        for((i, d) in data.withIndex()){
//            Log.d(TAG, "insertAllTender: $i $d")
            noticeList.add(NoticeInformation(i, d[0], d[1], d[2], d[3], Category.NOTICE))
        }
        mRepository.insertAllNotice(noticeList)
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
