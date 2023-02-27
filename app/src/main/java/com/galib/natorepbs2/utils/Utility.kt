package com.galib.natorepbs2.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.galib.natorepbs2.sync.SyncConfig
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSink
import okio.Okio
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class Utility {
    companion object{
        private var TAG = Utility::class.java.name

        @JvmStatic
        fun getFacebookPageURL(context: Context, url:String): String {
            val packageManager = context.packageManager
            return try {
                val appInfo = packageManager.getPackageInfo("com.facebook.katana", 0)
                Log.d("NPBS2", "fb" + appInfo.versionCode)
                if (appInfo.versionCode >= 3002850) {
                    "fb://facewebmodal/f?href=$url"
                } else {
                    url
                }
            } catch (e: PackageManager.NameNotFoundException) {
                Log.d("NPBS2", "fb app not found " + e.localizedMessage)
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
                Log.e(TAG, "dateStringToEpoch: $dateTime $format")
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
                    Log.d(TAG, "openMap: " + innerEx.localizedMessage)
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
                    Log.d(TAG, "openMap: " + innerEx.localizedMessage)
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
                //            Log.d(TAG, "openPlayStore: play store not found. Trying to find other market apps");
//            try {
//                Intent storeIntent = new Intent(Intent.ACTION_VIEW, marketUri);
//                context.startActivity(storeIntent);
//            } catch (android.content.ActivityNotFoundException anfe) {
//                Log.d(TAG, "openPlayStore: no market apps found. opening browser " + anfe.getLocalizedMessage());
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
                    Log.d(TAG, "fetchHtml: " + e.localizedMessage)
                }
                if (document != null) {
                    html = document.select(selector).html()
                }
                return@async html
            }.await()
        }

        fun downloadAndSave(bannerUrl: String, name: String, bannerDir: File) {
            val file = File(bannerDir, name)
            if(file.exists()) return
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url(bannerUrl).build()
                val response: Response = client.newCall(request).execute()
                val body: ResponseBody? = response.body()
                val source = body!!.source()

                val sink: BufferedSink = Okio.buffer(Okio.sink(file))
                val sinkBuffer: Buffer = sink.buffer()

                val bufferSize: Long = 8 * 1024
                var bytesRead: Long = source.read(sinkBuffer, bufferSize)
                while (bytesRead != -1L) {
                    sink.emit()
                    bytesRead = source.read(sinkBuffer, bufferSize)
                }
                sink.flush()
                sink.close()
                source.close()
                Log.d(TAG, "Downloading... ${file.absolutePath}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun getInitDataJson(filename:String, context: Context, assetManager: AssetManager) : String?{
            var json = getJsonFromFile(filename, context)
            if(json == null){
                json = getJsonFromAssets(filename, assetManager)
            }
            return json
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

        fun getConsumerClass() : List<String>{
            val list : MutableList<String> = ArrayList()
            list.add("এলটি-এ: আবাসিক")
            list.add("এলটি-বি: সেচ/কৃষিকাজে ব্যবহৃত পাম্প")
            list.add("এলটি-সি ১: ক্ষুদ্র শিল্প")
            list.add("এলটি-সি ২: নির্মাণ")
            list.add("এলটি-ডি ১: শিক্ষা, ধর্মীয় ও দাতব্য প্রতিষ্ঠান এবং হাসপাতাল")
            list.add("এলটি-ডি ২: রাস্তার বাতি ও পানির পাম্প")
            list.add("এলটি-ডি ৩: ব্যাটারি চার্জিং স্টেশন")
            list.add("এলটি-ই: বানিজ্যিক ও অফিস")
            list.add("এলটি-টি: অস্থায়ী")
            list.add("এমটি-১: আবাসিক")
            list.add("এমটি-২: বানিজ্যিক ও অফিস")
            list.add("এমটি-৩: শিল্প")
            list.add("এমটি-৪: নির্মাণ")
            list.add("এমটি-৫: সাধারণ")
            list.add("এমটি-৬: অস্থায়ী")
            list.add("এমটি-৭: ব্যাটারি চার্জিং স্টেশন")
            list.add("এমটি-৮: সেচ/কৃষিকাজে ব্যবহৃত পাম্প")
            list.add("এইচটি -১: সাধারণ")
            list.add("এইচটি-২: বানিজ্যিক ও অফিস")
            list.add("এইচটি-৩: শিল্প")
            list.add("এইচটি-৪: নির্মাণ")
            list.add("ইএইচটি -১: সাধারণ (২০ মে.ও. থেকে অনূর্ধ্ব ১৪০ মে.ও.)")
            list.add("ইএইচটি -২: সাধারণ (১৪০ মে.ও. এর উর্ধ্বে)")
            return list
        }

        fun getTariffHtml(assetManager: AssetManager): String? {
            val json = getJsonFromAssets("npbs2_sync_data.json", assetManager) ?: return null
            val jsonRootObject = JSONObject(json)
            return jsonRootObject.getString("electricity_tariff")
        }

        fun getConnectionRulesHtml(assetManager: AssetManager): String? {
            val json = getJsonFromAssets("npbs2_sync_data.json", assetManager) ?: return null
            val jsonRootObject = JSONObject(json)
            return jsonRootObject.getString("electricity_connection_rules")
        }

        fun getHowToGetServiceHtml(assetManager: AssetManager): String? {
            val json = getJsonFromAssets("npbs2_sync_data.json", assetManager) ?: return null
            val jsonRootObject = JSONObject(json)
            return jsonRootObject.getString("how_to_get_service")
        }

        fun loadImageInPicasso(sampleImages: List<String>, position: Int, imageView:ImageView, resources:Resources) {
            Picasso.get()
                .load(if (sampleImages[position] == null || sampleImages[position].isEmpty()) null else sampleImages[position])
                .into(imageView,  object: Callback {
                    override fun onSuccess() {
                        val src = (imageView.drawable as BitmapDrawable).bitmap
                        val dr = RoundedBitmapDrawableFactory.create(resources, src)
                        dr.cornerRadius = src.height/10.0F
                        imageView.setImageDrawable(dr)
                    }

                    override fun onError(e: java.lang.Exception?) {
                        e?.printStackTrace()
                    }
                })
        }

        fun makeCall(context: Context, mobile: String) {
            val intent = Intent(
                Intent.ACTION_DIAL, Uri.parse("tel:" + bnDigitToEnDigit(mobile))
            )
            context.startActivity(intent)
        }

        fun getHtmlFromAsset(assets: AssetManager?, file_path: String, object_name: String): String? {
            val json = assets?.let { getJsonFromAssets(file_path, it) } ?: return null
            val jsonRootObject = JSONObject(json)
            return jsonRootObject.getString(object_name)
        }

        fun writeToFile(data: String, filename: String, context: Context) {
            val path = context.filesDir
            val file = File(path, filename)
            file.createNewFile()
            Log.d(TAG, "writeToFile: ${file.absolutePath}")
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
                Log.d(TAG, "readFromFile: ${file.absolutePath} not found")
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