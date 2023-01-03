package com.galib.natorepbs2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.galib.natorepbs2.models.MyMenuItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MyMenuItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(myMenuItem: MyMenuItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(myMenuItemList: List<MyMenuItem>)

    @Query("DELETE FROM my_menu_table")
    suspend fun deleteAll()

    @Query("UPDATE my_menu_table SET is_favorite = :isFavorite where name = :name")
    suspend fun updateFavorite(name : String, isFavorite: Int)

    @get:Query("SELECT * FROM my_menu_table where is_favorite = 1")
    val favoriteMenu: Flow<List<MyMenuItem>>

    @get:Query("SELECT * FROM my_menu_table where is_favorite = 0")
    val availableMenu: Flow<List<MyMenuItem>>
}