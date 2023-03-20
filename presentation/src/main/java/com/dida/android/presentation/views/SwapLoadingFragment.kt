package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.dida.common.util.repeatOnResumed
import com.dida.common.util.repeatOnStarted
import com.dida.swap.databinding.FragmentSwapLoadingBinding
import com.dida.swap.loading.SwapLoadingNavigationAction
import com.dida.swap.loading.SwapLoadingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SwapLoadingFragment : BaseFragment<FragmentSwapLoadingBinding, SwapLoadingViewModel>(com.dida.swap.R.layout.fragment_swap_loading) {

    private val TAG = "SwapLoadingFragment"

    override val layoutResourceId: Int
        get() = com.dida.swap.R.layout.fragment_swap_loading

    override val viewModel : SwapLoadingViewModel by viewModels()

    private val args: SwapLoadingFragmentArgs by navArgs()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        viewModel.initLoadingData(args.swapType)
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    SwapLoadingNavigationAction.NavigateToSuccess -> navigate(SwapLoadingFragmentDirections.actionSwapLoadingFragmentToSwapSuccessFragment(args.swapType))
                }
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.swapTypeState.collectLatest {
                viewModel.swap(args.swapType, args.password, args.amount.toDouble())
            }
        }
    }

    override fun initAfterBinding() {
    }
}
