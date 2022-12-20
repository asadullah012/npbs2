package com.galib.natorepbs2.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee_table")
data class Employee(
    @ColumnInfo(name = "priority") var priority: Int,
    @ColumnInfo(name = "image_url") var imageUrl: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "designation") var designation: String,
    @ColumnInfo(name = "office") var office: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "mobile") @PrimaryKey var mobile: String,
    @ColumnInfo(name = "phone") var phone: String,
    @ColumnInfo(name = "type") var type: Int
)