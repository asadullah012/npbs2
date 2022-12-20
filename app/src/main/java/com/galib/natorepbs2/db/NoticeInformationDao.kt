package com.galib.natorepbs2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoticeInformationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(noticeInformation: NoticeInformation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(noticeInformationList: List<NoticeInformation>)

    @Query("DELETE FROM notice_information_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM notice_information_table ORDER BY priority ASC")
    fun getAllNotice(): Flow<List<NoticeInformation>>

    @Query("SELECT * FROM notice_information_table WHERE type = :type ORDER BY priority ASC")
    fun getAllNoticeByCategory(type: String): Flow<List<NoticeInformation>>

    @Query("DELETE FROM notice_information_table WHERE type = :type")
    suspend fun deleteAllByCategory(type: String)
}