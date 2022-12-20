package com.galib.natorepbs2.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import com.galib.natorepbs2.constants.URLs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class Utility {
    companion object{
        private var TAG = Utility::class.java.name
        @JvmStatic
        fun openActivity(context: Context, cls: Class<*>?) {
            val intent = Intent(context, cls)
            context.startActivity(intent)
        }

        @JvmStatic
        fun getFacebookPageURL(context: Context): String {
            val packageManager = context.packageManager
            return try {
                val appInfo = packageManager.getPackageInfo("com.facebook.katana", 0)
                Log.d("NPBS2", "fb" + appInfo.versionCode)
                if (appInfo.versionCode >= 3002850) {
                    "fb://facewebmodal/f?href=" + URLs.FACEBOOK
                } else {
                    "fb://page/" + URLs.FACEBOOK_APP_ID
                }
            } catch (e: PackageManager.NameNotFoundException) {
                Log.d("NPBS2", "fb app not found " + e.localizedMessage)
                URLs.FACEBOOK //normal web url
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
            val df = SimpleDateFormat(format)
            val date: Date = df.parse(dateTime)
            return date.time
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
                        Uri.parse(URLs.PLAY_STORE_PREFIX + appId)
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

        @JvmStatic
        fun arrayToString(arr: Array<String>): String? {
            if (arr == null) return null
            val sb = StringBuilder()
            for (s in arr) {
                sb.append(s)
                sb.append(' ')
            }
            return sb.toString()
        }
    }
}