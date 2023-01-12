package com.moneymanagement.mywalletpro.activities.AddTransaction.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.moneymanagement.mywalletpro.Model.CategoryTransaction
import com.moneymanagement.mywalletpro.Utils.OnCategoryClick
import com.moneymanagement.mywalletpro.activities.AddTransaction.AddTransactionActivity
import com.moneymanagement.mywalletpro.activities.CategoryTransaction.CategoryTransactionActivity
import com.moneymanagement.mywalletpro.databinding.LayoutCategoryTransactionBinding

class TransactionCategoryAdapter(val mContext : Context, val categoryTransaction: List<CategoryTransaction>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding : LayoutCategoryTransactionBinding

    class ViewHolderCategory(binding: LayoutCategoryTransactionBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = LayoutCategoryTransactionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolderCategory(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var category = categoryTransaction[position]
        binding.tvNameCategory.text = category.categoryName
        binding.categoryIcon.setImageResource(category.icon)
        binding.root.setOnClickListener {
            val intent = Intent(mContext, AddTransactionActivity::class.java)
            intent.putExtra("CATEGORY_KEY", category)
            (mContext as CategoryTransactionActivity).setResult(Activity.RESULT_OK,intent)
            mContext.finish()
        }
    }

    override fun getItemCount(): Int {
        return categoryTransaction.size
    }

}