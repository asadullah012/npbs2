package com.galib.natorepbs2.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tender_information_table")
data class TenderInformation (
    @PrimaryKey
    @ColumnInfo(name = "priority") val priority:Int,
    @ColumnInfo(name = "title") val title:String,
    @ColumnInfo(name = "url") val url:String,
    @ColumnInfo(name = "date") val date:String,
    @ColumnInfo(name = "pdf_url") val pdfUrl:String
)