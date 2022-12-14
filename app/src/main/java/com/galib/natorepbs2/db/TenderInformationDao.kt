package com.galib.natorepbs2.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TenderInformationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tenderInformation: TenderInformation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tenderInformationList: List<TenderInformation>)

    @Query("DELETE FROM tender_information_table")
    fun deleteAll()

    @Query("SELECT * FROM tender_information_table ORDER BY priority ASC")
    fun getAllTender(): LiveData<List<TenderInformation>>
}