package com.galib.natorepbs2.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_menu_table")
class MyMenuItem (n:String = "", p:Int = 0, isFav:Boolean=false){

    @ColumnInfo(name = "name") @PrimaryKey var name:String = n
    @ColumnInfo(name = "priority") var priority: Int = p
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = isFav
    companion object{
        fun populateData() : List<MyMenuItem>{
            return listOf(
                MyMenuItem("A", 0, true),
                MyMenuItem("B", 1, true),
                MyMenuItem("C", 2, false) )
        }
    }
}