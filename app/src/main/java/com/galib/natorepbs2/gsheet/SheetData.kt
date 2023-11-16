package com.galib.natorepbs2.gsheet

data class SheetData(
    val range: String,
    val majorDimension: String,
    val values: List<List<String>>
)
