package com.galib.natorepbs2.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_menu_table")
class MyMenuItem (n:String = "", isFav:Boolean=false){
    @ColumnInfo(name = "name") @PrimaryKey var name:String = n
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = isFav
}