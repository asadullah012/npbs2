package com.galib.natorepbs2.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.galib.natorepbs2.constants.Category
import com.galib.natorepbs2.models.Information
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.models.Instruction
import kotlinx.coroutines.launch

class InformationViewModel(private val mRepository: NPBS2Repository) : ViewModel() {

    fun getInformationByCategory(category: String?): LiveData<List<Information>> {
        return mRepository.getInformationByCategory(category).asLiveData()
    }

    fun setMonth(month: String) = viewModelScope.launch{
        Log.d("InformationViewModel", "setMonth: $month")
        mRepository.setMonth(month)
    }

    val month: LiveData<Information>
        get() = mRepository.month.asLiveData()

    fun insertFromAtAGlance(trtd: List<List<String>>) = viewModelScope.launch{
        val informationList: MutableList<Information> = ArrayList()
        for (i in 1 until trtd.size) {
            informationList.add(
                Information(trtd[i][0].toInt(), trtd[i][1], trtd[i][2], Category.atAGlance)
            )
        }
        mRepository.insertInformations(informationList)
    }

    fun insertAll(data: List<Information>) = viewModelScope.launch{
        mRepository.insertInformations(data)
    }

    fun deleteAllByCategory(atAGlance: String?) = viewModelScope.launch{
        mRepository.deleteAllByCategory(Category.atAGlance)
    }

    fun getInstructionByType(type: Int): MutableList<Instruction>? {
        return mRepository.getInstructionByType(type)
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