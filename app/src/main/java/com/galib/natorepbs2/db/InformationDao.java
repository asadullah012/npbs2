package com.galib.natorepbs2.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface InformationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Information info);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInfos(List<Information> informations);

    @Query("DELETE FROM information_table")
    void deleteAll();

    @Query("SELECT * FROM information_table where category = :category ORDER BY priority ASC")
    LiveData<List<Information>> getInformationByCategory(String category);

    @Query("SELECT COUNT(*) FROM information_table")
    int getCount();

    @Query("DELETE FROM information_table where category = :category")
    void deleteAllByCategory(String category);
}
