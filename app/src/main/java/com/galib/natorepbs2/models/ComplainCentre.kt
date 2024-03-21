package com.galib.natorepbs2.models

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "complain_center_table")
class ComplainCentre(
    @ColumnInfo(name = "priority") var priority: Int,
    @ColumnInfo(name = "name") @PrimaryKey var name: String,
    @ColumnInfo(name = "address") var address:String,
    @ColumnInfo(name = "mobile_no") var mobileNo: String,
    @ColumnInfo(name = "telephone_no") var telephoneNo: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "latitude") var latitude: Double,
    @ColumnInfo(name = "longitude") var longitude: Double
)