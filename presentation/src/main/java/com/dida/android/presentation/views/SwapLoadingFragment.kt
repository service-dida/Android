package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.dida.common.util.repeatOnStarted
import com.dida.create_community_input.CreateCommunityInputViewModel
import com.dida.swap.databinding.FragmentSwapLoadingBinding
import com.dida.swap.loading.SwapLoadingNavigationAction
import com.dida.swap.loading.SwapLoadingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SwapLoadingFragment : BaseFragment<FragmentSwapLoadingBinding, SwapLoadingViewModel>(com.dida.swap.R.layout.fragment_swap_loading) {

    private val TAG = "SwapLoadingFragment"

    override val layoutResourceId: Int
        get() = com.dida.swap.R.layout.fragment_swap_loading

    @Inject
    lateinit var assistedFactory: SwapLoadingViewModel.AssistedFactory
    override val viewModel: SwapLoadingViewModel by viewModels {
        SwapLoadingViewModel.provideFactory(
            assistedFactory,
            swapType = args.swapType,
            password = args.password,
            amount = args.amount
        )
    }

    private val args: SwapLoadingFragmentArgs by navArgs()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    SwapLoadingNavigationAction.NavigateToSuccess -> navigate(SwapLoadingFragmentDirections.actionSwapLoadingFragmentToSwapSuccessFragment(args.swapType))
                }
            }
        }
    }

    override fun initAfterBinding() {
    }
}
