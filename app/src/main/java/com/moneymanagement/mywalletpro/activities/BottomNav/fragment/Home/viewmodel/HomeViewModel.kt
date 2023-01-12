package com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Home.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moneymanagement.mywalletpro.AppDatabase
import com.moneymanagement.mywalletpro.Model.Transaksi
import com.moneymanagement.mywalletpro.Model.User
import com.moneymanagement.mywalletpro.Model.UserWithTransaction
import com.moneymanagement.mywalletpro.Utils.SharedPreference
import java.util.concurrent.Executors

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val db = AppDatabase.getDatabaseInstance(application.applicationContext)
    private val executors = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())
    var dataSpending: Long = 0
    var hasl: Long = 0

    fun getBalance(): LiveData<Long>{
        var spending: Long = 0
        var income: Long = 0
        var data = MutableLiveData<Long>()
        db.userDao().getSpending(1).observeForever {
            spending = it.sum()
            hasl = income - spending
            data.value = hasl
            Log.e("TAG", "getSpending view model: $spending")
        }

        db.userDao().getSpending(2).observeForever {
            income = it.sum()
            hasl = income - spending
            data.value = hasl
            Log.e("TAG", "getSpending view model: $income $spending $hasl")
        }
        return data
    }

    fun getSpen(spendType: Int): LiveData<List<Long>>{
        return db.userDao().getSpending(spendType)
    }

    fun getIn(incomeType: Int): LiveData<List<Long>>{
        return db.userDao().getSpending(incomeType)
    }

    //Get transaction value from room database
    fun getAllTransaction(): LiveData<List<Transaksi>>{
        return db.userDao().getAllTransaction()
    }

    fun getRelation(): LiveData<List<UserWithTransaction>>{
        return db.userDao().getUsersWithPlaylists()
    }

    //get spending money observeforever untuk observe data terus menerus tanpa lifecyclerowner
    fun getSpendingTotal(type: Int): LiveData<Long>{
        val dataSpending = MutableLiveData<Long>()
        var spending: Long = 0
        db.userDao().getSpending(type).observeForever {
            var index = 0
            while (index < it.size){
                spending += it[index]
                index++
            }
            dataSpending.value = spending
            Log.e("TAG", "getSpending view model: $spending")
        }
        return dataSpending
    }

    //get income money observeforever untuk observe data terus menerus tanpa lifecyclerowner
    fun getIncomeTotal(type: Int): LiveData<Long>{
        val totalIncom = MutableLiveData<Long>()
        var income : Long = 0
        db.userDao().getSpending(type).observeForever {
            var index = 0
            while (index < it.size){
                income += it[index]
                index++
            }
            totalIncom.value = income
            Log.e("TAG", "getIncome view model: $income")
        }
        return totalIncom
    }

    fun deleteTransaction(transaction: Transaksi){
        executors.execute {
            db.userDao().deleteTransaction(transaction)
        }
    }

    //Get username user login from sharedpreference
    fun getLoginUsernameSharedPref(): String? {
        return SharedPreference.getUsernameLogin(getApplication<Application>().applicationContext)
    }

    fun getUserLoginDetail(username : String):LiveData<User>{
        return db.userDao().getUserLogin(username)
    }

    //Set user login id to sharedpreference
    fun setUserLoginId(userId: Int){
        return SharedPreference.setUserIdLogin(getApplication<Application>().applicationContext, userId)
    }
}