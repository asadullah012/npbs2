package com.galib.natorepbs2.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoticeInformationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(noticeInformation: NoticeInformation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(noticeInformationList: List<NoticeInformation>)

    @Query("DELETE FROM notice_information_table")
    fun deleteAll()

    @Query("SELECT * FROM notice_information_table ORDER BY priority ASC")
    fun getAllNotice(): LiveData<List<NoticeInformation>>

    @Query("SELECT * FROM notice_information_table WHERE type = :type ORDER BY priority ASC")
    fun getAllNoticeByCategory(type: String): LiveData<List<NoticeInformation>>

    @Query("DELETE FROM notice_information_table WHERE type = :type")
    fun deleteAllByCategory(type: String)
}