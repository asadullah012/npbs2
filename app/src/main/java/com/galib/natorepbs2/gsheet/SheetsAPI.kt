package com.galib.natorepbs2.gsheet

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface SheetsAPI {
    @GET("spreadsheets/{spreadsheetId}/values/{range}")
    fun getData(
        @Path("spreadsheetId") spreadsheetId: String,
        @Path("range") range: String,
        @Query("key") apiKey: String
    ): Call<SheetData>
}