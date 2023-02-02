package com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.Transition
import com.moneymanagement.mywalletpro.Model.Transaksi
import com.moneymanagement.mywalletpro.Utils.FormatToRupiah
import com.moneymanagement.mywalletpro.Utils.SharedPreference
import com.moneymanagement.mywalletpro.activities.AddTransaction.AddTransactionActivity
import com.moneymanagement.mywalletpro.activities.AllTransaction.AllTransactionActivity
import com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Home.Adapter.LastTransactionAdapter
import com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Home.viewmodel.HomeViewModel
import com.moneymanagement.mywalletpro.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val TAG = HomeFragment::class.simpleName
    private var transaksi: ArrayList<Transaksi> = ArrayList()
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var adapter: LastTransactionAdapter
    private lateinit var homeViewModel: HomeViewModel
    //var balance: Double = 0.0
    private var income: Long = 0
    private var spending: Long = 0
    private var balance: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentHomeBinding.inflate(inflater,container,false)
        homeViewModel= ViewModelProvider(this)[HomeViewModel::class.java]

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val transactionList: List<Transaksi> = adapter.transaksi
                if (direction == ItemTouchHelper.LEFT){
                    homeViewModel.deleteTransaction(transactionList[position])
                }else{
                    val pindah = Intent(context, AddTransactionActivity::class.java)
                    pindah.putExtra("DETAIL_DATA", transactionList[position])
                    startActivity(pindah)
                }
            }
        }).attachToRecyclerView(mBinding.rvLastTrasaction)

        homeViewModel.getBalance().observe(viewLifecycleOwner){
            mBinding.tvBalance.text = "Rp. "+FormatToRupiah.convertRupiahToDecimal(it)
        }

        homeViewModel.getSpen(1).observe(viewLifecycleOwner){
            spending = it.sum()
            mBinding.tvSpending.text = "Rp. -"+FormatToRupiah.convertRupiahToDecimal(spending)
        }

        homeViewModel.getIn(2).observe(viewLifecycleOwner){
            income = it.sum()
            mBinding.tvIncome.text = "Rp. "+FormatToRupiah.convertRupiahToDecimal(income)
        }

        mBinding.btnShow.setOnClickListener {
            if (mBinding.hiddenView.isVisible){
                TransitionManager.beginDelayedTransition(mBinding.cardView3)
                mBinding.hiddenView.visibility = View.GONE
            }else{
                TransitionManager.beginDelayedTransition(mBinding.cardView3)
                mBinding.hiddenView.visibility = View.VISIBLE
            }
        }

        mBinding.btnShowAllTransaksi.setOnClickListener {
            startActivity(Intent(context, AllTransactionActivity::class.java))
        }
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getTransaksiOfUser().observe(viewLifecycleOwner){
            if (it.equals(null) || it.isEmpty()){
                mBinding.tvDataNull.isVisible = true
            }else{
                mBinding.tvDataNull.isVisible = false
                adapter = LastTransactionAdapter(requireContext(),it)
                mBinding.rvLastTrasaction.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
                mBinding.rvLastTrasaction.adapter = adapter
            }
        }
    }

}