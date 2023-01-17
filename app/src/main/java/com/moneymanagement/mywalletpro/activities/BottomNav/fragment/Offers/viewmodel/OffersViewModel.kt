package com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Offers.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moneymanagement.mywalletpro.BuildConfig
import com.moneymanagement.mywalletpro.Model.Online.ResponseNews
import com.moneymanagement.mywalletpro.Utils.ApiClient
import com.moneymanagement.mywalletpro.Utils.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OffersViewModel: ViewModel(){
    val data: MutableLiveData<ResponseNews> = MutableLiveData()
    var retrofit = ApiClient.getClient()
    var apiService = retrofit.create(ApiService::class.java)
    val API_KEY = BuildConfig.API_KEY

    fun getListArticle(): LiveData<ResponseNews>{
        apiService.getArticelNews("id","business",API_KEY).enqueue(object : Callback<ResponseNews?> {
            override fun onResponse(call: Call<ResponseNews?>, response: Response<ResponseNews?>) {
                if (response != null){
                    data.value = response.body()
                }else{
                    data.value = null
                }
            }

            override fun onFailure(call: Call<ResponseNews?>, t: Throwable) {
                Log.e("TAG", "onFailure: get article data "+t.message )
            }
        })
        return data
    }
}