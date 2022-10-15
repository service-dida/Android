package com.dida.android.presentation.views.password

import android.graphics.Color
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import com.dida.android.R
import com.dida.android.databinding.DialogPasswordBinding
import com.dida.android.presentation.base.BaseBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordDialog(
    private val walletCheck: Boolean,
    val password: (String) -> Unit
) : BaseBottomSheetDialogFragment<DialogPasswordBinding, PasswordViewModel>() {

    private val TAG = "PasswordDialog"

    override val layoutResourceId: Int
        get() = R.layout.dialog_password

    override val viewModel: PasswordViewModel by viewModels()

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
            // 지갑이 이미 있는 경우
            if(walletCheck) {
                password(viewModel.stackToString())
                dismiss()
            }
            // 지갑을 처음 만드는 경우
            else {
                val dialog = PasswordReconfirmDialog(){ password ->
                    if(viewModel.stackToString() == password){
                        //TODO : 지갑 생성 API 호출
                        password(viewModel.stackToString())
                        dismiss()
                    }else{
                        Toast.makeText(requireContext(), "비밀번호가 같지 않습니다.", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                    viewModel.clearStack()
                }
                dialog.show(childFragmentManager, "PasswordDialog")
            }
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
                dialog!!.findViewById(com.google.android.material.R.id.design_bottom_sheet)
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