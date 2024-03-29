package com.dida.common.customview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dida.common.R
import kotlinx.coroutines.delay


class CustomEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {
    init {
        //포커스에 따라 테두리색 변경
        setOnFocusChangeListener { _, isFocus ->
            if (isFocus) {
                setBackgroundResource(R.drawable.custom_brandlemon_radius10_surface5_width1)
                showKeyBoard()
            } else {
                setBackgroundResource(R.drawable.custom_surface2_radius10_surface5_width1)
                hideKeyBoard()
            }
        }

        //텍스트 삭제
        if (maxLines == 1) {
            val imgDrawable = ContextCompat.getDrawable(context, R.drawable.ic_remove_text)
            setCompoundDrawablesWithIntrinsicBounds(null, null, imgDrawable, null)
            setOnTouchListener(object : OnTouchListener {
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

    private fun showKeyBoard() {
        val inputMethodManager = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, 0)
    }

    private fun hideKeyBoard() {
        val inputMethodManager = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
    }

    fun errorEvent() {
        hideKeyBoard()
        this.setBackgroundResource(R.drawable.custom_noticered_radius10_surface5_width1)
    }
}
