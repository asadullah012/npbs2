package com.galib.natorepbs2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.galib.natorepbs2.models.ComplainCentre
import kotlinx.coroutines.flow.Flow

@Dao
interface ComplainCentreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(complainCentre: ComplainCentre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(complainCentreList: List<ComplainCentre>)

    @get:Query("SELECT * FROM complain_center_table ORDER BY priority ASC")
    val allComplainCentre: Flow<List<ComplainCentre>>

    @Query("DELETE FROM complain_center_table")
    suspend fun deleteAll()
}