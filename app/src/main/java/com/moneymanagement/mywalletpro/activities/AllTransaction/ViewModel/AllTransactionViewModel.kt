package com.moneymanagement.mywalletpro.activities.AllTransaction.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moneymanagement.mywalletpro.AppDatabase
import com.moneymanagement.mywalletpro.Model.Relation.UserWithTransaksi
import com.moneymanagement.mywalletpro.Model.Transaksi
import java.util.*
import java.util.concurrent.Executors

class AllTransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabaseInstance(application.applicationContext)
    private val executors = Executors.newSingleThreadExecutor()
    private var listTransaction: MutableLiveData<List<Transaksi>> = MutableLiveData()

    fun getAllTransaction(): LiveData<List<Transaksi>>{
        db.userDao().getAllTransaction().observeForever {
            listTransaction.value = it
        }
        return listTransaction
    }

    fun getAllTransactionToday(dateToday: Long):LiveData<List<Transaksi>>{
        db.userDao().getTransaksiOfUserToday(dateToday).observeForever {
            listTransaction.value = it
            for (i in it){
                Log.e("TAG", "getAllTransactionToday: "+i.transactionName)
            }
        }
        return listTransaction
    }

    fun getAllTransactionAweek():LiveData<List<Transaksi>>{
        db.userDao().getTransaksiOfUserWeek().observeForever {
            listTransaction.value = it
        }
        return listTransaction
    }

    fun getAlTransactionMonth():LiveData<List<Transaksi>>{
        db.userDao().getTransaksiOfUserMonth().observeForever {
            listTransaction.value = it
        }
        return listTransaction
    }
}