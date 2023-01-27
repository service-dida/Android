package com.dida.add.bottom

import androidx.fragment.app.viewModels
import com.dida.add.R
import com.dida.add.databinding.BottomAddKeepNftBinding
import com.dida.common.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddKeepNftBottomSheet(
    val itemClick: () -> Unit
) : BaseBottomSheetDialogFragment<BottomAddKeepNftBinding, AddKeepViewModel>() {

    private val TAG = "AddNftBottomSheet"

    override val layoutResourceId: Int
        get() = R.layout.bottom_add_keep_nft

    override val viewModel: AddKeepViewModel by viewModels()

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
