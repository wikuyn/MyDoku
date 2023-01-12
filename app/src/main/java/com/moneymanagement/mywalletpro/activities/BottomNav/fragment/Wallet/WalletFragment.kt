package com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Wallet

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.moneymanagement.mywalletpro.R
import com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Home.Adapter.LastTransactionAdapter
import com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Wallet.viewmodel.WalletViewModel
import com.moneymanagement.mywalletpro.databinding.FragmentWalletBinding

class WalletFragment : Fragment() {

    private lateinit var listDataLine: ArrayList<Entry>

    private lateinit var mBinding: FragmentWalletBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentWalletBinding.inflate(inflater,container,false)
        val viewModel = ViewModelProvider(this)[WalletViewModel::class.java]


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

        setData(viewModel)

        mBinding.linechart.xAxis.setDrawLabels(false)
        mBinding.linechart.axisRight.setDrawLabels(false)
        mBinding.linechart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        setDataLineChart(viewModel)

        viewModel.getLastTransaction().observe(viewLifecycleOwner){
            mBinding.rvTransaksiTerakhir.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
            val adapter = LastTransactionAdapter(requireContext(),it)
            mBinding.rvTransaksiTerakhir.adapter = adapter
        }
        return mBinding.root
    }

    private fun setDataLineChart(viewModel: WalletViewModel) {
        viewModel.getList().observe(viewLifecycleOwner){
            listDataLine = ArrayList()
            var x = 10f
            for (i in it){
                listDataLine.add(Entry(x,i.nominal.toFloat()))
                x += 10f
                val linedataset = LineDataSet(listDataLine,"Pengeluaran")
                val linedata = LineData(linedataset)
                mBinding.linechart.data = linedata
                mBinding.piechart.invalidate()
            }
        }
    }

    private fun setData(viewModel: WalletViewModel) {
        val entries: ArrayList<PieEntry> = ArrayList()
        val colors: ArrayList<Int> = ArrayList()
        // on below line we are creating array list and
        // adding data to it to display in pie chart
        viewModel.getSpendingNominalByCategory("Bensin").observe(viewLifecycleOwner){
            if (it > 0){
                entries.add(PieEntry(it.toFloat()))//entries.add(PieEntry(transactionInternet))

                // on below line we are setting pie data set
                val dataSet = PieDataSet(entries, "Mobile OS")

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
                val data = PieData(dataSet)
                data.setValueFormatter(PercentFormatter())
                data.setValueTextSize(15f)
                data.setValueTypeface(Typeface.DEFAULT_BOLD)
                data.setValueTextColor(Color.WHITE)
                mBinding.piechart.setData(data)

                // undo all highlights
                mBinding.piechart.highlightValues(null)

                // loading chart
                mBinding.piechart.invalidate()
            }
        }

        viewModel.getSpendingNominalByCategory("Makan").observe(viewLifecycleOwner){
            if (it > 0){
                entries.add(PieEntry(it.toFloat()))//entries.add(PieEntry(transactionInternet))

                // on below line we are setting pie data set
                val dataSet = PieDataSet(entries, "Mobile OS")

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
                val data = PieData(dataSet)
                data.setValueFormatter(PercentFormatter())
                data.setValueTextSize(15f)
                data.setValueTypeface(Typeface.DEFAULT_BOLD)
                data.setValueTextColor(Color.WHITE)
                mBinding.piechart.setData(data)

                // undo all highlights
                mBinding.piechart.highlightValues(null)

                // loading chart
                mBinding.piechart.invalidate()
            }
        }

        viewModel.getSpendingNominalByCategory("Internet").observe(viewLifecycleOwner) {
            if (it > 0){
                //entries.add(PieEntry(transactionInternet))
                entries.add(PieEntry(it.toFloat()))

                // on below line we are setting pie data set
                val dataSet = PieDataSet(entries, "Mobile OS")

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
                val data = PieData(dataSet)
                data.setValueFormatter(PercentFormatter())
                data.setValueTextSize(15f)
                data.setValueTypeface(Typeface.DEFAULT_BOLD)
                data.setValueTextColor(Color.WHITE)
                mBinding.piechart.setData(data)

                // undo all highlights
                mBinding.piechart.highlightValues(null)

                // loading chart
                mBinding.piechart.invalidate()
            }
        }

        viewModel.getSpendingNominalByCategory("Traveling").observe(viewLifecycleOwner){
            if (it > 0){
                entries.add(PieEntry(it.toFloat()))//entries.add(PieEntry(transactionInternet))

                // on below line we are setting pie data set
                val dataSet = PieDataSet(entries, "Mobile OS")

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
                val data = PieData(dataSet)
                data.setValueFormatter(PercentFormatter())
                data.setValueTextSize(15f)
                data.setValueTypeface(Typeface.DEFAULT_BOLD)
                data.setValueTextColor(Color.WHITE)
                mBinding.piechart.setData(data)

                // undo all highlights
                mBinding.piechart.highlightValues(null)

                // loading chart
                mBinding.piechart.invalidate()
            }
        }

        viewModel.getSpendingNominalByCategory("Belanja").observe(viewLifecycleOwner){
            if (it > 0){
                entries.add(PieEntry(it.toFloat()))//entries.add(PieEntry(transactionInternet))

                // on below line we are setting pie data set
                val dataSet = PieDataSet(entries, "Mobile OS")

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
                val data = PieData(dataSet)
                data.setValueFormatter(PercentFormatter())
                data.setValueTextSize(15f)
                data.setValueTypeface(Typeface.DEFAULT_BOLD)
                data.setValueTextColor(Color.WHITE)
                mBinding.piechart.setData(data)

                // undo all highlights
                mBinding.piechart.highlightValues(null)

                // loading chart
                mBinding.piechart.invalidate()

            }
        }
    }

}