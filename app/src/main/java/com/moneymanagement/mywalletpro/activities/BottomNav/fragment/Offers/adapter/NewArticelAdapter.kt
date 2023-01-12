package com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Offers.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moneymanagement.mywalletpro.Model.Online.Article
import com.moneymanagement.mywalletpro.Model.Online.ResponseNews
import com.moneymanagement.mywalletpro.activities.NewsActivity.NewsActivity
import com.moneymanagement.mywalletpro.databinding.FragmentOffersBinding
import com.moneymanagement.mywalletpro.databinding.LayoutNewsArticelItemBinding
import com.squareup.picasso.Picasso

class NewArticelAdapter(val context: Context, val data: List<Article>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var mBinding: LayoutNewsArticelItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mBinding = LayoutNewsArticelItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val article = data[position]
        mBinding.tvNewsTittle.text = article.title
        mBinding.tvPublishAt.text = article.publishedAt
        Picasso.get()
            .load(article.urlToImage).
                fit().
                into(mBinding.thumbnail)

        holder.itemView.setOnClickListener {
            val pindah = Intent(context, NewsActivity::class.java)
            pindah.putExtra("URL",article.url)
            context.startActivity(pindah)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(binding: LayoutNewsArticelItemBinding): RecyclerView.ViewHolder(binding.root){}

}