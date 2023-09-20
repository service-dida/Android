package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.common.util.addOnPagingListener
import com.dida.common.util.repeatOnResumed
import com.dida.common.util.repeatOnStarted
import com.dida.swap.history.R
import com.dida.swap.history.SwapHistoryViewModel
import com.dida.swap.history.adapter.SwapHistoryAdapter
import com.dida.swap.history.databinding.FragmentSwapHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

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
        viewLifecycleOwner.repeatOnResumed {
            viewModel.navigationEvent.collectLatest {
                binding.swapHistoryRefreshLayout.isRefreshing = false
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.swapHistoryState.collectLatest {
                swapHistoryAdapter.submitList(it.content)
            }
        }
    }

    override fun initAfterBinding() {

    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
        binding.swapHistoryRefreshLayout.setOnRefreshListener {
            viewModel.getSwapHistory()
        }
    }

    private fun initAdapter(){
        binding.swapHistoryRv.adapter = swapHistoryAdapter
        binding.swapHistoryRv.addOnPagingListener(
            arrivedBottom = { viewModel.nextPage() }
        )
    }
}
