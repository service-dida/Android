package com.dida.common.base

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.dida.common.R
import com.dida.common.bindingadapters.setOnSingleClickListener
import com.dida.common.util.repeatOnCreated

class DefaultDialogFragment : BaseDialogFragment() {

    private var title: String? = null
    private var message: String? = null
    private var positiveButtonLabel: String? = null
    private var neutralButtonLabel: String? = null
    private var negativeButtonLabel: String? = null
    private var positiveButtonListener: OnClickListener? = null
    private var neutralButtonListener: OnClickListener? = null
    private var negativeButtonListener: OnClickListener? = null
    private var dismissListener: OnDismissListener? = null
    private var contents: View? = null

    private val isMessageOnly: Boolean
        get() = title.isNullOrBlank() && !message.isNullOrBlank()

    private lateinit var titleTextView: TextView
    private lateinit var messageTextView: TextView
    private lateinit var contentsLayout: FrameLayout
    private lateinit var marginView: View
    private lateinit var positiveButton: Button
    private lateinit var neutralButton: Button
    private lateinit var negativeButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val root = inflater.inflate(R.layout.fragment_default_dialog, container, false)

        titleTextView = root.findViewById(R.id.dialog_title_text)
        messageTextView = root.findViewById(R.id.dialog_message_text)
        contentsLayout = root.findViewById(R.id.dialog_contents_layout)
        marginView = root.findViewById(R.id.dialog_message_type2_margin_view)
        positiveButton = root.findViewById(R.id.dialog_positive_button)
        neutralButton = root.findViewById(R.id.dialog_neutral_button)
        negativeButton = root.findViewById(R.id.dialog_negative_button)

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTextView.isVisible = !title.isNullOrBlank()
        titleTextView.text = title

        messageTextView.isVisible = !message.isNullOrBlank()
        messageTextView.text = message
        val messageTextColor = if (isMessageOnly) {
            ContextCompat.getColor(requireContext(), R.color.default_dialog_message_type2_text)
        } else {
            ContextCompat.getColor(requireContext(), R.color.default_dialog_message_type1_text)
        }
        messageTextView.setTextColor(messageTextColor)

        viewLifecycleOwner.repeatOnCreated {
            contents?.let {
                if(it.parent != null) { dismissAllowingStateLoss() }
                else { contentsLayout.addView(it) }
            }
        }

        marginView.isVisible = isMessageOnly

        positiveButton.isVisible = !positiveButtonLabel.isNullOrBlank()
        positiveButton.text = positiveButtonLabel
        positiveButton.setOnSingleClickListener {
            positiveButtonListener?.onClick()
            dismissAllowingStateLoss()
        }

        neutralButton.isVisible = !neutralButtonLabel.isNullOrBlank()
        neutralButton.text = neutralButtonLabel
        neutralButton.setOnSingleClickListener {
            neutralButtonListener?.onClick()
            dismissAllowingStateLoss()
        }

        negativeButton.isVisible = !negativeButtonLabel.isNullOrBlank()
        negativeButton.text = negativeButtonLabel
        negativeButton.setOnSingleClickListener {
            negativeButtonListener?.onClick()
            dismissAllowingStateLoss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListener?.onDismiss()
    }

    data class Builder(
        private var title: String? = null,
        private var message: String? = null,
        private var contents: View? = null,
        private var positiveButtonLabel: String? = null,
        private var neutralButtonLabel: String? = null,
        private var negativeButtonLabel: String? = null,
        private var positiveButtonListener: OnClickListener? = null,
        private var neutralButtonListener: OnClickListener? = null,
        private var negativeButtonListener: OnClickListener? = null,
        private var dismissListener: OnDismissListener? = null,
        private var cancelable: Boolean = true
    ) {
        fun title(title: String) = apply { this.title = title }

        fun message(message: String) = apply { this.message = message }

        fun contents(contents: View) = apply { this.contents = contents }

        fun positiveButton(label: String, listener: OnClickListener? = null) = apply {
            this.positiveButtonLabel = label
            this.positiveButtonListener = listener
        }

        fun neutralButton(label: String, listener: OnClickListener? = null) = apply {
            this.neutralButtonLabel = label
            this.neutralButtonListener = listener
        }

        fun negativeButton(label: String, listener: OnClickListener? = null) = apply {
            this.negativeButtonLabel = label
            this.negativeButtonListener = listener
        }

        fun dismissListener(listener: OnDismissListener) = apply { this.dismissListener = listener }

        fun cancelable(cancelable: Boolean) = apply { this.cancelable = cancelable }

        fun build() = DefaultDialogFragment().also {
            it.title = title
            it.message = message
            it.contents = contents
            it.positiveButtonLabel = positiveButtonLabel
            it.neutralButtonLabel = neutralButtonLabel
            it.negativeButtonLabel = negativeButtonLabel
            it.positiveButtonListener = positiveButtonListener
            it.neutralButtonListener = neutralButtonListener
            it.negativeButtonListener = negativeButtonListener
            it.dismissListener = dismissListener
            it.isCancelable = cancelable
        }
    }

    interface OnDismissListener {
        fun onDismiss()
    }

    interface OnClickListener {
        fun onClick()
    }

}
