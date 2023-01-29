package com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Wallet

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.moneymanagement.mywalletpro.R
import com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Home.Adapter.LastTransactionAdapter
import com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Wallet.viewmodel.WalletViewModel
import com.moneymanagement.mywalletpro.databinding.FragmentWalletBinding

class WalletFragment : Fragment() {
    private lateinit var dataSet: PieDataSet
    private lateinit var data: PieData
    private lateinit var mBinding: FragmentWalletBinding
    private var entries: ArrayList<PieEntry> = ArrayList()
    private var makan: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel = ViewModelProvider(this)[WalletViewModel::class.java]

        // Inflate the layout for this fragment
        mBinding = FragmentWalletBinding.inflate(inflater, container, false)

        initPieChart(viewModel)
        //setDataPieChart(viewModel)
        initLineChart()
        setDataLineChart(viewModel)

        viewModel.getLastTransaction().observe(viewLifecycleOwner) {
            mBinding.rvTransaksiTerakhir.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
            val adapter = LastTransactionAdapter(requireContext(), it)
            mBinding.rvTransaksiTerakhir.adapter = adapter
        }
        return mBinding.root
    }

    private fun initLineChart() {
        mBinding.lineChart.xAxis.setDrawLabels(false)
        mBinding.lineChart.axisRight.setDrawLabels(false)
        mBinding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
    }


    private fun setDataLineChart(viewModel: WalletViewModel) {
        var listDataLine: ArrayList<Entry> = ArrayList()

        listDataLine.add(Entry(10f,25000.toFloat()))
        listDataLine.add(Entry(20f,4000.toFloat()))
        listDataLine.add(Entry(30f,50000.toFloat()))
        val linedataset = LineDataSet(listDataLine,"Pengeluaran")
        val linedata = LineData(linedataset)
        mBinding.lineChart.data = linedata
        viewModel.getList().observe(viewLifecycleOwner){
            /*
            var x = 10f
            for (i in it){
                x += 10f
                val linedataset = LineDataSet(listDataLine,"Pengeluaran")
                val linedata = LineData(linedataset)
                mBinding.lineChart.data = linedata
                mBinding.piechart.invalidate()
            }*

             */
        }

    }


    private fun initPieChart(viewmodel: WalletViewModel) {
        mBinding.piechart.setUsePercentValues(true)
        mBinding.piechart.description.isEnabled = false
        mBinding.piechart.setDragDecelerationFrictionCoef(0.95f)

        // on below line we are setting hole
        // and hole color for pie chart
        mBinding.piechart.setDrawHoleEnabled(true)
        mBinding.piechart.setHoleColor(Color.WHITE)

        // on below line we are setting circle color and alpha
        mBinding.piechart.setTransparentCircleColor(Color.WHITE)
        mBinding.piechart.setTransparentCircleAlpha(110)

        // on  below line we are setting hole radius
        mBinding.piechart.setHoleRadius(58f)
        mBinding.piechart.setTransparentCircleRadius(61f)

        // on below line we are setting center text
        mBinding.piechart.setDrawCenterText(true)

        // on below line we are setting
        // rotation for our pie chart
        mBinding.piechart.setRotationAngle(0f)

        // enable rotation of the pieChart by touch
        mBinding.piechart.setRotationEnabled(true)
        mBinding.piechart.setHighlightPerTapEnabled(true)

        // on below line we are setting animation for our pie chart
        mBinding.piechart.animateY(1400, Easing.EaseInOutQuad)

        // on below line we are disabling our legend for pie chart
        mBinding.piechart.legend.isEnabled = false
        mBinding.piechart.setEntryLabelColor(Color.WHITE)
        mBinding.piechart.setEntryLabelTextSize(12f)

        setDataPieChart(viewmodel)
    }

    fun setDataPieChart(viewModel: WalletViewModel){
        val colors: ArrayList<Int> = ArrayList()
        viewModel.getSpendingNominalByCategory("Bensin").observe(viewLifecycleOwner){
            if (it != null && it > 0){
                entries.add(PieEntry(it.toFloat()))
                dataSet = PieDataSet(entries, "Mobile OS")
                // on below line we are setting icons.
                dataSet.setDrawIcons(false)

                // on below line we are setting slice for pie
                dataSet.sliceSpace = 3f
                dataSet.iconsOffset = MPPointF(0f, 40f)
                dataSet.selectionShift = 5f

                // add a lot of colors to list
                colors.add(resources.getColor(R.color.bensin_category))

                // on below line we are setting colors.
                dataSet.colors = colors

                // on below line we are setting pie data set
                data = PieData(dataSet)
                data.setValueFormatter(PercentFormatter())
                data.setValueTextSize(15f)
                data.setValueTypeface(Typeface.DEFAULT_BOLD)
                data.setValueTextColor(Color.WHITE)
                mBinding.piechart.data = data
                mBinding.piechart.refreshDrawableState()
                mBinding.piechart.invalidate()
            }
        }
        viewModel.getSpendingNominalByCategory("Makan").observe(viewLifecycleOwner){
            if (it != null && it > 0){
                entries.add(PieEntry(it.toFloat()))
                dataSet = PieDataSet(entries, "Mobile OS")
                // on below line we are setting icons.
                dataSet.setDrawIcons(false)

                // on below line we are setting slice for pie
                dataSet.sliceSpace = 3f
                dataSet.iconsOffset = MPPointF(0f, 40f)
                dataSet.selectionShift = 5f

                // add a lot of colors to list
                colors.add(resources.getColor(R.color.makanan_category))

                // on below line we are setting colors.
                dataSet.colors = colors

                // on below line we are setting pie data set
                data = PieData(dataSet)
                data.setValueFormatter(PercentFormatter())
                data.setValueTextSize(15f)
                data.setValueTypeface(Typeface.DEFAULT_BOLD)
                data.setValueTextColor(Color.WHITE)
                mBinding.piechart.data = data
                mBinding.piechart.refreshDrawableState()
                mBinding.piechart.invalidate()
            }
        }
        viewModel.getSpendingNominalByCategory("Internet").observe(viewLifecycleOwner){
            if (it != null && it > 0){
                entries.add(PieEntry(it.toFloat()))
                dataSet = PieDataSet(entries, "Mobile OS")
                // on below line we are setting icons.
                dataSet.setDrawIcons(false)

                // on below line we are setting slice for pie
                dataSet.sliceSpace = 3f
                dataSet.iconsOffset = MPPointF(0f, 40f)
                dataSet.selectionShift = 5f

                // add a lot of colors to list
                colors.add(resources.getColor(R.color.internet_category))

                // on below line we are setting colors.
                dataSet.colors = colors

                // on below line we are setting pie data set
                data = PieData(dataSet)
                data.setValueFormatter(PercentFormatter())
                data.setValueTextSize(15f)
                data.setValueTypeface(Typeface.DEFAULT_BOLD)
                data.setValueTextColor(Color.WHITE)
                mBinding.piechart.data = data
                mBinding.piechart.refreshDrawableState()
                mBinding.piechart.invalidate()

            }
        }
        viewModel.getSpendingNominalByCategory("Belanja").observe(viewLifecycleOwner){
            if (it != null && it > 0){
                entries.add(PieEntry(it.toFloat()))
                dataSet = PieDataSet(entries, "Mobile OS")
                // on below line we are setting icons.
                dataSet.setDrawIcons(false)

                // on below line we are setting slice for pie
                dataSet.sliceSpace = 3f
                dataSet.iconsOffset = MPPointF(0f, 40f)
                dataSet.selectionShift = 5f

                // add a lot of colors to list
                colors.add(resources.getColor(R.color.belanja_category))

                // on below line we are setting colors.
                dataSet.colors = colors

                // on below line we are setting pie data set
                data = PieData(dataSet)
                data.setValueFormatter(PercentFormatter())
                data.setValueTextSize(15f)
                data.setValueTypeface(Typeface.DEFAULT_BOLD)
                data.setValueTextColor(Color.WHITE)
                mBinding.piechart.data = data
                mBinding.piechart.refreshDrawableState()
                mBinding.piechart.invalidate()
            }
        }
        viewModel.getSpendingNominalByCategory("Traveling").observe(viewLifecycleOwner){
            if (it != null && it > 0){
                entries.add(PieEntry(it.toFloat()))
                dataSet = PieDataSet(entries, "Mobile OS")
                // on below line we are setting icons.
                dataSet.setDrawIcons(false)

                // on below line we are setting slice for pie
                dataSet.sliceSpace = 3f
                dataSet.iconsOffset = MPPointF(0f, 40f)
                dataSet.selectionShift = 5f

                // add a lot of colors to list
                colors.add(resources.getColor(R.color.traveling_category))

                // on below line we are setting colors.
                dataSet.colors = colors

                // on below line we are setting pie data set
                data = PieData(dataSet)
                data.setValueFormatter(PercentFormatter())
                data.setValueTextSize(15f)
                data.setValueTypeface(Typeface.DEFAULT_BOLD)
                data.setValueTextColor(Color.WHITE)
                mBinding.piechart.data = data
                mBinding.piechart.refreshDrawableState()
                mBinding.piechart.invalidate()
            }
        }

    }
}