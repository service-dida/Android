package com.dida.common.base

import com.dida.common.R
import com.dida.common.databinding.DialogCommonAlertBinding

data class AlertModel(
    val title : String,
    val description : String,
    val noButtonTitle : String,
    val yesButtonTitle : String
)

class DefaultAlertDialog(
    private val alertModel: AlertModel,
    private val clickNegative: () -> Unit,
    private val clickPositive: () -> Unit
) : BaseDialog<DialogCommonAlertBinding>(layoutId = R.layout.dialog_common_alert) {

    override val layoutResourceId: Int
        get() = R.layout.dialog_common_alert

    override fun initStartView() {
        binding.alertModel = alertModel

        binding.alertNoButton.setOnClickListener {
            clickNegative.invoke()
            dismiss()
        }
        binding.alertYesButton.setOnClickListener { 
            clickPositive.invoke()
            dismiss()
        }
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {}

}
