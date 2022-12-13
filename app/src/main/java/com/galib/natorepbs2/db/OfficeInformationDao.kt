package com.galib.natorepbs2.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OfficeInformationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(officeInformation: OfficeInformation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(officeInformations: List<OfficeInformation>)

    @Query("DELETE FROM office_info_table")
    fun deleteAll()

    @Query("SELECT * FROM office_info_table")
    fun getAllOffice(): LiveData<List<OfficeInformation>>
}
