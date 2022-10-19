package com.dida.android.util

import com.dida.android.R
import com.dida.android.databinding.DialogCommonAlertBinding
import com.dida.android.presentation.base.BaseDialog

data class AlertModel(
    val title : String,
    val description : String,
    val noButtonTitle : String,
    val yesButtonTitle : String
)
class CommonAlertDialog(private val alertModel: AlertModel,
                        private val clickNo: () -> Unit,
                        private val clickYes: () -> Unit) : BaseDialog<DialogCommonAlertBinding>(layoutId = R.layout.dialog_common_alert) {

    override val layoutResourceId: Int
        get() = R.layout.dialog_common_alert

    override fun initStartView() {
        binding.alertModel = alertModel

        binding.alertNoButton.setOnClickListener {
            clickNo.invoke()
            dismiss()
        }
        binding.alertYesButton.setOnClickListener { 
            clickYes.invoke()
            dismiss()
        }
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {}

}