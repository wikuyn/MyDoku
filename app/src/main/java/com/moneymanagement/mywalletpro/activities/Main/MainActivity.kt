package com.moneymanagement.mywalletpro.activities.Main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.moneymanagement.mywalletpro.Model.User
import com.moneymanagement.mywalletpro.Utils.SharedPreference
import com.moneymanagement.mywalletpro.activities.BottomNav.BottomNavigationActivity
import com.moneymanagement.mywalletpro.activities.Main.viewmodel.MainViewModel
import com.moneymanagement.mywalletpro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel : MainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val isLogin = mainViewModel.getLoginStatusSharedPref()
        if (isLogin){
            intent = Intent(this, BottomNavigationActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnContinue.setOnClickListener {
            val username = binding.edtUsername.text.trim().toString()
            if (username == "" || username.isEmpty()){
                Toast.makeText(applicationContext, "Please input username first", Toast.LENGTH_SHORT).show()
            }else{
                val newUser = User(username,"https://upload.wikimedia.org/wikipedia/commons/2/2a/Vinfast-D.Beckham-cropped.jpg","0","0","0")

                mainViewModel.createNewUser(newUser)
                mainViewModel.successCreateUser().observe(this){

                    mainViewModel.setDataToSharedPref(username,true)
                    intent = Intent(this, BottomNavigationActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}