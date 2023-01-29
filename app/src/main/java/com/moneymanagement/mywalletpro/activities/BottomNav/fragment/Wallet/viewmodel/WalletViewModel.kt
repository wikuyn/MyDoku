package com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Wallet.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moneymanagement.mywalletpro.AppDatabase
import com.moneymanagement.mywalletpro.Model.Relation.UserWithTransaksi
import com.moneymanagement.mywalletpro.Model.Transaksi
import java.util.concurrent.Executors

class WalletViewModel(application: Application): AndroidViewModel(application){
    private val db = AppDatabase.getDatabaseInstance(application.applicationContext)
    private val executors = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    fun getSpendingNominalByCategory(category: String): LiveData<Float>{
        var liveDataSpending = MutableLiveData<Float>()
        try {
            db.userDao().getNominalByCategory(category).observeForever {
                liveDataSpending.value = it.sum().toFloat()
            }
        }catch (e: NullPointerException){
            Log.e("TAG", "getSpendingNominalByCategory: ${e.message}")
        }
        Log.e("TAG", "getSpendingNominalByCategory: ${liveDataSpending.value}")
        return liveDataSpending
    }

    fun getList(): LiveData<List<Transaksi>>{
        var data= MutableLiveData<List<Transaksi>>()
        db.userDao().getSpendingList(1).observeForever {
            data.value = it
        }
        return data
    }

    fun getLastTransaction(): LiveData<List<Transaksi>>{
        var transaction = MutableLiveData<List<Transaksi>>()
        db.userDao().getAllTransaction().observeForever {
            transaction.value = it
        }
        return transaction
    }

    fun getUserWithTransaction(): LiveData<UserWithTransaksi>{
        var data = MutableLiveData<UserWithTransaksi>()
        db.userDao().getUserWithTransaction().observeForever {
            data.value = it
        }
        return data
    }
}