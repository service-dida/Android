package com.dida.community_detail

import androidx.fragment.app.viewModels
import com.dida.common.base.BaseBottomSheetDialogFragment
import com.dida.community_detail.databinding.DialogBottomSheetDetailCommunityBinding

class DetailCommunityBottomSheetDialog(
    val itemClick: (MoreState) -> Unit
) : BaseBottomSheetDialogFragment<DialogBottomSheetDetailCommunityBinding, DetailCommunityViewModel>(){
    override val layoutResourceId: Int
        get() = R.layout.dialog_bottom_sheet_detail_community

    override val viewModel: DetailCommunityViewModel by viewModels()
    override fun initStartView() {
        binding.apply {
            this.vm
            this.lifecycleOwner = viewLifecycleOwner
        }
    }
    override fun initDataBinding() {}
    override fun initAfterBinding() {
        // 수정하기
        binding.updateBtn.setOnClickListener {
            itemClick(MoreState.Update)
            dismissAllowingStateLoss()
        }

        // 삭제하기
        binding.deleteBtn.setOnClickListener {
            itemClick(MoreState.Delete)
            dismissAllowingStateLoss()
        }
    }
}

sealed class MoreState {
    object Update: MoreState()
    object Delete: MoreState()
}
