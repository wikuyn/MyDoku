package com.moneymanagement.mywalletpro.Utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.util.prefs.Preferences

class SharedPreference {

    companion object{
        private fun getSharedPreference(context: Context): SharedPreferences{
            return context.getSharedPreferences("USER_LOGIN",Context.MODE_PRIVATE)
        }

        fun setUsernameLogin(context: Context, username : String){
            val editor : SharedPreferences.Editor = getSharedPreference(context).edit()
            editor.putString("USERNAME_LOGIN", username)
            editor.apply()
        }

        fun getUsernameLogin(context: Context): String? {
            return getSharedPreference(context).getString("USERNAME_LOGIN",null)
        }

        fun setLoginStatus(context: Context, status: Boolean){
            val editor : SharedPreferences.Editor = getSharedPreference(context).edit()
            editor.putBoolean("IS_LOGIN", status)
            editor.apply()
        }

        fun getLoginStatus(context: Context): Boolean{
            return getSharedPreference(context).getBoolean("IS_LOGIN",false)
        }

        fun setUserIdLogin(context: Context, userId: Int){
            val editor : SharedPreferences.Editor = getSharedPreference(context).edit()
            editor.putInt("USER_ID", userId)
            editor.apply()
        }

        fun getUserIdLogin(context: Context): Int{
            return getSharedPreference(context).getInt("USER_ID",90000)
        }
    }
}