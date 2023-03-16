package com.dida.android.presentation.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.dida.common.util.SnapPagerScrollListener
import com.dida.common.util.addSnapPagerScroll
import com.dida.common.util.repeatOnResumed
import com.dida.common.util.repeatOnStarted
import com.dida.wallet.R
import com.dida.wallet.WalletViewModel
import com.dida.wallet.adapter.WalletAdapter
import com.dida.wallet.adapter.WalletHistoryAdapter
import com.dida.wallet.databinding.FragmentWalletBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding, WalletViewModel>(R.layout.fragment_wallet) {

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
        exception = viewModel.errorEvent
        initToolbar()
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.repeatOnResumed {
            viewModel.navigationEvent.collectLatest {
                when(it) {
                    is com.dida.wallet.WalletNavigationAction.NavigateToBack -> navController.popBackStack()
                    is com.dida.wallet.WalletNavigationAction.NavigateToSwapHistory -> navigate(WalletFragmentDirections.actionWalletFragmentToSwapHistoryFragment())
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
                    walletHistoryAdapter.submitList(it)
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHistory()
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
            toastMessage("텍스트가 복사되었습니다.")
        }
    }

    private fun initAdapter() {
        binding.nftHistoryRecyclerView.adapter = walletHistoryAdapter

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
            adapter = walletAdapter
            addSnapPagerScroll()
            addOnScrollListener(listener)
        }
    }
}
