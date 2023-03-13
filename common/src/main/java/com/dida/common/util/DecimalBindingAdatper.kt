package com.dida.swap

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dida.common.util.Constants
import com.dida.common.util.addPriceDot


@BindingAdapter("decimalFormat")
fun EditText.setDecimalFormat(decimalDigits: Int) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            val currentValue = s.toString()
            if(currentValue.startsWith(".")){
                s?.delete(0,1)
            }else{
                if (currentValue.isEmpty()) {
                    return
                }
                if (currentValue.toDouble() > Constants.MAX_AMOUNT) {
                    this@setDecimalFormat.setText(Constants.MAX_AMOUNT.toString())
                }
                val dotPos = currentValue.indexOf(".")
                if (dotPos < 0) {
                    return
                }
                if (currentValue.length - dotPos - 1 > decimalDigits) {
                    this@setDecimalFormat.removeTextChangedListener(this)
                    s?.delete(dotPos + decimalDigits + 1, s.length)
                    this@setDecimalFormat.addTextChangedListener(this)
                }
            }
        }
    })
}

@BindingAdapter("decimalPrice")
fun TextView.setDecimalPrice(price: String) {
    if (price.isNullOrEmpty() || price == "NOT SALE" || price == "NO MARKETED") {
        this.text = "NOT SALE"
    } else {
        val roundedValue = (price.toDouble() * 100).toLong() / 100.0

        val formattedValue = if (roundedValue % 1 == 0.0) {
            String.format("%.0f", roundedValue)
        } else {
            String.format("%.2f", roundedValue)
        }

        this.text = "${addPriceDot(formattedValue)} dida"
    }
}

@BindingAdapter("decimalPrice")
fun TextView.setDecimalPrice(priceF: Float) {
    val price = priceF.toString()
    if (price.isNullOrEmpty() || price == "NOT SALE" || price == "NO MARKETED") {
        this.text = "NOT SALE"
    } else {
        val roundedValue = (price.toDouble() * 100).toLong() / 100.0

        val formattedValue = if (roundedValue % 1 == 0.0) {
            String.format("%.0f", roundedValue)
        } else {
            String.format("%.2f", roundedValue)
        }
        this.text = "${addPriceDot(formattedValue)} dida"
    }
}
