package com.galib.natorepbs2.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ComplainCentreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ComplainCentre complainCentre);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ComplainCentre> complainCentreList);

    @Query("SELECT * FROM complain_center_table ORDER BY priority ASC")
    LiveData<List<ComplainCentre>> getAll();

    @Query("DELETE FROM complain_center_table")
    void deleteAll();
}
