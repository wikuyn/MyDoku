package com.moneymanagement.mywalletpro.activities.AllTransaction.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moneymanagement.mywalletpro.Model.Transaksi
import com.moneymanagement.mywalletpro.Utils.FormatToRupiah
import com.moneymanagement.mywalletpro.activities.AddTransaction.AddTransactionActivity
import com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Home.Adapter.LastTransactionAdapter
import com.moneymanagement.mywalletpro.databinding.LayoutItemIncomeBinding
import com.moneymanagement.mywalletpro.databinding.LayoutItemSpendingBinding
import java.text.SimpleDateFormat

class AllTransactionAdapter(val context: Context, val allTrasaction: List<Transaksi>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var bindingIncome : LayoutItemIncomeBinding
    private lateinit var bindingSpending : LayoutItemSpendingBinding
    private val VIEW_INCOME = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_INCOME){
            bindingIncome = LayoutItemIncomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LastTransactionAdapter.ViewHolderIncome(bindingIncome)
        }else{
            bindingSpending = LayoutItemSpendingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return LastTransactionAdapter.ViewHolderSpending(bindingSpending)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (allTrasaction[position].type == VIEW_INCOME){
            bindingIncome.tvIncomeName.text = allTrasaction[position].transactionName
            bindingIncome.ivIcon.setImageResource(allTrasaction[position].icon)
            bindingIncome.tvMoney.text = "Rp. -"+ FormatToRupiah.convertRupiahToDecimal(allTrasaction[position].nominal)
            val dateFormat = SimpleDateFormat(
                "dd/MM/yyyy"
                        + " "
                        + " HH:mm")
            val date = dateFormat.format(allTrasaction[position].date)
            bindingIncome.tvDate.text = date.toString()
            bindingIncome.root.setOnClickListener {
                val intent = Intent(context, AddTransactionActivity::class.java)
                intent.putExtra("DETAIL_DATA",allTrasaction[position])
                context.startActivity(intent)
            }

        }else{
            bindingSpending.tvSpendingName.text = allTrasaction[position].transactionName
            bindingSpending.ivIcon.setImageResource(allTrasaction[position].icon)
            bindingSpending.tvMoney.text = "Rp. "+ FormatToRupiah.convertRupiahToDecimal(allTrasaction[position].nominal)
            val dateFormat = SimpleDateFormat(
                "dd/MM/yyyy"
                        + " "
                        + " HH:mm")
            val date = dateFormat.format(allTrasaction[position].date)
            bindingSpending.tvDate.text = date.toString()
            bindingSpending.root.setOnClickListener {
                val intent = Intent(context, AddTransactionActivity::class.java)
                intent.putExtra("DETAIL_DATA",allTrasaction[position])
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return allTrasaction.size
    }

    override fun getItemViewType(position: Int): Int {
        return allTrasaction.get(position).type
    }
}