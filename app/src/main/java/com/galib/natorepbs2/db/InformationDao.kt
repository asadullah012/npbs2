package com.galib.natorepbs2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.galib.natorepbs2.models.Information
import kotlinx.coroutines.flow.Flow

@Dao
interface InformationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(info: Information?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfos(informations: List<Information>)

    @Query("DELETE FROM information_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM information_table where category = :category ORDER BY priority ASC")
    fun getInformationsByCategory(category: String?): Flow<List<Information>>

    @Query("SELECT * FROM information_table where category = :category ORDER BY priority ASC LIMIT 1")
    fun getInformationByCategory(category: String?): Flow<Information?>

    @get:Query("SELECT COUNT(*) FROM information_table")
    val count: Int

    @Query("DELETE FROM information_table where category = :category")
    fun deleteAllByCategory(category: String?)
}