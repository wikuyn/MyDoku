package com.moneymanagement.mywalletpro.activities.NewsActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.moneymanagement.mywalletpro.R
import com.moneymanagement.mywalletpro.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val urlNews = intent.getStringExtra("URL").toString()

        binding.newsView.webViewClient = WebViewClient()

        binding.newsView.loadUrl(urlNews)
        binding.newsView.settings.javaScriptEnabled = true
    }
}