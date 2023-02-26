package com.galib.natorepbs2.sync

import android.content.Context
import android.content.SharedPreferences
import com.galib.natorepbs2.R
import com.galib.natorepbs2.utils.Utility

object SyncConfig {
    var failedAttempts = 0
    const val TIMEOUT = 1000
    private fun syncInitData() {
        Utility.downloadContent()
    }

    fun setLastSyncTime(context: Context, lastSyncTime: Long){
        val sharedPref: SharedPreferences =
            context.getSharedPreferences("SyncPrefs", Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putLong(context.getString(R.string.last_update_time), lastSyncTime)
            apply()
        }
    }

    fun getLastSyncTime(context: Context):Long{
        val sharedPref: SharedPreferences =
            context.getSharedPreferences("SyncPrefs", Context.MODE_PRIVATE)
        return sharedPref.getLong(context.getString(R.string.last_update_time), 0L)
    }

    fun getSyncFailedAttempts():Int{
        return failedAttempts;
    }

    fun setSyncFailedAttempts(attempts:Int){
        failedAttempts = attempts
    }
}