package com.galib.natorepbs2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.galib.natorepbs2.models.Interruption
import kotlinx.coroutines.flow.Flow

@Dao
interface InterruptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(interruption: Interruption)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(interruptionList: List<Interruption>)

    @get:Query("SELECT * FROM interruptions_table ORDER BY creation_time DESC")
    val allInterruption: Flow<List<Interruption>>

    @Query("SELECT * FROM interruptions_table where office = :office ORDER BY creation_time DESC")
    fun getAllInterruptionByOffice(office:String): Flow<List<Interruption>>

    @Query("DELETE FROM interruptions_table")
    suspend fun deleteAll()
}