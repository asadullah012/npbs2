package com.galib.natorepbs2.gsheet

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.api.services.sheets.v4.SheetsScopes


class SignInManager(private val activity: Activity, private val tokenManager: TokenManager) {
    private val signInClient: GoogleSignInClient
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

    fun signIn() {
        //val account = GoogleSignIn.getLastSignedInAccount(this)
        val signInIntent = signInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun handleSignInResult(data: Intent?, tokenManager: TokenManager) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            // Save tokens to TokenManager
            val acc = account.account
            val accessToken = account.idToken
            val refreshToken = account.serverAuthCode
            tokenManager.saveTokens(accessToken, refreshToken)
        } catch (e: ApiException) {
            Log.e(TAG, "handleSignInResult: ${e.localizedMessage}" )
        }
    }

    fun refreshAccessTokenIfNeeded() {
        // Check if access token is expired or about to expire
        val accessToken = tokenManager.accessToken
        if (isTokenExpired(accessToken)) {
            val refreshToken = tokenManager.refreshToken
            // Call the token refresh API using the refresh token
            // Update the access token in TokenManager with the refreshed token
            // Save the updated access token to TokenManager
        }
    }

    private fun isTokenExpired(accessToken: String?): Boolean {
        // Implement your logic to check if the access token has expired or is about to expire
        // You can compare the expiration time with the current time and return true if expired, false otherwise
        return false // Placeholder, replace with your logic
    }

    companion object {
        private const val RC_SIGN_IN = 123
    }
}
