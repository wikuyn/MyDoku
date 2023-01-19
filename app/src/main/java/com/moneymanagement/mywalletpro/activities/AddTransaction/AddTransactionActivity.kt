package com.moneymanagement.mywalletpro.activities.AddTransaction

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.moneymanagement.mywalletpro.Model.CategoryTransaction
import com.moneymanagement.mywalletpro.Model.Transaksi
import com.moneymanagement.mywalletpro.R
import com.moneymanagement.mywalletpro.Utils.DateConverter
import com.moneymanagement.mywalletpro.Utils.FormatToRupiah
import com.moneymanagement.mywalletpro.Utils.SharedPreference
import com.moneymanagement.mywalletpro.activities.AddTransaction.ViewModel.TransactionViewModel
import com.moneymanagement.mywalletpro.activities.CategoryTransaction.CategoryTransactionActivity
import com.moneymanagement.mywalletpro.databinding.ActivityAddTransactionBinding
import java.text.SimpleDateFormat
import java.util.*

class AddTransactionActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityAddTransactionBinding
    private lateinit var viewmodel: TransactionViewModel
    private lateinit var newTransaksi: Transaksi
    private var textMoney : Long = 0
    private var categoryIcon: Int = 0
    private var categoryType: Int = 0
    private lateinit var categoryName: String
    private lateinit var transactionName: String

    private val calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("EEE, d MMM yyyy", Locale.US)
    private lateinit var date : Date
    private var data: Transaksi? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        viewmodel = ViewModelProvider(this)[TransactionViewModel::class.java]
        setContentView(binding.root)

        data = intent.getParcelableExtra<Transaksi>("DETAIL_DATA")
        if (data != null){
            categoryName = data!!.category.toString()
            categoryIcon = data!!.icon
            categoryType = data!!.type
            textMoney = data!!.nominal
            date = data!!.date

            binding.edtTransactionName.setText(data!!.transactionName)
            val formatRupiah = FormatToRupiah.convertRupiahToDecimal(textMoney)

            binding.edtMoney.setText(formatRupiah)
            binding.tvCategory.text = categoryName
            binding.iconCategory.setImageResource(categoryIcon)
            binding.tvDate.text = formatter.format(date)
        }else{
            setDefaultCategory()
        }

        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.tvDate.setOnClickListener {
            DatePickerDialog(
                this,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.tvCategory.setOnClickListener {
            intent = Intent(applicationContext, CategoryTransactionActivity::class.java)
            getData.launch(intent)
        }

        binding.edtMoney.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.edtMoney.removeTextChangedListener(this)
                try {
                    var originalString = p0.toString()
                    var longVal : Long
                    val replace = originalString.replace(Regex("[Rp. ]"), "")
                    if (p0.toString().isNotEmpty()){
                        originalString = replace
                        longVal = originalString.toLong()
                        val formatRupiah = FormatToRupiah.convertRupiahToDecimal(longVal)
                        textMoney = longVal
                        binding.edtMoney.setText(formatRupiah)
                        binding.edtMoney.setSelection(binding.edtMoney.text.length)
                    }

                }catch (e : NumberFormatException){
                    Log.e("TAG", "afterTextChanged: ${e.message}")
                }
                binding.edtMoney.addTextChangedListener(this)
            }

        })

        binding.btnSave.setOnClickListener {
            if (binding.edtMoney.text.trim().isEmpty()){
                binding.edtMoney.requestFocus()
                Toast.makeText(applicationContext, "Please insert money first", Toast.LENGTH_SHORT).show()
            }else if (binding.edtTransactionName.text.trim().isEmpty()){
                binding.edtTransactionName.requestFocus()
                Toast.makeText(applicationContext, "Please transaction name first", Toast.LENGTH_SHORT).show()
            }else{
                if (data == null){
                    saveData()
                }else{
                    updateData()
                }
            }
        }
    }

    private fun updateData() {
        transactionName = binding.edtTransactionName.text.toString().trim()
        val updateData = Transaksi(categoryIcon,transactionName,SharedPreference.getUserIdLogin(applicationContext)
            , textMoney, date, categoryType,categoryName)
        updateData.transactionId = data?.transactionId!!
        viewmodel.updateTransaction(updateData)
        Toast.makeText(this, "${updateData.transactionId}", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun saveData() {
        transactionName = binding.edtTransactionName.text.toString().trim()
        newTransaksi = Transaksi(categoryIcon,transactionName,SharedPreference.getUserIdLogin(applicationContext)
            , textMoney, date, categoryType, categoryName)
        viewmodel.insertNewTransaction(newTransaksi)
        finish()
    }

    private fun setDefaultCategory() {
        categoryName = "Belanja"
        categoryIcon = R.drawable.ic_shopping
        categoryType = 1

        binding.tvCategory.text = categoryName
        binding.iconCategory.setImageResource(categoryIcon)
    }

    val getData = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            val dada = it.data
            val data = dada?.getParcelableExtra<CategoryTransaction>("CATEGORY_KEY")

            categoryIcon = data!!.icon
            categoryType = data.categoryType
            categoryName = data.categoryName
            binding.tvCategory.text = categoryName
            binding.iconCategory.setImageResource(categoryIcon)
        }else{
            Log.e("TAG", "on activity result error")
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayMonth: Int) {
        calendar.set(year, month, dayMonth)
        date = calendar.time
        setFormatDate(date)
    }

    private fun setFormatDate(s: Date) {
        binding.tvDate.text = formatter.format(s)
    }
}