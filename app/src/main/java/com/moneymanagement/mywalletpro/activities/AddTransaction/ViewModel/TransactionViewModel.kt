package com.moneymanagement.mywalletpro.activities.AddTransaction.ViewModel

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import com.moneymanagement.mywalletpro.AppDatabase
import com.moneymanagement.mywalletpro.Model.Transaksi
import java.util.concurrent.Executors

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabaseInstance(application.applicationContext)
    private val executors = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    fun insertNewTransaction(transaksi: Transaksi){
        executors.execute{
            db.userDao().insertNewTransaction(transaksi)
        }
    }

    fun updateTransaction(transaksi: Transaksi){
         executors.execute {
             db.userDao().updateTransaction(transaksi)
         }
    }
}