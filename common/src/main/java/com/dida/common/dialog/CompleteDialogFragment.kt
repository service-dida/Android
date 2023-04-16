package com.dida.common.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.core.view.isVisible
import com.dida.common.R
import com.dida.common.base.BaseDialogFragment
import com.dida.common.util.repeatOnResumed
import kotlinx.coroutines.delay

class CompleteDialogFragment : BaseDialogFragment() {

    private var message: String? = null

    private lateinit var messageTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val root = inflater.inflate(R.layout.fragment_complete_dialog, container, false)

        messageTextView = root.findViewById(R.id.dialog_message_text)

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messageTextView.isVisible = !message.isNullOrBlank()
        messageTextView.text = message

        viewLifecycleOwner.repeatOnResumed {
            delay(1000L)
            dismissAllowingStateLoss()
        }
    }

    data class Builder(
        private var message: String? = null,
        private var cancelable: Boolean = true
    ) {

        fun message(message: String) = apply { this.message = message }

        fun cancelable(cancelable: Boolean) = apply { this.cancelable = cancelable }

        fun build() = CompleteDialogFragment().also {
            it.message = message
            it.isCancelable = cancelable
        }
    }

    interface OnClickListener {
        fun onClick()
    }

}
