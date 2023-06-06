package com.galib.natorepbs2.gsheet

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import java.io.IOException


class SpreadsheetManager {
    @Throws(IOException::class)
    fun readSheet(context: Context, spreadsheetId: String?, range: String?): List<List<Any>> {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        val googleAccountCredential = GoogleAccountCredential
            .usingOAuth2(context, listOf(SheetsScopes.SPREADSHEETS))
            .setBackOff(ExponentialBackOff())
        googleAccountCredential.selectedAccount = account?.account

        val spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms"
        val range = "Class Data!A2:E"

        val sheetService = Sheets.Builder(
            AndroidHttp.newCompatibleTransport(), JacksonFactory.getDefaultInstance(),
            googleAccountCredential)
            .setApplicationName("sheetTest")
            .build()

        val response = sheetService.spreadsheets().values().get(spreadsheetId, range).execute()
        return response.getValues()
    }
}