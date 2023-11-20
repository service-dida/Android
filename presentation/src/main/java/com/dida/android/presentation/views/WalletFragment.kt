package com.dida.android.presentation.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.dida.common.util.SnapPagerScrollListener
import com.dida.common.util.addSnapPagerScroll
import com.dida.common.util.performHapticEvent
import com.dida.common.util.repeatOnStarted
import com.dida.wallet.R
import com.dida.wallet.WalletNavigationAction
import com.dida.wallet.WalletViewModel
import com.dida.wallet.adapter.WalletAdapter
import com.dida.wallet.adapter.WalletHistoryAdapter
import com.dida.wallet.databinding.FragmentWalletBinding
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding, WalletViewModel>(R.layout.fragment_wallet),
    AppBarLayout.OnOffsetChangedListener {

    private val TAG = "WalletFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_wallet

    override val viewModel: WalletViewModel by viewModels()

    private val walletAdapter by lazy { WalletAdapter(viewModel) }
    private val walletHistoryAdapter by lazy { WalletHistoryAdapter() }
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        initToolbar()
        initAdapter()
        initSwipeRefresh()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is WalletNavigationAction.NavigateToBack -> navController.popBackStack()
                    is WalletNavigationAction.NavigateToSwapHistory -> navigate(WalletFragmentDirections.actionWalletFragmentToSwapHistoryFragment())
                    is WalletNavigationAction.NavigateToHotCard -> {}
                }
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            launch {
                viewModel.walletListState.collectLatest {
                    walletAdapter.submitList(it)
                }
            }

            launch {
                viewModel.currentHistoryState.collectLatest {
                    binding.emptyView.isVisible = it.isEmpty()
                    binding.nftHistoryRecyclerView.isVisible = it.isNotEmpty()
                    walletHistoryAdapter.submitList(it)
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    override fun onResume() {
        super.onResume()
        binding.walletAppbar.addOnOffsetChangedListener(this)
        viewModel.getHistory()
    }

    override fun onPause() {
        super.onPause()
        binding.walletAppbar.removeOnOffsetChangedListener(this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val isExpanded = verticalOffset == 0
        if (isExpanded != binding.swipeRefreshLayout.isEnabled) {
            binding.swipeRefreshLayout.isEnabled = isExpanded
        }
    }

    private fun initSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getWallet()
            requireContext().performHapticEvent()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            setNavigationIcon(com.dida.android.R.drawable.ic_baseline_close_24)
            setNavigationOnClickListener { viewModel.onBack() }
        }
        binding.walletAddressArea.setOnClickListener {
            val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", viewModel.walletAddressState.value)
            clipboard.setPrimaryClip(clip)
            showToastMessage("텍스트가 복사 되었습니다.")
        }
    }

    private fun initAdapter() {
        binding.nftHistoryRecyclerView.adapter = walletHistoryAdapter

        val snapPagerScrollListener = SnapPagerScrollListener(
            snapHelper = PagerSnapHelper(),
            type = SnapPagerScrollListener.ON_SETTLED,
            notifyOnInit = true,
            listener = object : SnapPagerScrollListener.OnChangeListener {
                override fun onSnapped(position: Int) {
                    //position 받아서 이벤트 처리
                }
            }
        )

        binding.walletCardRecyclerView.apply {
            adapter = walletAdapter
            addSnapPagerScroll()
            addOnScrollListener(snapPagerScrollListener)
        }
    }
}
