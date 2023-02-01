package com.moneymanagement.mywalletpro.activities.AllTransaction.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moneymanagement.mywalletpro.AppDatabase
import com.moneymanagement.mywalletpro.Model.Relation.UserWithTransaksi
import com.moneymanagement.mywalletpro.Model.Transaksi
import java.util.concurrent.Executors

class AllTransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabaseInstance(application.applicationContext)
    private val executors = Executors.newSingleThreadExecutor()
    private var listTransaction: MutableLiveData<List<Transaksi>> = MutableLiveData()

    fun getAllTransaction(userId: Int): LiveData<List<Transaksi>>{
        db.userDao().getTransaksiOfUser(userId).observeForever {
            listTransaction.value = it.transaksi
        }
        return listTransaction
    }
}