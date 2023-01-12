package com.moneymanagement.mywalletpro.activities.Main.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moneymanagement.mywalletpro.AppDatabase
import com.moneymanagement.mywalletpro.Model.User
import com.moneymanagement.mywalletpro.Utils.SharedPreference
import java.util.concurrent.Executors

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabaseInstance(application.applicationContext)
    private val executors = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    private var succesCreate = MutableLiveData<Boolean>()

    //Create user and save to room
    fun createNewUser(user : User){
        executors.execute{

            db.userDao().createUsar(user)

            handler.post{
                succesCreate.postValue(true)
            }
        }
    }

    //Returning value true if success create a new user
    fun successCreateUser(): MutableLiveData<Boolean>{
        return succesCreate
    }

    //save data to sharedpreference with username and status as param
    fun setDataToSharedPref(username : String, loginStatus : Boolean){
        SharedPreference.setUsernameLogin(getApplication<Application>().applicationContext, username)
        SharedPreference.setLoginStatus(getApplication<Application>().applicationContext, loginStatus)
    }

    fun getLoginStatusSharedPref(): Boolean{
        return SharedPreference.getLoginStatus(getApplication<Application>().applicationContext)
    }

}