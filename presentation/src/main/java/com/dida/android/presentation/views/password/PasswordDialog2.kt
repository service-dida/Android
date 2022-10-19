package com.dida.android.presentation.views.password

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import com.dida.android.R
import com.dida.android.databinding.DialogPassword2Binding
import com.dida.android.presentation.base.BaseBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordDialog2(
    private val mainTitleStr : String,
    private val subTitleStr : String,
    private val password: (String) -> Unit) : BaseBottomSheetDialogFragment<DialogPassword2Binding, PasswordViewModel2>() {

    override val layoutResourceId: Int
        get() = R.layout.dialog_password2

    override val viewModel: PasswordViewModel2 by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
            this.mainTitle = mainTitleStr
            this.subTitle = subTitleStr
        }
        exception = viewModel.errorEvent
        dialogFullScreen()
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {}

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
}