package com.galib.natorepbs2.gsheet

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.api.services.sheets.v4.SheetsScopes


class SignInManager(private val activity: Activity, private val listener: SignInListener) {
    private var signInClient: GoogleSignInClient
    private var signInListener: SignInListener = listener

    interface SignInListener {
        fun onSignInSuccess(account: GoogleSignInAccount)
        fun onSignInFailure()
    }

    val TAG = "SignInManager"
    init {
        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("355140138292-qv79inhmdhq1g9gai9oi08mmqoplboc0.apps.googleusercontent.com")
            .requestEmail()
            .requestScopes(Scope(SheetsScopes.SPREADSHEETS))
            .requestScopes(Scope(SheetsScopes.SPREADSHEETS_READONLY))
            .build()
        signInClient = GoogleSignIn.getClient(activity, gso)
    }



    fun getSignInIntent(): Intent? {
        return signInClient.signInIntent
    }

    fun handleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            signInListener.onSignInSuccess(account)
        } catch (e: ApiException) {
            Log.e(TAG, "handleSignInResult: ${e.localizedMessage}" )
            signInListener.onSignInFailure()
        }
    }

    companion object {
        const val RC_SIGN_IN = 123
    }
}
