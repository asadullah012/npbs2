package com.galib.natorepbs2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.galib.natorepbs2.models.AccountByCC
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountByCCDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(accountByCCList: List<AccountByCC>)

    @Query("SELECT DISTINCT cc_name FROM account_by_cc_table WHERE account LIKE :account || '%'")
    fun getCCByAccount(account: String?): Flow<List<String>>

    @Query("DELETE FROM account_by_cc_table")
    suspend fun deleteAll()
}