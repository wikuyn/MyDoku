package com.moneymanagement.mywalletpro.Utils

import com.moneymanagement.mywalletpro.Model.Online.ResponseNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    fun getArticelNews(@Query("country") country: String,
                        @Query("category") category: String,
                        @Query("apiKey") apikey: String):Call<ResponseNews>

}