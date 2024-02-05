package com.galib.natorepbs2.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.net.Uri
import com.galib.natorepbs2.logger.LogUtils
import com.galib.natorepbs2.sync.SyncConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Utility {
    companion object{
        private var TAG = Utility::class.java.name

        @JvmStatic
        fun getFacebookPageURL(context: Context, url:String): String {
            val packageManager = context.packageManager
            return try {
                val appInfo = packageManager.getPackageInfo("com.facebook.katana", 0)
                LogUtils.d("NPBS2", "fb" + appInfo.versionCode)
                if (appInfo.versionCode >= 3002850) {
                    "fb://facewebmodal/f?href=$url"
                } else {
                    url
                }
            } catch (e: PackageManager.NameNotFoundException) {
                LogUtils.d("NPBS2", "fb app not found " + e.localizedMessage)
                url //normal web url
            }
        }

        @JvmStatic
        fun bnDigitToEnDigit(bnString: String): String {
            val sb = StringBuilder()
            for (c in bnString.toCharArray()) {
                if (c in '০'..'৯') sb.append(c - '০') else sb.append(c)
            }
            return sb.toString()
        }

        @JvmStatic
        fun dateStringToEpoch(dateTime: String, format: String): Long {
            try {
                val df = SimpleDateFormat(format, Locale.ENGLISH)
                val date: Date? = df.parse(dateTime)
                return date?.time ?: 0L
            } catch (e : IllegalArgumentException){
                LogUtils.e(TAG, "dateStringToEpoch: $dateTime $format")
                e.printStackTrace()
            }
            return 0L
        }

        @JvmStatic
        fun openMap(context: Context, loc: String?, destLong: Double?, destLat: Double?) {
            val uri = String.format(
                Locale.ENGLISH,
                "https://www.google.com/maps/place/%s/@%f,%f",
                loc,
                destLat,
                destLong
            )
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setPackage("com.google.android.apps.maps")
            try {
                context.startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                try {
                    val unrestrictedIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    context.startActivity(unrestrictedIntent)
                } catch (innerEx: ActivityNotFoundException) {
                    LogUtils.d(TAG, "openMap: " + innerEx.localizedMessage)
                }
            }
        }

        @JvmStatic
        fun openMap(context: Context, url: String){
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.setPackage("com.google.android.apps.maps")
            try {
                context.startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                try {
                    val unrestrictedIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(unrestrictedIntent)
                } catch (innerEx: ActivityNotFoundException) {
                    LogUtils.d(TAG, "openMap: " + innerEx.localizedMessage)
                }
            }
        }

        @JvmStatic
        fun openPlayStore(context: Context, appId: String) {
            val marketUri = Uri.parse("market://details?id=$appId")
            val playStoreIntent = Intent(Intent.ACTION_VIEW, marketUri)
            playStoreIntent.setPackage("com.android.vending")
            if (playStoreIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(playStoreIntent)
            } else {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(SyncConfig.getUrl("PLAY_STORE_PREFIX",context) + appId)
                    )
                )
                //            LogUtils.d(TAG, "openPlayStore: play store not found. Trying to find other market apps");
//            try {
//                Intent storeIntent = new Intent(Intent.ACTION_VIEW, marketUri);
//                context.startActivity(storeIntent);
//            } catch (android.content.ActivityNotFoundException anfe) {
//                LogUtils.d(TAG, "openPlayStore: no market apps found. opening browser " + anfe.getLocalizedMessage());
//                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URLs.PLAY_STORE_PREFIX + appId)));
//            }
            }
        }

        suspend fun fetchHtml(url: String, selector:String): String? {
            return GlobalScope.async(Dispatchers.IO) {
                var document: Document? = null
                var html: String? = null
                try {
                    document = Jsoup.connect(url).get()
                } catch (e: IOException) {
                    LogUtils.d(TAG, "fetchHtml: " + e.localizedMessage)
                }
                if (document != null) {
                    html = document.select(selector).html()
                }
                return@async html
            }.await()
        }

        fun getJsonFromAssets(filename:String, assetManager: AssetManager): String?{
            var json: String? = null
            try {
                val inputStream: InputStream = assetManager.open(filename)
                val size: Int = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                json = buffer.toString(Charsets.UTF_8)
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            return json
        }

        fun getJsonFromFile(filename:String, context:Context): String?{
            var json: String? = null
            try {
                val inputStream: InputStream = File(context.filesDir, filename).inputStream()
                val size: Int = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                json = buffer.toString(Charsets.UTF_8)
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            return json
        }

        fun makeCall(context: Context, mobile: String) {
            val intent = Intent(
                Intent.ACTION_DIAL, Uri.parse("tel:" + bnDigitToEnDigit(mobile))
            )
            context.startActivity(intent)
        }
        fun sendEmail(context: Context, email: String) {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
            context.startActivity(intent)
        }

        fun getStringFromSyncData(context: Context, object_name:String):String?{
            val jsonRootObject = SyncConfig.getSyncDataJson(context)
            val data = jsonRootObject?.optString(object_name)
            LogUtils.d(TAG, "getStringFromSyncData: $data")
            return data
        }

        fun writeToFile(data: String, filename: String, context: Context) {
            val path = context.filesDir
            val file = File(path, filename)
            file.createNewFile()
            LogUtils.d(TAG, "writeToFile: ${file.absolutePath}")
            val outStream = FileOutputStream(file)
            outStream.use { stream ->
                stream.write(data.toByteArray())
            }
        }

        fun readFromFile(filename: String, context: Context): String?{
            val path = context.filesDir
            val file = File(path, filename)
            if(file.exists()){
                val bytes = ByteArray(file.length().toInt())
                val inputStream = FileInputStream(file)
                inputStream.use { stream ->
                    stream.read(bytes)
                }
                return String(bytes, Charsets.UTF_8)
            } else {
                LogUtils.d(TAG, "readFromFile: ${file.absolutePath} not found")
            }
            return null
        }

        fun getElectricityRate(context: Context, tariff: String, slab: Int): Double {
            val syncDataJson = SyncConfig.getSyncDataJson(context)
            return (syncDataJson?.optJSONObject("tariff")?.optJSONArray(tariff)?.getDouble(slab) ?: 0) as Double
        }

        fun getDemandCharge(context: Context, tariff: String): Double {
            val syncDataJson = SyncConfig.getSyncDataJson(context)
            return (syncDataJson?.optJSONObject("demand_charge")?.optDouble(tariff) ?: 0) as Double
        }

    }
}