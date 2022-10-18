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
class CommonAlertDialog(private val alertModel: AlertModel) : BaseDialog<DialogCommonAlertBinding>(layoutId = R.layout.dialog_common_alert) {

    override val layoutResourceId: Int
        get() = R.layout.dialog_password

    override fun initStartView() {
        binding.alertModel = alertModel
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {}

}