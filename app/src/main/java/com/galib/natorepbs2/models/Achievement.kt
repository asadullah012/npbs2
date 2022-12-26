package com.galib.natorepbs2.models

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "achievement_table")
data class Achievement(
    @ColumnInfo(name = "sl") val serial: String,
    @ColumnInfo(name = "title") @PrimaryKey val title: String,
    @ColumnInfo(name = "last_value") val lastValue: String,
    @ColumnInfo(name = "cur_value") val curValue: String,
    @ColumnInfo(name = "total_value") val totalValue: String,
    @ColumnInfo(name = "priority") val priority: Int
)