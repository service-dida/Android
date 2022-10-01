package com.dida.android.presentation.views.nav.add

import androidx.fragment.app.viewModels
import com.dida.android.R
import com.dida.android.databinding.BottomAddNftBinding
import com.dida.android.presentation.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNftBottomSheet(
    val itemClick: () -> Unit
) : BaseBottomSheetDialogFragment<BottomAddNftBinding, AddNftViewModel>() {

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
            itemClick()
            dismiss()
        }
    }
}