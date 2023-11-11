package com.dida.ai.keyword.result.dialog

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.ai.R
import com.dida.ai.databinding.BottomAiPictureRestartBinding
import com.dida.common.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AiPictureRestartBottomSheet(
    val onRestartMenu: (RestartMenu) -> Unit
) : BaseBottomSheetDialogFragment<BottomAiPictureRestartBinding, AiPictureRestartViewModel>() {

    private val TAG = "AddNftBottomSheet"

    override val layoutResourceId: Int
        get() = R.layout.bottom_ai_picture_restart

    override val viewModel: AiPictureRestartViewModel by viewModels()

    override fun initStartView() {
        binding.vm = viewModel
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationAction.collectLatest {
                onRestartMenu(it)
            }
        }
    }

    override fun initAfterBinding() {}
}
