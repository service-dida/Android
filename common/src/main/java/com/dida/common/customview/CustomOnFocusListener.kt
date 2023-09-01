package com.dida.common.customview

import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.dida.common.R

fun EditText.addOnFocusListener(
    focus: (Boolean) -> Unit
) {
    val editText = this
    editText.apply {
        setOnFocusChangeListener { _, isFocus ->
            if (isFocus) {
                setBackgroundResource(R.drawable.custom_brandlemon_radius10_surface5_width1)
                showKeyBoard()
                focus(true)
            } else {
                setBackgroundResource(R.drawable.custom_surface2_radius10_surface5_width1)
                hideKeyBoard()
                focus(false)
            }
        }

        //텍스트 삭제
        if (maxLines == 1) {
            val imgDrawable = ContextCompat.getDrawable(context, R.drawable.ic_remove_text)
            setCompoundDrawablesWithIntrinsicBounds(null, null, imgDrawable, null)
            setOnTouchListener(object : View.OnTouchListener {
                @SuppressLint("ClickableViewAccessibility")
                override fun onTouch(v: View, motionEvent: MotionEvent): Boolean {
                    val view = v as EditText
                    if (motionEvent.action == MotionEvent.ACTION_UP) {
                        if (motionEvent.rawX >= view.right - view.compoundDrawables[2].bounds.width()) {
                            setText("")
                            return true
                        }
                    }
                    return false
                }
            })
        }
    }
}

private fun EditText.showKeyBoard() {
    val editText = this
    val inputMethodManager = editText.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(editText, 0)
}

private fun EditText.hideKeyBoard() {
    val editText = this
    val inputMethodManager = editText.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
}

private fun EditText.errorEvent() {
    val editText = this
    hideKeyBoard()
    editText.setBackgroundResource(R.drawable.custom_noticered_radius10_surface5_width1)
}
