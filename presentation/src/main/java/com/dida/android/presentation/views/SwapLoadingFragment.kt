package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.dida.password.PasswordDialog
import com.dida.swap.SwapNavigationAction
import com.dida.swap.databinding.FragmentSwapLoadingBinding
import com.dida.swap.loading.SwapLoadingNavigationAction
import com.dida.swap.loading.SwapLoadingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

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
        viewModel.swap(args.swapType,args.password,args.amount.toDouble())
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    SwapLoadingNavigationAction.NavigateToSuccess -> {
                        //TODO : 성공화면으로 가야함
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {
    }
}
