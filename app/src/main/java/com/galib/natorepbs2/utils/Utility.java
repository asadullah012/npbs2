package com.galib.natorepbs2.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import com.galib.natorepbs2.constants.URLs;
import com.galib.natorepbs2.ui.WebActivity;

import java.util.List;

public class Utility {
    public static String TAG = Utility.class.getName();
    public static void openActivity(Context context, Class cls){
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
    public static void openWebActivity(Context context, String title, String url, String html){
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("URL", url);
        intent.putExtra("HTML", html);
        context.startActivity(intent);
    }

    public static String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo appInfo = packageManager.getPackageInfo("com.facebook.katana", 0);
            Log.d("NPBS2", "fb" + appInfo.versionCode);
            if (appInfo.versionCode >= 3002850) {
                return "fb://facewebmodal/f?href=" + URLs.FACEBOOK;
            } else {
                return "fb://page/" + URLs.FACEBOOK_APP_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("NPBS2", "fb app not found " + e.getLocalizedMessage());
            return URLs.FACEBOOK; //normal web url
        }
    }

    public static String bnDigitToEnDigit(String bnString){
        StringBuilder sb = new StringBuilder();
        for(char c : bnString.toCharArray()){
            if(c >= '০' && c <= '৯')
                sb.append(c - '০');
            else sb.append(c);
        }
        return sb.toString();
    }

    public static void openPlayStore(Context context, String appId){
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.android.vending", 0);
            Intent launchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appId));
            context.startActivity(new Intent(launchIntent).setPackage("com.android.vending"));
        } catch (PackageManager.NameNotFoundException e){
            Log.d(TAG, "openPlayStore: play store not found");
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appId)));
            } catch (android.content.ActivityNotFoundException anfe) {
                Log.d(TAG, "openPlayStore: " + anfe.getLocalizedMessage());
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URLs.PLAY_STORE_PREFIX + appId)));
            }
        }
    }
    public static String arrayToString(String []arr){
        if(arr == null) return null;
        StringBuilder sb = new StringBuilder();
        for(String s: arr) {
            sb.append(s);
            sb.append(' ');
        }
        return sb.toString();
    }
}
