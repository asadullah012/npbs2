package com.galib.natorepbs2.viewmodel

import androidx.lifecycle.*
import com.galib.natorepbs2.constants.Category
import com.galib.natorepbs2.models.Employee
import com.galib.natorepbs2.db.NPBS2Repository
import kotlinx.coroutines.launch

class EmployeeViewModel(private val mRepository: NPBS2Repository) : ViewModel() {

    val officerList: LiveData<List<Employee>>
        get() = mRepository.officerList.asLiveData()
    val juniorOfficerList: LiveData<List<Employee>>
        get() = mRepository.juniorOfficerList.asLiveData()
    val boardMemberList: LiveData<List<Employee>>
        get() = mRepository.boardMemberList.asLiveData()

    fun getOfficerListByOffice(office : String): LiveData<List<Employee>>{
        return mRepository.getOfficerListByOffice(office).asLiveData()
    }

    val powerOutageContactList: LiveData<List<Employee>>
        get() = mRepository.powerOutageContactList.asLiveData()
    val officeHead: LiveData<Employee>
        get() = mRepository.officeHead.asLiveData()
}

class EmployeeViewModelFactory(private val repository: NPBS2Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmployeeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
