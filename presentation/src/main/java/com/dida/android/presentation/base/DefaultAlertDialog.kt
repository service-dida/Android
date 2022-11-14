package com.dida.android.presentation.base

import com.dida.android.R
import com.dida.android.databinding.DialogCommonAlertBinding

data class AlertModel(
    val title : String,
    val description : String,
    val noButtonTitle : String,
    val yesButtonTitle : String
)

class DefaultAlertDialog(
    private val alertModel: AlertModel,
    private val clickNegative: () -> Unit,
    private val clickPossitive: () -> Unit
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
            clickPossitive.invoke()
            dismiss()
        }
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {}

}