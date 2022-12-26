package com.galib.natorepbs2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.galib.natorepbs2.models.Employee
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(employee: Employee?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(employeeList: List<Employee>)

    @Query("DELETE FROM employee_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM employee_table where type = :type ORDER BY priority ASC")
    fun getAllByType(type: Int): Flow<List<Employee>>

    @Query("SELECT * FROM employee_table where type = :type ORDER BY priority LIMIT 1")
    fun getOfficeHead(type: Int): Flow<Employee>

    @Query("DELETE FROM employee_table where type = :type")
    suspend fun deleteByCategory(type: Int)
}