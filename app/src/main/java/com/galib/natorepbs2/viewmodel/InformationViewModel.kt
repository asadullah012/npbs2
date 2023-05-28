package com.galib.natorepbs2.viewmodel

import androidx.lifecycle.*
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.models.Information
import com.galib.natorepbs2.models.Instruction
import kotlinx.coroutines.launch

class InformationViewModel(private val mRepository: NPBS2Repository) : ViewModel() {

    fun getInformationByCategory(category: String?): LiveData<List<Information>> {
        return mRepository.getInformationByCategory(category).asLiveData()
    }

    val month: LiveData<Information?>
        get() = mRepository.month.asLiveData()

    val importantNotice: LiveData<Information?>
        get() = mRepository.importantNotice.asLiveData()

    val bannerUrl: LiveData<List<String>>
        get() = mRepository.bannersUrl.asLiveData()

    fun insertAll(data: List<Information>) = viewModelScope.launch{
        mRepository.insertInformations(data)
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