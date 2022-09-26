package com.dida.android.presentation.views.password

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import com.dida.android.R
import com.dida.android.databinding.DialogPasswordReconfirmBinding
import com.dida.android.presentation.base.BaseBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordReconfirmDialog(
    val password : (String)->Unit
) : BaseBottomSheetDialogFragment<DialogPasswordReconfirmBinding, PasswordReconfirmViewModel>() {

    private val TAG = "PasswordReconfirmDialog"

    override val layoutResourceId: Int
        get() = R.layout.dialog_password_reconfirm

    override val viewModel: PasswordReconfirmViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        dialogFullScreen()
    }

    override fun initDataBinding() {
        viewModel.completeLiveData.observe(viewLifecycleOwner){
            password(viewModel.stackToString())
            dismiss()
        }
    }

    override fun initAfterBinding() {
        binding.dialogPaymentKeypad0.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad1.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad2.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad3.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad4.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad5.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad6.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad7.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad8.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypad9.setOnClickListener(numberClickLister)
        binding.dialogPaymentKeypadBack.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
            viewModel.removeStack()
        }
    }

    private fun dialogFullScreen() {
        if (dialog != null) {
            val bottomSheet: View =
                dialog!!.findViewById(androidx.navigation.ui.R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        val view = view
        view!!.post {
            val parent = view!!.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
            bottomSheetBehavior!!.peekHeight = view!!.measuredHeight
            parent.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private val numberClickLister: View.OnClickListener = View.OnClickListener {
        it.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
        viewModel.addStack((it as TextView).text.toString().toInt())
    }
}