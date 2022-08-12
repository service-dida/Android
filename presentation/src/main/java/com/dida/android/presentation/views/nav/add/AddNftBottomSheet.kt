package com.dida.android.presentation.views.nav.add

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import com.dida.android.R
import com.dida.android.databinding.BottomAddNftBinding
import com.dida.android.databinding.DialogPasswordBinding
import com.dida.android.presentation.base.BaseBottomSheetDialogFragment
import com.dida.android.presentation.views.password.PasswordReconfirmDialog
import com.dida.android.presentation.views.password.PasswordViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNftBottomSheet(val itemClick: (Int) -> Unit) :
    BaseBottomSheetDialogFragment<BottomAddNftBinding, AddNftViewModel>() {

    private val TAG = "AddNftBottomSheet"

    override val layoutResourceId: Int
        get() = R.layout.bottom_add_nft

    override val viewModel: AddNftViewModel by viewModels()

    override fun initStartView() {
        binding.vm = viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.okBtn.setOnClickListener {
            itemClick(0)
        }
    }
}