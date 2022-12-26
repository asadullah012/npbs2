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

    fun insertOfficersFromTable(tableData: List<List<String>>) = viewModelScope.launch{
        val officersList: MutableList<Employee> = ArrayList()
        for (i in tableData.indices) {
            val s = tableData[i][2].split(", ").toTypedArray()
            val designation = s[0]
            val office: String = if (s.size == 1) "সদর দপ্তর" else s[1]
            officersList.add(
                Employee(
                    i, tableData[i][0], tableData[i][1], designation, office,
                    tableData[i][4], tableData[i][5], tableData[i][6], Category.OFFICERS
                )
            )
            //Log.d(TAG, "insertFromTable: " + i + " " + tableData[i][0] + " " + tableData[i][1] + " " + designation + " " + office + " " + tableData[i][4] + " " + tableData[i][5] + " " + tableData[i][6] + " " + Category.OFFICERS);
        }
        mRepository.deleteEmployeeByType(Category.OFFICERS)
        mRepository.insertEmployeeList(officersList)
    }

    fun insertJuniorOfficerFromTable(tableData: List<List<String>>) = viewModelScope.launch{
        val employeeList: MutableList<Employee> = ArrayList()
        var office: String? = null
        var i = 0
        while (i < tableData.size) {
            //Log.d(TAG, "insertJuniorOfficerFromTable: " + Utility.arrayToString(tableData[i]));
            if (tableData[i].size == 1) {
                office = tableData[i][0]
                i++ // ignore header row
            } else employeeList.add(
                Employee(
                    i, tableData[i][1], tableData[i][2], tableData[i][3],
                    office!!, tableData[i][4], tableData[i][5], "", Category.JUNIOR_OFFICER
                )
            )
            i++
        }
        mRepository.deleteEmployeeByType(Category.JUNIOR_OFFICER)
        mRepository.insertEmployeeList(employeeList)
    }

    fun insertBoardMembersFromTable(tableData: List<List<String>>) = viewModelScope.launch{
        val employeeList: MutableList<Employee> = ArrayList()
        for (i in tableData.indices) {
            //Log.d(TAG, "insertBoardMembersFromTable: " + Utility.arrayToString(tableData[i]));
            employeeList.add(
                Employee(
                    i, tableData[i][0], tableData[i][1], tableData[i][2],
                    tableData[i][3], "", tableData[i][4], "", Category.BOARD_MEMBER
                )
            )
        }
        mRepository.deleteEmployeeByType(Category.BOARD_MEMBER)
        mRepository.insertEmployeeList(employeeList)
    }

    fun insertPowerOutageContactFromTable(tableData: List<List<String>>) = viewModelScope.launch{
        val employeeList: MutableList<Employee> = ArrayList()
        for (i in tableData.indices) {
            //Log.d(TAG, "insertPowerOutageContactFromTable: " + Utility.arrayToString(tableData[i]));
            employeeList.add(
                Employee(i, tableData[i][1], tableData[i][2], tableData[i][3],
                    "", "", tableData[i][4], "", Category.POWER_OUTAGE_CONTACT)
            )
        }
        mRepository.deleteEmployeeByType(Category.POWER_OUTAGE_CONTACT)
        mRepository.insertEmployeeList(employeeList)
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
