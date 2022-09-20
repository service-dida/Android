package com.dida.android.presentation.views.wallet

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.android.databinding.FragmentWalletBinding
import com.dida.android.presentation.adapter.mypage.WalletCardRecyclerViewAdapter
import com.dida.android.presentation.adapter.mypage.WalletNFTHistoryRecyclerViewAdapter
import com.dida.android.presentation.base.BaseFragment
import com.dida.domain.model.nav.mypage.WalletCardHolderModel
import com.dida.domain.model.nav.mypage.WalletNFTHistoryHolderModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class WalletFragment :
    BaseFragment<FragmentWalletBinding, WalletViewModel>(R.layout.fragment_wallet) {

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
            viewModel.navigationEvent.collect {
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
        val list = mutableListOf(
            WalletNFTHistoryHolderModel("", "user name here", "NFT name here", 1.65, true),
            WalletNFTHistoryHolderModel("", "user name here", "NFT name here", 1.65, false),
            WalletNFTHistoryHolderModel("", "user name here", "NFT name here", 1.65, true),
            WalletNFTHistoryHolderModel("", "user name here", "NFT name here", 1.65, false),
            WalletNFTHistoryHolderModel("", "user name here", "NFT name here", 1.65, true),
            WalletNFTHistoryHolderModel("", "user name here", "NFT name here", 1.65, false),
            WalletNFTHistoryHolderModel("", "user name here", "NFT name here", 1.65, true),
            WalletNFTHistoryHolderModel("", "user name here", "NFT name here", 1.65, false),
            WalletNFTHistoryHolderModel("", "user name here", "NFT name here", 1.65, true),
            WalletNFTHistoryHolderModel("", "user name here", "NFT name here", 1.65, false)
        )
        walletNFTHistoryRecyclerViewAdapter.submitList(list)
        binding.nftHistoryRecyclerView.adapter = walletNFTHistoryRecyclerViewAdapter


        val walletCardHolderModelList = mutableListOf(
            WalletCardHolderModel("20.09865", "KLAY"),
            WalletCardHolderModel("20.09865", "DIDA")
        )
        walletCardRecyclerViewAdapter.submitList(walletCardHolderModelList)
        binding.walletCardRecyclerView.adapter = walletCardRecyclerViewAdapter
    }

}