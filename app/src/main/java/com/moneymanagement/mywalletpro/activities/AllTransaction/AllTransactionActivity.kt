package com.moneymanagement.mywalletpro.activities.AllTransaction

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import okhttp3.internal.format
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*

class AllTransactionActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    private lateinit var binding: ActivityAllTransactionBinding
    private lateinit var adapter: AllTransactionAdapter
    private lateinit var viewmodel: AllTransactionViewModel
    private val simpleDateFormater = SimpleDateFormat("dd-MM-yyyy")
    private var tipeKategori = arrayOf("Semua","Pengeluaran","Pemasukan")
    private val date: Date = Calendar.getInstance().time
    private lateinit var def: ColorStateList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllTransactionBinding.inflate(layoutInflater)

        viewmodel = ViewModelProvider(this)[AllTransactionViewModel::class.java]
        setContentView(binding.root)

        binding.all.setOnClickListener(this)
        binding.today.setOnClickListener(this)
        binding.week.setOnClickListener(this)
        binding.month.setOnClickListener(this)
        def = binding.today.textColors

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
            adapter -> adapter
            .setDropDownViewResource(android.R.layout.simple_spinner_item)
            binding.spinner.adapter = adapter
        }

    }

    private fun getAllTransaction() {
        viewmodel.getAllTransaction().observe(this){
            if (it.equals(null) || it.isEmpty()){

            }else{
                adapter = AllTransactionAdapter(this,it)
                binding.rvAllTransaction.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
                binding.rvAllTransaction.adapter = adapter
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(p0: View?) {
        if (p0?.id == R.id.all){
            binding.select.animate().x(0F).duration = 100
            binding.all.setTextColor(Color.WHITE)
            binding.today.setTextColor(def)
            binding.week.setTextColor(def)
            binding.month.setTextColor(def)
            getAllTransaction()
        }else if (p0?.id == R.id.today){
            var size = binding.today.width * 1
            binding.select.animate().x(size.toFloat()).duration = 100;
            binding.all.setTextColor(def)
            binding.today.setTextColor(Color.WHITE)
            binding.week.setTextColor(def)
            binding.month.setTextColor(def)
            getTransactionToday()
        }else if (p0?.id == R.id.week){
            var size = binding.today.width * 2
            binding.select.animate().x(size.toFloat()).duration = 100;
            binding.all.setTextColor(def)
            binding.today.setTextColor(def)
            binding.week.setTextColor(Color.WHITE)
            binding.month.setTextColor(def)
            getTransactionAweek()
        }else if (p0?.id == R.id.month){
            var size = binding.today.width * 3
            binding.select.animate().x(size.toFloat()).duration = 100;
            binding.all.setTextColor(def)
            binding.today.setTextColor(def)
            binding.week.setTextColor(def)
            binding.month.setTextColor(Color.WHITE)
            getTransactionMonth()
        }
    }

    private fun getTransactionToday() {
        val todayDate = simpleDateFormater.format(date)
        viewmodel.getAllTransactionToday(todayDate).observe(this){
            if (it.size > 0){
                binding.rvAllTransaction.visibility = View.VISIBLE
                adapter = AllTransactionAdapter(this,it)
                binding.rvAllTransaction.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
                binding.rvAllTransaction.adapter = adapter
            }else{
                binding.rvAllTransaction.visibility = View.GONE
            }
        }
    }

    private fun getTransactionAweek() {
        val calendar = Calendar.getInstance()
        val currentWeek = calendar.get(Calendar.WEEK_OF_YEAR)
        viewmodel.getAllTransactionAweek(currentWeek).observe(this){
            if (it.size > 0 || it.isNotEmpty()){
                binding.rvAllTransaction.visibility = View.VISIBLE
                adapter = AllTransactionAdapter(this,it)
                binding.rvAllTransaction.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
                binding.rvAllTransaction.adapter = adapter
            }else{
                binding.rvAllTransaction.visibility = View.GONE
            }
        }
    }


    private fun getTransactionMonth() {
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH)+1
        viewmodel.getAlTransactionMonth(month).observe(this){
            if (it.isNotEmpty()){
                binding.rvAllTransaction.visibility = View.VISIBLE
                adapter = AllTransactionAdapter(this,it)
                binding.rvAllTransaction.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
                binding.rvAllTransaction.adapter = adapter
            }else{
                binding.rvAllTransaction.visibility = View.GONE
            }
        }
    }
}