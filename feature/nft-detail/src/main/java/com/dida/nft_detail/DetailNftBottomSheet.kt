package com.dida.nft_detail

import androidx.fragment.app.viewModels
import com.dida.common.base.BaseBottomSheetDialogFragment
import com.dida.nft_detail.databinding.DialogBottomDetailNftBinding

enum class DetailNftMenuType{
    SELL,
    CANCEL,
    REMOVE,
    HIDE,
    REPORT
}
class DetailNftBottomSheet(
    val callback: (clickGallery: DetailNftMenuType) -> Unit
) : BaseBottomSheetDialogFragment<DialogBottomDetailNftBinding, DetailNftViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.dialog_bottom_detail_nft

    override val viewModel: DetailNftViewModel by viewModels()
    override fun initStartView() {
        binding.apply {
            this.vm
            this.lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {
        binding.sellBtn.setOnClickListener {
            callback.invoke(DetailNftMenuType.SELL)
            dismiss()
        }
        binding.cancelBtn.setOnClickListener {
            callback.invoke(DetailNftMenuType.CANCEL)
            dismiss()
        }
        binding.removeBtn.setOnClickListener {
            callback.invoke(DetailNftMenuType.REMOVE)
            dismiss()
        }
        binding.hideBtn.setOnClickListener {
            callback.invoke(DetailNftMenuType.HIDE)
            dismiss()
        }
        binding.reportBtn.setOnClickListener {
            callback.invoke(DetailNftMenuType.REPORT)
            dismiss()
        }
    }
}
