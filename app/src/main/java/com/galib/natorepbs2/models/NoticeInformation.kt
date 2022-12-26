package com.galib.natorepbs2.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notice_information_table")
data class NoticeInformation (
    @ColumnInfo(name = "priority") val priority:Int,
    @ColumnInfo(name = "title") val title:String,
    @ColumnInfo(name = "url") val url:String,
    @ColumnInfo(name = "date") val date:String,
    @ColumnInfo(name = "pdf_url") val pdfUrl:String,
    @ColumnInfo(name = "type") val type:String
){
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}