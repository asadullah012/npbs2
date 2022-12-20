package com.galib.natorepbs2.db

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "information_table")
data class Information(
    @ColumnInfo(name = "priority") var priority: Int,
    @ColumnInfo(name = "title") @PrimaryKey var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "category") var category: String
)