package com.galib.natorepbs2.gsheet

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


class TokenManager(context: Context?) {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun saveTokens(accessToken: String?, refreshToken: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_ACCESS_TOKEN, accessToken)
        editor.putString(KEY_REFRESH_TOKEN, refreshToken)
        editor.apply()
    }

    val accessToken: String?
        get() = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    val refreshToken: String?
        get() = sharedPreferences.getString(KEY_REFRESH_TOKEN, null)

    fun clearTokens() {
        val editor = sharedPreferences.edit()
        editor.remove(KEY_ACCESS_TOKEN)
        editor.remove(KEY_REFRESH_TOKEN)
        editor.apply()
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }
}
