package com.galib.natorepbs2.sync

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.galib.natorepbs2.R
import com.galib.natorepbs2.utils.Utility
import org.json.JSONObject
import org.jsoup.Connection
import org.jsoup.Jsoup

object SyncConfig {
    private const val CONFIG_VERSION = "configVersion"
    private const val sharedPrefName = "SyncPrefs"

    private const val gistConfigApi = "https://api.github.com/gists/15ef3b96f6850fb2cf653ad96b5a417f"
    const val gistDataApi  = "https://api.github.com/gists/8af830cabedca188a82965b2d6b149a9"
    const val fileData = "npbs2_sync_data.json"
    private const val fileConfig = "npbs2_sync_config.json"
    private const val TAG = "SyncConfig"
    var failedAttempts = 0
    const val TIMEOUT = 1000

    var jsonUrls : JSONObject? = null
    var jsonSelectors:JSONObject? = null

    fun updateConfigFile(context: Context) {
        try {
            val response: Connection.Response = Jsoup.connect(gistConfigApi).ignoreContentType(true).execute()
            val jsonRootObject = JSONObject(response.body())
            val syncConfig = jsonRootObject.optJSONObject("files")?.optJSONObject(fileConfig)
                ?.getString("content")
            syncConfig?.let { Utility.writeToFile(it, fileConfig, context) }
            Log.d(TAG, "updateConfigFile: $syncConfig")
        } catch (e:java.lang.Exception){
            Log.e(TAG, "updateConfigFile: ${e.localizedMessage}")
        }
    }

    fun getConfigJson(context: Context): JSONObject?{
        var json = Utility.readFromFile(fileConfig, context) // try to read from file
        if(json == null){
            json = Utility.getJsonFromAssets(fileConfig, context.assets) // if not present in file, read from asset
        }
        if(json != null) return JSONObject(json)
        return null
    }

    fun getUrl(type : String, context: Context): String?{
        if(jsonUrls == null){
            val configJson = getConfigJson(context) ?: return null
            jsonUrls = configJson.optJSONObject("urls")
        }
        return jsonUrls?.optString(type)
    }

    fun getSelector(type : String, context: Context): String?{
        if(jsonSelectors == null){
            val configJson = getConfigJson(context) ?: return null
            jsonSelectors = configJson.optJSONObject("selectors")
        }
        return jsonSelectors?.optString(type)
    }

    fun getConfigVersion(context: Context): Int {
        val sharedPref: SharedPreferences =
            context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        return sharedPref.getInt(CONFIG_VERSION, 0)
    }

    fun setConfigVersion(context: Context, configVersion: Int){
        val sharedPref: SharedPreferences =
            context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putInt(CONFIG_VERSION, configVersion)
            apply()
        }
    }


    fun setLastSyncTime(context: Context, lastSyncTime: Long){
        val sharedPref: SharedPreferences =
            context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putLong(context.getString(R.string.last_update_time), lastSyncTime)
            apply()
        }
    }

    fun getLastSyncTime(context: Context):Long{
        val sharedPref: SharedPreferences =
            context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        return sharedPref.getLong(context.getString(R.string.last_update_time), 0L)
    }

    fun getSyncFailedAttempts():Int{
        return failedAttempts
    }

    fun setSyncFailedAttempts(attempts:Int){
        failedAttempts = attempts
    }
}