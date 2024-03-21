package com.galib.natorepbs2.models

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "account_by_cc_table")
class AccountByCC(
    @ColumnInfo(name = "cc_name") var ccName: String,
    @ColumnInfo(name = "account") @PrimaryKey var account: String
){
    override fun toString() = "$account $ccName"
}