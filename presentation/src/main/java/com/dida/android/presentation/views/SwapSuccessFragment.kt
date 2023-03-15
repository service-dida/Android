package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.dida.common.util.repeatOnResumed
import com.dida.swap.databinding.FragmentSwapSuccessBinding
import com.dida.swap.success.SwapSuccessNavigationAction
import com.dida.swap.success.SwapSuccessViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SwapSuccessFragment : BaseFragment<FragmentSwapSuccessBinding, SwapSuccessViewModel>(com.dida.swap.R.layout.fragment_swap_success) {

    private val TAG = "SwapSuccessFragment"

    override val layoutResourceId: Int
        get() = com.dida.swap.R.layout.fragment_swap_success

    override val viewModel : SwapSuccessViewModel by viewModels()

    private val args: SwapSuccessFragmentArgs by navArgs()
    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        viewModel.initSuccessData(args.swapType)
    }

    override fun initDataBinding() {
        viewLifecycleOwner.repeatOnResumed {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    SwapSuccessNavigationAction.NavigateToHistory -> navigate(SwapSuccessFragmentDirections.actionSwapSuccessFragmentToSwapHistoryFragment())
                }
            }
        }
    }

    override fun initAfterBinding() {
    }
}
