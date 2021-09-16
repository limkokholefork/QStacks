package com.example.android.qstack

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.android.qstack.databinding.ActivityUserDetailBinding
import com.example.android.qstack.ui.users.USER_DETAIL_KEY

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent.extras
        val link : String? = intent?.getString(USER_DETAIL_KEY)

        binding.linearIndicator.max = 100
        binding.linearIndicator.progress = 1

        link?.let {
            binding.webView.loadUrl(it)
        }

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