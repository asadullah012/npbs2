package com.galib.natorepbs2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OfficeInformationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(officeInformation: OfficeInformation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(officeInformations: List<OfficeInformation>)

    @Query("DELETE FROM office_info_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM office_info_table")
    fun getAllOffice(): Flow<List<OfficeInformation>>
}
