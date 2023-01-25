package com.dida.android.presentation.views.wallet

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.dida.android.R
import com.dida.android.databinding.FragmentWalletBinding
import com.dida.android.presentation.adapter.mypage.WalletCardRecyclerViewAdapter
import com.dida.android.presentation.adapter.mypage.WalletNFTHistoryRecyclerViewAdapter
import com.dida.android.presentation.views.BaseFragment
import com.dida.common.util.SnapPagerScrollListener
import com.dida.common.util.addSnapPagerScroll
import com.dida.domain.model.nav.mypage.WalletCardHolderModel
import com.dida.domain.model.nav.mypage.WalletNFTHistoryHolderModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding, WalletViewModel>(R.layout.fragment_wallet) {

    private val TAG = "WalletFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_wallet

    override val viewModel: WalletViewModel by viewModels()

    private val walletCardRecyclerViewAdapter = WalletCardRecyclerViewAdapter()
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
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when(it) {
                    is WalletNavigationAction.NavigateToBack -> navController.popBackStack()
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_baseline_close_24)
            setNavigationOnClickListener { viewModel.onBack() }
        }
    }

    private fun initAdapter() {
        binding.nftHistoryRecyclerView.adapter = walletNFTHistoryRecyclerViewAdapter
        val exampleList = mutableListOf(
            WalletNFTHistoryHolderModel(1, "", "user name here", "구매한 NFT111", 1.65, true),
            WalletNFTHistoryHolderModel(2, "", "user name here", "NFT name here", 1.65, false),
            WalletNFTHistoryHolderModel(3, "", "user name here", "구매한 NFT222", 1.65, true),
            WalletNFTHistoryHolderModel(4, "", "user name here", "구매한 NFT111", 1.65, false),
            WalletNFTHistoryHolderModel(5, "", "user name here", "구매한 NFT222", 1.65, true),
            WalletNFTHistoryHolderModel(6, "", "user name here", "NFT name here", 1.65, false),
            WalletNFTHistoryHolderModel(7, "", "user name here", "구매한 NFT222", 1.65, true),
            WalletNFTHistoryHolderModel(8, "", "user name here", "NFT name here", 1.65, false),
            WalletNFTHistoryHolderModel(9, "", "user name here", "구매한 NFT222", 1.65, true),
            WalletNFTHistoryHolderModel(10, "", "user name here", "NFT name here", 1.65, false)
        )

        viewModel.setNftHistory(exampleList)
        lifecycleScope.launchWhenStarted {
            viewModel.currentHistoryState.collectLatest { list ->
                walletNFTHistoryRecyclerViewAdapter.submitList(list)
            }
        }

        val walletCardHolderModelList = mutableListOf(
            WalletCardHolderModel("20.09865", "KLAY"),
            WalletCardHolderModel("333.09865", "DIDA")
        )

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

        walletCardRecyclerViewAdapter.submitList(walletCardHolderModelList)
        binding.walletCardRecyclerView.apply {
            adapter = walletCardRecyclerViewAdapter
            addSnapPagerScroll()
            addOnScrollListener(listener)
        }
    }

}
