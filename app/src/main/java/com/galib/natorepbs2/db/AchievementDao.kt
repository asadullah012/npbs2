package com.galib.natorepbs2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.galib.natorepbs2.models.Achievement
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(achievement: Achievement)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(achievements: List<Achievement>)

    @Query("DELETE FROM achievement_table")
    suspend fun deleteAll()

    @get:Query("SELECT * FROM achievement_table ORDER BY priority ASC")
    val all: Flow<List<Achievement>>

    @get:Query("SELECT COUNT(*) FROM achievement_table")
    val count: Int
}