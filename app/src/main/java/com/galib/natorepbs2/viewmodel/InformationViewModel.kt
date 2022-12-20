package com.galib.natorepbs2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.galib.natorepbs2.db.NPBS2Repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.galib.natorepbs2.NPBS2Application
import com.galib.natorepbs2.constants.Category
import com.galib.natorepbs2.db.Information
import java.util.ArrayList

class InformationViewModel(private val mRepository: NPBS2Repository) : ViewModel() {

    fun getInformationByCategory(category: String?): LiveData<List<Information>> {
        return mRepository.getInformationByCategory(category)
    }

    fun setMonth(month: String?) {
        mRepository.setMonth(month)
    }

    val month: LiveData<Information>
        get() = mRepository.month

    fun insertFromAtAGlance(trtd: List<List<String>>) {
        val informationList: MutableList<Information?> = ArrayList()
        for (i in 1 until trtd.size) {
            informationList.add(
                Information(
                    trtd[i][0].toInt(),
                    trtd[i][1],
                    trtd[i][2],
                    Category.atAGlance
                )
            )
        }
        mRepository.insertInformations(informationList)
    }

    fun insertAll(data: List<Information?>?) {
        mRepository.insertInformations(data!!)
    }

    fun deleteAllByCategory(atAGlance: String?) {
        mRepository.deleteAllByCategory(Category.atAGlance)
    }

    fun insertVisionMission(vision: String, mission: String) {
        var s: Array<String?> = vision.split(": ").toTypedArray()
        mRepository.insertInformation(Information(0, s[0], s[1], Category.visionMission))
        s = mission.split(": ").toTypedArray()
        mRepository.insertInformation(Information(1, s[0], s[1], Category.visionMission))
    }
}

class InformationViewModelFactory(private val repository: NPBS2Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InformationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InformationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}