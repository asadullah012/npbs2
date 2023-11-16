package com.galib.natorepbs2.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interruptions_table")
data class Interruption(
    @ColumnInfo(name = "id") @PrimaryKey var id: Int,
    @ColumnInfo(name = "creation_time") var creationTime: String,
    @ColumnInfo(name = "office") var office: String,
    @ColumnInfo(name = "feeder") var feeder:String,
    @ColumnInfo(name = "area") var area: String,
    @ColumnInfo(name = "start_time") var startTime: String,
    @ColumnInfo(name = "end_time") var endTime: String,
    @ColumnInfo(name = "reason") var reason: String,
    @ColumnInfo(name = "read_status") var readStatus:Boolean,
)