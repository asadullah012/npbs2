package com.galib.natorepbs2.ui;

import static androidx.webkit.WebSettingsCompat.FORCE_DARK_OFF;
import static androidx.webkit.WebSettingsCompat.FORCE_DARK_ON;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.galib.natorepbs2.R;

public class WebViewFragment extends Fragment {

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_web_view, container, false);
        TextView titleTextView = root.findViewById(R.id.titleTextView);
        WebView mWebView = root.findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        Button button = root.findViewById(R.id.openInBrowserButton);
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
        String url = null;
        String html = null;
        String title = null;
        if(getArguments() != null){
            url = WebViewFragmentArgs.fromBundle(getArguments()).getUrl();
            html = WebViewFragmentArgs.fromBundle(getArguments()).getHtml();
            title = WebViewFragmentArgs.fromBundle(getArguments()).getPageTitle();
        }

        titleTextView.setText(title);
        if(url != null){
            mWebView.loadUrl(url);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mWebView.getUrl()));
                startActivity(browserIntent);
            });
        }
        if(html != null){
            mWebView.loadData(html, "text/html; charset=utf-8", "UTF-8");
        }
        return root;
    }
}