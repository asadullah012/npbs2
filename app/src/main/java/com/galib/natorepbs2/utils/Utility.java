package com.galib.natorepbs2.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import com.galib.natorepbs2.constants.URLs;
import com.galib.natorepbs2.ui.WebActivity;

import java.util.Locale;

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

    public static void openMap(Context context, String loc, Double destLong, Double destLat){
        String uri = String.format(Locale.ENGLISH, "https://www.google.com/maps/place/%s/@%f,%f", loc, destLat, destLong);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        try {
            context.startActivity(intent);
        } catch(ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(unrestrictedIntent);
            } catch(ActivityNotFoundException innerEx) {
                Log.d(TAG, "openMap: " + innerEx.getLocalizedMessage());
            }
        }
    }

    public static void openPlayStore(Context context, String appId){
        Uri marketUri = Uri.parse("market://details?id=" + appId);
        Intent playStoreIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        playStoreIntent.setPackage("com.android.vending");
        if (playStoreIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(playStoreIntent);
        } else {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URLs.PLAY_STORE_PREFIX + appId)));
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
