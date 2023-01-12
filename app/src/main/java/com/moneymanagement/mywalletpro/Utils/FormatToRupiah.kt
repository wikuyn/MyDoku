package com.moneymanagement.mywalletpro.Utils

import java.text.NumberFormat
import java.util.*

class FormatToRupiah {

    companion object{
        fun convertRupiahToDecimal(number: Long): String{
            val localeId = Locale("IND","ID")
            val numberFormat = NumberFormat.getNumberInstance(localeId)
            val formatRupiah = numberFormat.format(number)
            return formatRupiah
        }
    }
}