package com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Home.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.moneymanagement.mywalletpro.Model.Transaksi
import com.moneymanagement.mywalletpro.Utils.FormatToRupiah
import com.moneymanagement.mywalletpro.activities.AddTransaction.AddTransactionActivity
import com.moneymanagement.mywalletpro.databinding.LayoutItemIncomeBinding
import com.moneymanagement.mywalletpro.databinding.LayoutItemSpendingBinding
import java.text.SimpleDateFormat

class LastTransactionAdapter(val context : Context,val transaksi : List<Transaksi>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TAG = LastTransactionAdapter::class.toString()
    private lateinit var bindingIncome : LayoutItemIncomeBinding
    private lateinit var bindingSpending : LayoutItemSpendingBinding
    val VIEW_INCOME = 1

    class ViewHolderIncome(binding : LayoutItemIncomeBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    class ViewHolderSpending(binding : LayoutItemSpendingBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == VIEW_INCOME){
            bindingIncome = LayoutItemIncomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolderIncome(bindingIncome)
        }else{
            bindingSpending = LayoutItemSpendingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ViewHolderSpending(bindingSpending)
        }
    }

    override fun getItemCount(): Int {
        return transaksi.size
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (transaksi[position].type == VIEW_INCOME){
            bindingIncome.tvIncomeName.text = transaksi[position].transactionName
            bindingIncome.ivIcon.setImageResource(transaksi[position].icon)
            bindingIncome.tvMoney.text = "Rp. -"+FormatToRupiah.convertRupiahToDecimal(transaksi[position].nominal)
            val dateFormat = SimpleDateFormat(
                "dd/MM/yyyy"
                        + " "
                        + " HH:mm")
            val date = dateFormat.format(transaksi[position].date)
            bindingIncome.tvDate.text = date.toString()
            bindingIncome.root.setOnClickListener {
                val intent = Intent(context, AddTransactionActivity::class.java)
                intent.putExtra("DETAIL_DATA",transaksi[position])
                context.startActivity(intent)
            }

        }else{
            bindingSpending.tvSpendingName.text = transaksi[position].transactionName
            bindingSpending.ivIcon.setImageResource(transaksi[position].icon)
            bindingSpending.tvMoney.text = "Rp. "+FormatToRupiah.convertRupiahToDecimal(transaksi[position].nominal)
            val dateFormat = SimpleDateFormat(
                "dd/MM/yyyy"
                        + " "
                        + " HH:mm")
            val date = dateFormat.format(transaksi[position].date)
            bindingSpending.tvDate.text = date.toString()
            bindingSpending.root.setOnClickListener {
                val intent = Intent(context, AddTransactionActivity::class.java)
                intent.putExtra("DETAIL_DATA",transaksi[position])
                context.startActivity(intent)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return transaksi[position].type
    }

}