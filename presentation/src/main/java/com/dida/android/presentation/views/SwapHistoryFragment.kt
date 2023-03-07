package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.swap.history.R
import com.dida.swap.history.SwapHistoryViewModel
import com.dida.swap.history.adapter.SwapHistoryAdapter
import com.dida.swap.history.databinding.FragmentSwapHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SwapHistoryFragment : BaseFragment<FragmentSwapHistoryBinding, SwapHistoryViewModel>(R.layout.fragment_swap_history) {

    private val TAG = "SwapHistoryFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_swap_history

    override val viewModel : SwapHistoryViewModel by viewModels()

    private val navController: NavController by lazy { findNavController() }

    private val swapHistoryAdapter by lazy { SwapHistoryAdapter(viewModel) }
    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initAdapter()
        viewModel.getSwapHistory()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.navigationEvent.collectLatest {
                    binding.swapHistoryRefreshLayout.isRefreshing = false
                }
            }
            launch {
                viewModel.swapHistoryState.collectLatest {
                    swapHistoryAdapter.submitList(it)
                }
            }
        }
    }

    override fun initAfterBinding() {

    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.android.R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
        binding.swapHistoryRefreshLayout.setOnRefreshListener {
            viewModel.getSwapHistory()
        }
    }

    private fun initAdapter(){
        binding.swapHistoryRv.adapter = swapHistoryAdapter
    }
}
