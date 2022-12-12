package com.galib.natorepbs2.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.constants.Category;
import com.galib.natorepbs2.db.Employee;
import com.galib.natorepbs2.db.Information;
import com.galib.natorepbs2.db.NPBS2Repository;
import com.galib.natorepbs2.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class EmployeeViewModel extends AndroidViewModel {
    private static final String TAG = EmployeeViewModel.class.getName();
    private NPBS2Repository mRepository;
    LiveData<Information> headerText, footerText;
    public EmployeeViewModel(Application application){
        super(application);
        mRepository = new NPBS2Repository(application);
        headerText = mRepository.getPowerOutageHeaderText();
        footerText = mRepository.getPowerOutageFooterText();
    }

    public LiveData<List<Employee>> getOfficerList(){
        return mRepository.getOfficerList();
    }

    public LiveData<List<Employee>> getJuniorOfficerList(){
        return mRepository.getJuniorOfficerList();
    }

    public LiveData<List<Employee>> getBoardMemberList(){
        return mRepository.getBoardMemberList();
    }

    public void insertOfficersFromTable(List<List<String>> tableData){
        if(tableData == null) return;
        List<Employee> officersList = new ArrayList<>();
        for(int i =0; i<tableData.size(); i++){
            String s[] = tableData.get(i).get(2).split(", ");
            String designation = s[0], office;
            if(s.length == 1)
                office = "সদর দপ্তর";
            else
                office = s[1];
            officersList.add(new Employee(i, tableData.get(i).get(0), tableData.get(i).get(1), designation, office,
                    tableData.get(i).get(4), tableData.get(i).get(5), tableData.get(i).get(6), Category.OFFICERS));
            //Log.d(TAG, "insertFromTable: " + i + " " + tableData[i][0] + " " + tableData[i][1] + " " + designation + " " + office + " " + tableData[i][4] + " " + tableData[i][5] + " " + tableData[i][6] + " " + Category.OFFICERS);
        }
        mRepository.insertEmployeeList(officersList);
    }

    public void insertJuniorOfficerFromTable(List<List<String>> tableData) {
        if(tableData == null) return;
        List<Employee> employeeList = new ArrayList<>();
        String office = null;
        for(int i =0; i<tableData.size(); i++){
            //Log.d(TAG, "insertJuniorOfficerFromTable: " + Utility.arrayToString(tableData[i]));
            if(tableData.get(i).size() == 1){
                office = tableData.get(i).get(0);
                i++; // ignore header row
            }
            else
                employeeList.add(new Employee(i, tableData.get(i).get(1), tableData.get(i).get(2), tableData.get(i).get(3),
                        office, tableData.get(i).get(4), tableData.get(i).get(5),null, Category.JUNIOR_OFFICER));
        }
        mRepository.insertEmployeeList(employeeList);
    }

    public void insertBoardMembersFromTable(String[][] tableData) {
        if(tableData == null) return;
        List<Employee> employeeList = new ArrayList<>();
        for(int i =0; i<tableData.length; i++){
            if(tableData[i] == null) continue;
            //Log.d(TAG, "insertBoardMembersFromTable: " + Utility.arrayToString(tableData[i]));
            employeeList.add(new Employee(i, null, tableData[i][1], tableData[i][2],
                    tableData[i][3], null, tableData[i][4], null,Category.BOARD_MEMBER));
        }
        mRepository.insertEmployeeList(employeeList);
    }

    public void insertPowerOutageContactFromTable(String[][] tableData, String headerText, String footerText) {
        if(tableData == null) return;
        List<Employee> employeeList = new ArrayList<>();
        for(int i =0; i<tableData.length; i++){
            if(tableData[i] == null) continue;
            Log.d(TAG, "insertPowerOutageContactFromTable: " + Utility.arrayToString(tableData[i]));
            employeeList.add(new Employee(i, tableData[i][1], tableData[i][2],
                    tableData[i][3], null, null, tableData[i][4], null,Category.POWER_OUTAGE_CONTACT));
        }
//        Log.d(TAG, "insertPowerOutageContactFromTable: " + headerText + " " + footerText);
        mRepository.insertEmployeeList(employeeList);
        mRepository.insertInformation(new Information(0, getApplication().getString(R.string.menu_power_outage_contact), headerText, Category.powerOutageContactHeader));
        mRepository.insertInformation(new Information(0, getApplication().getString(R.string.menu_power_outage_contact), footerText, Category.powerOutageContactFooter));
    }

    public LiveData<List<Employee>> getPowerOutageContactList() {
        return mRepository.getPowerOutageContactList();
    }

    public LiveData<Employee> getOfficeHead() {
        return mRepository.getOfficeHead();
    }
}
