package com.galib.natorepbs2.ui;

import static androidx.webkit.WebSettingsCompat.FORCE_DARK_OFF;
import static androidx.webkit.WebSettingsCompat.FORCE_DARK_ON;

import androidx.appcompat.app.AppCompatActivity;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.galib.natorepbs2.R;

import java.net.URI;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        TextView titleTextView = findViewById(R.id.titleTextView);
        WebView mWebView = findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        Button button = findViewById(R.id.openInBrowserButton);
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
                case Configuration.UI_MODE_NIGHT_YES:
                    WebSettingsCompat.setForceDark(mWebView.getSettings(), FORCE_DARK_ON);
                    break;
                case Configuration.UI_MODE_NIGHT_NO:
                case Configuration.UI_MODE_NIGHT_UNDEFINED:
                    WebSettingsCompat.setForceDark(mWebView.getSettings(), FORCE_DARK_OFF);
                    break;
            }
        }
        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE");
        titleTextView.setText(title);

        String url = intent.getStringExtra("URL");
        if(url != null){
            mWebView.loadUrl(url);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mWebView.getUrl()));
                startActivity(browserIntent);
            });
        }

        String html = intent.getStringExtra("HTML");
        if(html != null){
            mWebView.loadData(html, "text/html; charset=utf-8", "UTF-8");
        }

    }
}