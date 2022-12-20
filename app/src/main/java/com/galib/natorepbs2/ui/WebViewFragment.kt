package com.galib.natorepbs2.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.galib.natorepbs2.R
import com.galib.natorepbs2.utils.Utility
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class WebViewFragment : Fragment(), CoroutineScope {
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    private val args: WebViewFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_web_view, container, false)
        val titleTextView = root.findViewById<TextView>(R.id.titleTextView)
        val mWebView = root.findViewById<WebView>(R.id.webView)
        val button = root.findViewById<Button>(R.id.openInBrowserButton)
        configureWebView(mWebView)
        val url: String? = args.url
        val html: String? = args.html
        val title: String = args.pageTitle
        val selector: String? = args.selector

        titleTextView.text = title
        launch(Dispatchers.IO) {
            loadData(mWebView, url, html, selector)
        }
        if(url != null) {
            button.visibility = View.VISIBLE
            button.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(browserIntent)
            }
        }
        return root
    }

    private suspend fun loadData(mWebView:WebView, url:String?, html:String?, selector:String?){
        if (selector != null && url != null) {
            val data = Utility.fetchHtml(url, selector)
            mWebView.post {
                if (data != null)
                    mWebView.loadData(data, "text/html; charset=utf-8", "UTF-8")
                else
                    mWebView.loadUrl(url)
            }
        } else if (url != null) {
            mWebView.post{mWebView.loadUrl(url)}
        } else if (html != null) {
            mWebView.post{mWebView.loadData(html, "text/html; charset=utf-8", "UTF-8")}
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configureWebView(mWebView: WebView) {
        val webSettings = mWebView.settings
        webSettings.javaScriptEnabled = true
        mWebView.webViewClient = WebViewClient()
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> WebSettingsCompat.setForceDark(
                    mWebView.settings,
                    WebSettingsCompat.FORCE_DARK_ON
                )
                Configuration.UI_MODE_NIGHT_NO, Configuration.UI_MODE_NIGHT_UNDEFINED -> WebSettingsCompat.setForceDark(
                    mWebView.settings,
                    WebSettingsCompat.FORCE_DARK_OFF
                )
            }
        }
    }
}