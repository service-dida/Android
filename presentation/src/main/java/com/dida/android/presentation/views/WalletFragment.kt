package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.dida.common.util.SnapPagerScrollListener
import com.dida.common.util.addSnapPagerScroll
import com.dida.wallet.R
import com.dida.wallet.adapter.WalletCardRecyclerViewAdapter
import com.dida.wallet.adapter.WalletNFTHistoryRecyclerViewAdapter
import com.dida.wallet.databinding.FragmentWalletBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding, com.dida.wallet.WalletViewModel>(R.layout.fragment_wallet) {

    private val TAG = "WalletFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_wallet

    override val viewModel: com.dida.wallet.WalletViewModel by viewModels()

    private val walletCardRecyclerViewAdapter by lazy {  WalletCardRecyclerViewAdapter(viewModel) }
    private val walletNFTHistoryRecyclerViewAdapter = WalletNFTHistoryRecyclerViewAdapter()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.navigationEvent.collectLatest {
                when(it) {
                    is com.dida.wallet.WalletNavigationAction.NavigateToBack -> navController.popBackStack()
                    is com.dida.wallet.WalletNavigationAction.NavigateToSwapHistory -> navigate(WalletFragmentDirections.actionWalletFragmentToSwapHistoryFragment())
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.walletListState.collectLatest {
                    walletCardRecyclerViewAdapter.submitList(it)
                }
            }

            launch {
                viewModel.currentHistoryState.collectLatest {
                    walletNFTHistoryRecyclerViewAdapter.submitList(it)
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            setNavigationIcon(com.dida.android.R.drawable.ic_baseline_close_24)
            setNavigationOnClickListener { viewModel.onBack() }
        }
    }

    private fun initAdapter() {
        binding.nftHistoryRecyclerView.adapter = walletNFTHistoryRecyclerViewAdapter

        val listener = SnapPagerScrollListener(
            PagerSnapHelper(),
            SnapPagerScrollListener.ON_SETTLED,
            true,
            object : SnapPagerScrollListener.OnChangeListener {
                override fun onSnapped(position: Int) {
                    //position 받아서 이벤트 처리
                }
            }
        )

        binding.walletCardRecyclerView.apply {
            adapter = walletCardRecyclerViewAdapter
            addSnapPagerScroll()
            addOnScrollListener(listener)
        }
    }
}
