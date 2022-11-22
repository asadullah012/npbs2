package com.galib.natorepbs2.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Employee employee);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<Employee> employeeList);

    @Query("DELETE FROM employee_table")
    void deleteAll();

    @Query("SELECT * FROM employee_table where type = :type ORDER BY priority ASC")
    LiveData<List<Employee>> getAllByType(int type);

    @Query("SELECT * FROM employee_table where type = :type ORDER BY priority LIMIT 1")
    LiveData<Employee> getOfficeHead(int type);
}

