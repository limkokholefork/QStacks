package com.example.android.qstack

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.android.qstack.databinding.ActivityWebViewBinding
import com.example.android.qstack.utils.LINK_KEY

class WebViewActivity : AppCompatActivity() {
    
    private lateinit var binding : ActivityWebViewBinding
    
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_web_view)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        val intent = intent.extras
        val link : String? = intent?.getString(LINK_KEY)

        binding.linearIndicator.max = 100
        binding.linearIndicator.progress = 1

        link?.let { 
            binding.webView.loadUrl(it)
        }

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.builtInZoomControls = true
        
        val webViewChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                binding.linearIndicator.progress = newProgress
            }
        }

        binding.webView.webChromeClient = webViewChromeClient

        val client = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                binding.linearIndicator.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                binding.linearIndicator.visibility = View.GONE
            }
        }

        binding.webView.webViewClient = client

    }


}