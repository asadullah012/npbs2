package com.galib.natorepbs2.gsheet

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes

object GAccountSignInHelper {
    const val TAG = "GAccountSignInHelper"
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN: Int = 2

    fun googleSignIn(activity: Activity){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("355140138292-qv79inhmdhq1g9gai9oi08mmqoplboc0.apps.googleusercontent.com")
            .requestEmail()
            .requestScopes(Scope(SheetsScopes.SPREADSHEETS))
            .requestScopes(Scope(SheetsScopes.SPREADSHEETS_READONLY))
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
        signIn(activity)
    }

    private fun signIn(activity: Activity) {
        val signInIntent = mGoogleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun onActivityResult(activity: Activity, resultCode: Int, data: Intent?){
        Log.d("RESULT_CODE", "$resultCode")
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        handleSignInResult(activity, task)
    }

    private fun handleSignInResult(activity: Activity, completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.d(TAG, "Sign in succesful ${account!!.displayName}")
            val googleAccountCredential = GoogleAccountCredential
                .usingOAuth2(activity.applicationContext, listOf(SheetsScopes.SPREADSHEETS))
                .setBackOff(ExponentialBackOff())
            googleAccountCredential.selectedAccount = account.account
            GetSheetData(googleAccountCredential).execute()
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            println("Sign in failed")
            e.printStackTrace()
        }
    }
    class GetSheetData(private val googleAccountCredential: GoogleAccountCredential): AsyncTask<Any, Void, MutableList<MutableList<Any>>?>() {
        override fun doInBackground(vararg params: Any?): MutableList<MutableList<Any>>? {

            val spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms"
            val range = "Class Data!A2:E"

            val sheetService = Sheets.Builder(
                AndroidHttp.newCompatibleTransport(), JacksonFactory.getDefaultInstance()
                , googleAccountCredential
            )
                .setApplicationName("sheetTest")
                .build()

            val response = sheetService.spreadsheets().values().get(spreadsheetId, range).execute()
            return response.getValues()
        }

        override fun onPostExecute(result: MutableList<MutableList<Any>>?) {
            super.onPostExecute(result)
            Log.d(TAG,"Final result $result")
        }

    }
}