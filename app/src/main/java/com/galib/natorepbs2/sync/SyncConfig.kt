package com.galib.natorepbs2.sync

import android.content.Context
import android.content.SharedPreferences
import com.galib.natorepbs2.logger.LogUtils
import com.galib.natorepbs2.R
import com.galib.natorepbs2.utils.Utility
import org.json.JSONObject
import org.jsoup.Connection
import org.jsoup.Jsoup

object SyncConfig {
    private const val CONFIG_VERSION = "configVersion"
    private const val sharedPrefName = "SyncPrefs"

    private const val gistConfigApi = "https://api.github.com/gists/15ef3b96f6850fb2cf653ad96b5a417f"
    private const val gistDataApi  = "https://api.github.com/gists/8af830cabedca188a82965b2d6b149a9"
    private const val fileData = "npbs2_sync_data.json"
    private const val fileConfig = "npbs2_sync_config.json"
    private const val TAG = "SyncConfig"
    private var failedAttempts = 0
    const val TIMEOUT = 3000

    private var jsonUrls : JSONObject? = null
    private var jsonSelectors:JSONObject? = null

    fun updateConfigFile(context: Context) {
        try {
            val response: Connection.Response = Jsoup.connect(gistConfigApi).ignoreContentType(true).execute()
            val jsonRootObject = JSONObject(response.body())
            val syncConfig = jsonRootObject.optJSONObject("files")?.optJSONObject(fileConfig)
                ?.getString("content")
            syncConfig?.let {
                Utility.writeToFile(it, fileConfig, context)
                jsonUrls = null
                jsonSelectors = null
            }
            updateDataFileIfRequired(context)
        } catch (e:java.lang.Exception){
            LogUtils.e(TAG, "updateConfigFile: ${e.localizedMessage}")
        }
    }

    fun updateDataFileIfRequired(context: Context){
        val configJson = getConfigJson(context)
        val dataJson = getSyncDataJson(context)
        if(configJson != null && dataJson != null){
            val dataVersionInConfig = configJson.optInt("dataFileVersion")
            val dataVersion = dataJson.optInt("version")
            LogUtils.d(TAG, "updateDataFile: $dataVersionInConfig $dataVersion")
            if(dataVersionInConfig > dataVersion) {
                updateDataFile(context)
            }
        }
    }

    fun updateDataFile(context: Context) {
        try {
            val response: Connection.Response = Jsoup.connect(gistDataApi).ignoreContentType(true).execute()
            val jsonRootObject = JSONObject(response.body())
            val syncData = jsonRootObject.optJSONObject("files")?.optJSONObject(fileData)
                ?.getString("content")
            syncData?.let {
                Utility.writeToFile(it, fileData, context)
            }
        } catch (e:java.lang.Exception){
            LogUtils.e(TAG, "updateDataFile: ${e.localizedMessage}")
        }
    }

    private fun getConfigJson(context: Context): JSONObject?{
        var json = Utility.readFromFile(fileConfig, context) // try to read from file
        if(json == null){
            json = Utility.getJsonFromAssets(fileConfig, context.resources.assets) // if not present in file, read from asset
        }
        if(json != null) return JSONObject(json)
        return null
    }

    fun getSyncDataJson(context: Context): JSONObject?{
        var json = Utility.readFromFile(fileData, context) // try to read from file
        if(json == null){
            json = Utility.getJsonFromAssets(fileData, context.assets) // if not present in file, read from asset
        }
        if(json != null) return JSONObject(json)
        return null
    }

    fun getUrl(type : String, context: Context): String{
        if(jsonUrls == null){
            val configJson = getConfigJson(context) ?: return ""
            jsonUrls = configJson.optJSONObject("urls")
        }
        if(jsonUrls != null)
            return jsonUrls!!.optString(type)
        return ""
    }

    fun getSelector(type : String, context: Context): String{
        if(jsonSelectors == null){
            val configJson = getConfigJson(context) ?: return ""
            jsonSelectors = configJson.optJSONObject("selectors")
        }
        if(jsonSelectors != null)
            return jsonSelectors!!.optString(type)
        return ""
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