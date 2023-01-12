package com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Wallet.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moneymanagement.mywalletpro.AppDatabase
import com.moneymanagement.mywalletpro.Model.Transaksi
import com.moneymanagement.mywalletpro.Model.UserWithTransaction

class WalletViewModel(application: Application): AndroidViewModel(application){
    private val db = AppDatabase.getDatabaseInstance(application.applicationContext)

    fun getSpendingNominalByCategory(categoryName: String): LiveData<Long>{
        var spendingByCategory: Long
        var liveDataSpending = MutableLiveData<Long>()
        db.userDao().getNominalByCategory(categoryName).observeForever {
            spendingByCategory = it.sum()
            liveDataSpending.value = spendingByCategory
            Log.e("TAG", "getSpendingNominalByCategory: $it")
        }
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

    fun getUserWithTransaction(): LiveData<UserWithTransaction>{
        var data = MutableLiveData<UserWithTransaction>()
        db.userDao().getUserWithTransaction().observeForever {
            data.value = it
        }
        return data
    }
}