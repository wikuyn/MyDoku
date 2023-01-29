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
        if (urlNews != null) {
            loadDetailNews(urlNews)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadDetailNews(url: String) {
        binding.newsView.webViewClient = WebViewClient()

        binding.newsView.loadUrl(url)
        binding.newsView.settings.javaScriptEnabled = true
    }
}