package com.dida.ai.keyword.result.dialog

import androidx.fragment.app.viewModels
import com.dida.ai.R
import com.dida.ai.databinding.BottomAiPictureRestartBinding
import com.dida.common.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AiPictureRestartBottomSheet() : BaseBottomSheetDialogFragment<BottomAiPictureRestartBinding, AiPictureRestartViewModel>() {

    private val TAG = "AddNftBottomSheet"

    override val layoutResourceId: Int
        get() = R.layout.bottom_ai_picture_restart

    override val viewModel: AiPictureRestartViewModel by viewModels()

    override fun initStartView() {
        binding.vm = viewModel
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {}
}
