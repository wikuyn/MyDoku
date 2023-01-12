package com.moneymanagement.mywalletpro.Utils

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

class DateAxisValueFormatter: ValueFormatter() {
    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return super.getFormattedValue(value, axis)
    }
}