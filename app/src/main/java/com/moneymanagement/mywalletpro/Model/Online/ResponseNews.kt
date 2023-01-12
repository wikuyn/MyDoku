package com.moneymanagement.mywalletpro.Model.Online

data class ResponseNews(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)