package com.moneymanagement.mywalletpro.activities.AllTransaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moneymanagement.mywalletpro.R
import com.moneymanagement.mywalletpro.Utils.SharedPreference
import com.moneymanagement.mywalletpro.activities.AllTransaction.Adapter.AllTransactionAdapter
import com.moneymanagement.mywalletpro.activities.AllTransaction.ViewModel.AllTransactionViewModel
import com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Home.Adapter.LastTransactionAdapter
import com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Home.viewmodel.HomeViewModel
import com.moneymanagement.mywalletpro.databinding.ActivityAllTransactionBinding

class AllTransactionActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityAllTransactionBinding
    private lateinit var adapter: AllTransactionAdapter
    private lateinit var viewmodel: AllTransactionViewModel
    private var tipeKategori = arrayOf("Semua","Pengeluaran","Pemasukan")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllTransactionBinding.inflate(layoutInflater)

        viewmodel = ViewModelProvider(this)[AllTransactionViewModel::class.java]
        setContentView(binding.root)

        binding.spinner.onItemSelectedListener = this
        setSpinnerData()

        getAllTransaction()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setSpinnerData() {
        ArrayAdapter.createFromResource(
            this,
            R.array.tipe_transaksi,
            android.R.layout.simple_spinner_item
        ).also {
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            binding.spinner.adapter = adapter
        }

    }

    private fun getAllTransaction() {
        viewmodel.getAllTransaction(SharedPreference.getUserIdLogin(this)).observe(this){
            if (it.equals(null) || it.isEmpty()){
                Toast.makeText(this,"kosong", Toast.LENGTH_SHORT)
            }else{
                adapter = AllTransactionAdapter(this,it)
                binding.rvAllTransaction.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,true)
                binding.rvAllTransaction.adapter = adapter
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Toast.makeText(this, tipeKategori[p2], Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(this, "kcao", Toast.LENGTH_SHORT).show()
    }
}