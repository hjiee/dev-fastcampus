package com.hjiee.fastcampus.part2.chapter8


import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.hjiee.fastcampus.databinding.ActivityPart2Chapter8Binding

class Chapter8Activity : AppCompatActivity() {

    private val binding by lazy { ActivityPart2Chapter8Binding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        bindView()
    }

    override fun onBackPressed() {
        if (binding.webview.canGoBack()) {
            binding.webview.goBack()
        } else {
            super.onBackPressed()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        binding.apply {
            with(webview) {
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
                settings.javaScriptEnabled = true
                loadUrl(DEFAULT_URL)
            }
        }
    }

    private fun bindView() {
        binding.run {
            addressbar.setOnEditorActionListener { textView, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val loadingUrl = textView.text.toString()
                    if (URLUtil.isNetworkUrl(loadingUrl)) {
                        webview.loadUrl(loadingUrl)
                    } else {
                        webview.loadUrl("http://$loadingUrl")
                    }
                }
                false
            }
            ibHome.setOnClickListener {
                webview.loadUrl(DEFAULT_URL)
            }
            ibBack.setOnClickListener {
                webview.goBack()
            }
            ibForward.setOnClickListener {
                webview.goForward()
            }
            refresh.setOnRefreshListener {
                webview.reload()
            }
        }
    }

    inner class WebViewClient : android.webkit.WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            binding.progressbar.show()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.refresh.isRefreshing = false
            binding.progressbar.hide()
            binding.ibBack.isEnabled = binding.webview.canGoBack()
            binding.ibForward.isEnabled = binding.webview.canGoForward()
            binding.addressbar.setText(url)
        }
    }

    inner class WebChromeClient : android.webkit.WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            binding.progressbar.progress = newProgress
        }
    }


    companion object {
        private const val DEFAULT_URL = "https://google.com"
    }
}