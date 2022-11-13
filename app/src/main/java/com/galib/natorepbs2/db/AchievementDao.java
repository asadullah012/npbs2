package com.galib.natorepbs2.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AchievementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Achievement achievement);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Achievement> achievements);

    @Query("DELETE FROM achievement_table")
    void deleteAll();

    @Query("SELECT * FROM achievement_table ORDER BY priority ASC")
    LiveData<List<Achievement>> getAll();

    @Query("SELECT COUNT(*) FROM achievement_table")
    int getCount();


}
