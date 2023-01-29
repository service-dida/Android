package com.dida.swap

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter


@BindingAdapter("decimalFormat")
fun EditText.setDecimalFormat( decimalDigits: Int) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            val currentValue = s.toString()
            if (currentValue.isEmpty()) {
                return
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
    })
}