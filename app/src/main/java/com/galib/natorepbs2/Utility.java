package com.galib.natorepbs2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

public class Utility {
    public static void openActivity(Context context, Class cls){
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
    public static void openWebActivity(Context context, String title, String url){
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("URL", url);
        context.startActivity(intent);
    }

    public static String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            Log.d("NPBS2", "fb" + versionCode);
            if (versionCode >= 3002850) {
                return "fb://facewebmodal/f?href=" + context.getResources().getString(R.string.facebook_url);
            } else {
                return "fb://page/" + context.getResources().getString(R.string.facebook_page_id);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("NPBS2", "fb app not found");
            return context.getResources().getString(R.string.facebook_url); //normal web url
        }
    }

    public static void openPlayStore(Context context){
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getResources().getString(R.string.digital_phonebook_app_id))));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + context.getResources().getString(R.string.digital_phonebook_app_id))));
        }
    }
}
