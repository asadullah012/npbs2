package com.galib.natorepbs2.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "office_info_table")
data class OfficeInformation (
    @PrimaryKey
    @ColumnInfo(name = "name") val name :String,
    @ColumnInfo(name = "address") val address :String,
    @ColumnInfo(name = "mobile") val mobile :String,
    @ColumnInfo(name = "email") val email :String,
    @ColumnInfo(name = "google_map_url") val googleMapURL :String,
    @ColumnInfo(name = "priority") val priority :Int
)