package com.galib.natorepbs2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.galib.natorepbs2.constants.Category
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.db.NoticeInformation

class NoticeInformationViewModel(application: Application) :AndroidViewModel(application) {
    private val mRepository: NPBS2Repository
    private val TAG = "NoticeInfoViewModel"
    init {
        mRepository = NPBS2Repository(application)
    }

    fun getAllNotice() : LiveData<List<NoticeInformation>>? {
        return mRepository.getAllNoticeByCategory(Category.NOTICE)
    }

    fun getAllTender() : LiveData<List<NoticeInformation>>? {
        return mRepository.getAllNoticeByCategory(Category.TENDER)
    }

    fun insertAllTender(data: ArrayList<ArrayList<String>>){
        mRepository.deleteAllNoticeByType(Category.TENDER)
        val tenderList = ArrayList<NoticeInformation>()
        for((i, d) in data.withIndex()){
//            Log.d(TAG, "insertAllTender: $i $d")
            tenderList.add(NoticeInformation(i, d[0], d[1], d[2], d[3], Category.TENDER))
        }
        mRepository.insertAllNotice(tenderList)
    }

    fun insertAllNotice(data: ArrayList<ArrayList<String>>) {
        mRepository.deleteAllNoticeByType(Category.NOTICE)
        val noticeList = ArrayList<NoticeInformation>()
        for((i, d) in data.withIndex()){
//            Log.d(TAG, "insertAllTender: $i $d")
            noticeList.add(NoticeInformation(i, d[0], d[1], d[2], d[3], Category.NOTICE))
        }
        mRepository.insertAllNotice(noticeList)
    }
}
