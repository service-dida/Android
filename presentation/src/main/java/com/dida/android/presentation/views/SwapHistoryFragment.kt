package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.swap.history.R
import com.dida.swap.history.SwapHistoryViewModel
import com.dida.swap.history.databinding.FragmentSwapHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SwapHistoryFragment : BaseFragment<FragmentSwapHistoryBinding, SwapHistoryViewModel>(R.layout.fragment_swap_history) {

    private val TAG = "SwapFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_swap_history

    override val viewModel : SwapHistoryViewModel by viewModels()

    private val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.android.R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
