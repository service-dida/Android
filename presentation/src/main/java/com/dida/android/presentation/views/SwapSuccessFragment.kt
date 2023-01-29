package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.password.PasswordDialog
import com.dida.swap.SwapNavigationAction
import com.dida.swap.databinding.FragmentSwapLoadingBinding
import com.dida.swap.databinding.FragmentSwapSuccessBinding
import com.dida.swap.loading.SwapLoadingNavigationAction
import com.dida.swap.loading.SwapLoadingViewModel
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

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    SwapSuccessNavigationAction.NavigateToHistory -> {
                        navigate(
                            SwapSuccessFragmentDirections.actionSwapSuccessFragmentToSwapHistoryFragment()
                        )
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {
    }
}
