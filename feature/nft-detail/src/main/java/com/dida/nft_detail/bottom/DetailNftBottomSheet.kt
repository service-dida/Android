package com.dida.nft_detail.bottom

import androidx.fragment.app.viewModels
import com.dida.common.base.BaseBottomSheetDialogFragment
import com.dida.nft_detail.DetailNftViewModel
import com.dida.nft_detail.R
import com.dida.nft_detail.databinding.DialogBottomDetailNftBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailNftBottomSheet(
    val detailOwnerType: DetailOwnerType,
    val callback: (clickGallery: DetailNftMenuType) -> Unit
) : BaseBottomSheetDialogFragment<DialogBottomDetailNftBinding, DetailNftViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.dialog_bottom_detail_nft

    override val viewModel: DetailNftViewModel by viewModels()
    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
            this.type = detailOwnerType
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

enum class DetailNftMenuType{
    SELL,
    CANCEL,
    REMOVE,
    HIDE,
    REPORT
}

enum class DetailOwnerType {
    ALL,
    MINE_AND_SALE,
    MINE_AND_NOTSALE,
    NOTMINE_AND_SALE,
    NOTMINE_AND_NOTSALE,
    NOTLOGIN_AND_SALE,
    NOTLOGIN_AND_NOTSALE
}
