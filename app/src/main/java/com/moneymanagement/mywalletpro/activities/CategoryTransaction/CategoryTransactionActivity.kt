package com.moneymanagement.mywalletpro.activities.CategoryTransaction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.moneymanagement.mywalletpro.Model.CategoryTransaction
import com.moneymanagement.mywalletpro.R
import com.moneymanagement.mywalletpro.Utils.OnCategoryClick
import com.moneymanagement.mywalletpro.activities.AddTransaction.Adapter.TransactionCategoryAdapter
import com.moneymanagement.mywalletpro.activities.AddTransaction.AddTransactionActivity
import com.moneymanagement.mywalletpro.databinding.ActivityCategoryTransactionBinding

class CategoryTransactionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCategoryTransactionBinding
    private var categoryList : ArrayList<CategoryTransaction> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCategoryData()

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.rvCategoryTransaction.layoutManager = LinearLayoutManager(applicationContext,
            VERTICAL,false)

        val categoryAdapter = TransactionCategoryAdapter(this,categoryList)
        binding.rvCategoryTransaction.adapter = categoryAdapter

    }

    private fun setCategoryData() {
        val categoryOne = CategoryTransaction(R.drawable.ic_fuel
            ,R.drawable.fuel
            ,1
            ,"Bensin"
            ,"Kendaraan")
        val categoryTwo = CategoryTransaction(R.drawable.ic_eat
            ,R.drawable.fuel
            ,1
            ,"Makan"
            ,"Kebutuhan Pokok")
        val categoryThree = CategoryTransaction(R.drawable.ic_connection
            ,R.drawable.fuel
            ,1
            ,"Internet"
            ,"Pengeluaran")
        val categoryFour = CategoryTransaction(R.drawable.ic_travel
            ,R.drawable.fuel
            ,1
            ,"Traveling"
            ,"Hiburan")
        val categoryFive = CategoryTransaction(R.drawable.ic_shopping
            ,R.drawable.fuel
            ,1
            ,"Belanja"
            ,"Belanja")
        val categorySix = CategoryTransaction(R.drawable.ic_shopping
            ,R.drawable.fuel
            ,2
            ,"Pemasukan"
            ,"Gaji")

        categoryList.add(categoryOne)
        categoryList.add(categoryTwo)
        categoryList.add(categoryThree)
        categoryList.add(categoryFour)
        categoryList.add(categoryFive)
        categoryList.add(categorySix)
    }
}