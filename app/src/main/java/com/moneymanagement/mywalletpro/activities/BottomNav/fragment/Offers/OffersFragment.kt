package com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Offers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Offers.adapter.NewArticelAdapter
import com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Offers.viewmodel.OffersViewModel
import com.moneymanagement.mywalletpro.databinding.FragmentOffersBinding

class OffersFragment : Fragment() {

    lateinit var mBinding: FragmentOffersBinding
    lateinit var viewmodel: OffersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentOffersBinding.inflate(layoutInflater)
        viewmodel = ViewModelProvider(this)[OffersViewModel::class.java]

        mBinding.loading.startShimmer()
        getArticle()

        mBinding.refresh.setOnRefreshListener {
            mBinding.loading.startShimmer()
            getArticle()
        }

        return mBinding.root
    }

    private fun getArticle() {
        viewmodel.getListArticle().observe(viewLifecycleOwner) {
            var adapter = NewArticelAdapter(requireContext(),it.articles)
            mBinding.rvNewsArticle.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            mBinding.rvNewsArticle.adapter = adapter
            mBinding.loading.stopShimmer()
            mBinding.refresh.isRefreshing = false
            mBinding.loading.visibility = View.GONE
        }
    }
}