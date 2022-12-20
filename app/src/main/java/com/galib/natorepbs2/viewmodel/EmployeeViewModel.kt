package com.galib.natorepbs2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.galib.natorepbs2.constants.Category
import com.galib.natorepbs2.db.Employee
import com.galib.natorepbs2.db.NPBS2Repository

class EmployeeViewModel(private val mRepository: NPBS2Repository) : ViewModel() {

    val officerList: LiveData<List<Employee>>
        get() = mRepository.officerList
    val juniorOfficerList: LiveData<List<Employee>>
        get() = mRepository.juniorOfficerList
    val boardMemberList: LiveData<List<Employee>>
        get() = mRepository.boardMemberList

    fun insertOfficersFromTable(tableData: List<List<String>>?) {
        if (tableData == null) return
        val officersList: MutableList<Employee?> = ArrayList()
        for (i in tableData.indices) {
            val s = tableData[i][2].split(", ").toTypedArray()
            val designation = s[0]
            var office: String
            office = if (s.size == 1) "সদর দপ্তর" else s[1]
            officersList.add(
                Employee(
                    i, tableData[i][0], tableData[i][1], designation, office,
                    tableData[i][4], tableData[i][5], tableData[i][6], Category.OFFICERS
                )
            )
            //Log.d(TAG, "insertFromTable: " + i + " " + tableData[i][0] + " " + tableData[i][1] + " " + designation + " " + office + " " + tableData[i][4] + " " + tableData[i][5] + " " + tableData[i][6] + " " + Category.OFFICERS);
        }
        mRepository.insertEmployeeList(officersList)
    }

    fun insertJuniorOfficerFromTable(tableData: List<List<String?>>?) {
        if (tableData == null) return
        val employeeList: MutableList<Employee?> = ArrayList()
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
                    office, tableData[i][4], tableData[i][5], null, Category.JUNIOR_OFFICER
                )
            )
            i++
        }
        mRepository.insertEmployeeList(employeeList)
    }

    fun insertBoardMembersFromTable(tableData: List<List<String?>>?) {
        if (tableData == null) return
        val employeeList: MutableList<Employee?> = ArrayList()
        for (i in tableData.indices) {
            //Log.d(TAG, "insertBoardMembersFromTable: " + Utility.arrayToString(tableData[i]));
            employeeList.add(
                Employee(
                    i, null, tableData[i][1], tableData[i][2],
                    tableData[i][3], null, tableData[i][4], null, Category.BOARD_MEMBER
                )
            )
        }
        mRepository.insertEmployeeList(employeeList)
    }

    fun insertPowerOutageContactFromTable(
        tableData: List<List<String?>>?,
        headerText: String?,
        footerText: String?
    ) {
        if (tableData == null) return
        val employeeList: MutableList<Employee?> = ArrayList()
        for (i in tableData.indices) {
            //Log.d(TAG, "insertPowerOutageContactFromTable: " + Utility.arrayToString(tableData[i]));
            employeeList.add(
                Employee(
                    i,
                    tableData[i][1],
                    tableData[i][2],
                    tableData[i][3],
                    null,
                    null,
                    tableData[i][4],
                    null,
                    Category.POWER_OUTAGE_CONTACT
                )
            )
        }
        //        Log.d(TAG, "insertPowerOutageContactFromTable: " + headerText + " " + footerText);
        mRepository.insertEmployeeList(employeeList)
    }

    val powerOutageContactList: LiveData<List<Employee>>
        get() = mRepository.powerOutageContactList
    val officeHead: LiveData<Employee>
        get() = mRepository.officeHead
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
