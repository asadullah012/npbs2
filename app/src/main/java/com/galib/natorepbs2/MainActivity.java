package com.galib.natorepbs2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.aboutUsBtn).setOnClickListener(v -> openAboutUsActivity());
        //setButtonActions();
    }

    private void openAboutUsActivity() {
        Intent intent = new Intent(this, AboutUsActivity.class);
        startActivity(intent);
    }

    private void setButtonActions() {
        findViewById(R.id.aboutButton).setOnClickListener( v -> openAtAGlanceActivity());
        findViewById(R.id.complainCentreButton).setOnClickListener(v -> openComplainCentreActivity());
        findViewById(R.id.complainPBSButton).setOnClickListener(v ->
                openWebActivity(getResources().getString(R.string.complain_pbs),
                        getResources().getString(R.string.complain_pbs_url))
        );
        findViewById(R.id.newConnectionButton).setOnClickListener(v ->
                openWebActivity(getResources().getString(R.string.new_connection),
                        getResources().getString(R.string.new_connection_url))
        );
        findViewById(R.id.newConnectionIndustryButton).setOnClickListener(v ->
                openWebActivity(getResources().getString(R.string.new_connection_industry),
                        getResources().getString(R.string.new_connection_industry_url))
        );
        findViewById(R.id.onlineBillCollectionButton).setOnClickListener(v -> openWebActivity(getResources().getString(R.string.online_bill_payment),
                getResources().getString(R.string.online_bill_payment_url)));
        findViewById(R.id.consumerSMSButton).setOnClickListener(v -> openConsumerSMSActivity());
        findViewById(R.id.websiteButton).setOnClickListener(v ->
                openWebActivity(getResources().getString(R.string.website),
                        getResources().getString(R.string.website_url)));
        findViewById(R.id.facebookButton).setOnClickListener(v ->
                openWebActivity(getResources().getString(R.string.facebook), getFacebookPageURL(this)));
        findViewById(R.id.phoneBookAppButton).setOnClickListener(v -> openPlayStore());
        findViewById(R.id.nothiButton).setOnClickListener(v ->
                openWebActivity(getResources().getString(R.string.nothi),
                        getResources().getString(R.string.nothi_url)));
        findViewById(R.id.grsButton).setOnClickListener(v ->
                openWebActivity(getResources().getString(R.string.grs),
                        getResources().getString(R.string.grs_url)));
    }

    private void openAtAGlanceActivity(){
        Intent intent = new Intent(this, AtAGlanceActivity.class);
        startActivity(intent);
    }

    private void openComplainCentreActivity(){
        Intent intent = new Intent(this, ComplainCentreActivity.class);
        startActivity(intent);
    }

    private void openConsumerSMSActivity() {
        Intent intent = new Intent(this, ConsumerSMSActivity.class);
        startActivity(intent);
    }

    protected void openWebActivity(String title, String url){
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("URL", url);
        startActivity(intent);
    }

    protected String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            Log.d("NPBS2", "fb" + versionCode);
            if (versionCode >= 3002850) {
                return "fb://facewebmodal/f?href=" + getResources().getString(R.string.facebook_url);
            } else {
                return "fb://page/" + getResources().getString(R.string.facebook_page_id);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("NPBS2", "fb app not found");
            return getResources().getString(R.string.facebook_url); //normal web url
        }
    }

    protected void openPlayStore(){
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getResources().getString(R.string.digital_phonebook_app_id))));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getResources().getString(R.string.digital_phonebook_app_id))));
        }
    }
}