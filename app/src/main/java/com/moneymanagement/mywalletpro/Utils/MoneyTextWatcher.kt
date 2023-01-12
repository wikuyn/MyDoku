package com.moneymanagement.mywalletpro.Utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
import kotlin.math.log

class MoneyTextWatcher(editText: EditText) : TextWatcher {
    private val local : Locale = Locale("id", "ID")
    private val formatter : DecimalFormat = NumberFormat.getCurrencyInstance(local) as DecimalFormat
    private var editWeakReference : WeakReference<EditText>

    init {
        editWeakReference = WeakReference(editText)
        formatter.maximumIntegerDigits = 0
        formatter.roundingMode = RoundingMode.FLOOR
        var desimalSymbol = DecimalFormatSymbols(local)
        desimalSymbol.currencySymbol = desimalSymbol.currencySymbol+" "
        formatter.decimalFormatSymbols = desimalSymbol
    }


    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        var editText : EditText? = editWeakReference.get()
        if (editText == null || editText.text.toString().isEmpty()){
            Log.e("TAG", "beforeTextChanged: ")
        }
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        var editText : EditText? = editWeakReference.get()
        if (editText == null || editText.text.toString().isEmpty()){
            Log.e("TAG", "beforeTextChanged: ")
        }
    }

    override fun afterTextChanged(p0: Editable?) {
        var editText : EditText? = editWeakReference.get()
        if (editText == null || editText.text.toString().isEmpty()){
            Log.e("TAG", "beforeTextChanged: ")
            return
        }
        editText.removeTextChangedListener(this)

        var parsed : BigDecimal = parseCurrencyValue(editText.text.toString())
        var format = formatter.format(parsed)

        editText.setText(format)
        editText.setSelection(format.length)
        editText.addTextChangedListener(this)
    }

    private fun parseCurrencyValue(value : String): BigDecimal{
        try {
            val replaceRegex : String  = String.format("[%s,.\\s]",formatter.currency.getSymbol(local));
            val currencyValue: String  = value.replace(replaceRegex.toRegex(), "");
            return BigDecimal(currencyValue)
        } catch (e : Exception ) {
            Log.e("App", e.message, e);
        }
        return BigDecimal.ZERO;
    }
}