package com.moneymanagement.mywalletpro.Utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val BASE_URL = "https://newsapi.org/v2/"

    fun getClient(): Retrofit {
        val mRetrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        return mRetrofit
    }
}