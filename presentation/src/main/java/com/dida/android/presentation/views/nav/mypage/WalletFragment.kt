package com.dida.android.presentation.views.nav.mypage

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dida.android.R
import com.dida.android.databinding.FragmentWalletBinding
import com.dida.domain.model.nav.mypage.WalletCardHolderModel
import com.dida.domain.model.nav.mypage.WalletNFTHistoryHolderModel
import com.dida.android.presentation.adapter.mypage.WalletCardRecyclerViewAdapter
import com.dida.android.presentation.adapter.mypage.WalletNFTHistoryRecyclerViewAdapter
import com.dida.android.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment :
    BaseFragment<FragmentWalletBinding, WalletViewModel>(R.layout.fragment_wallet) {

    private val TAG = "WalletFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_wallet

    override val viewModel: WalletViewModel by viewModels()

    override fun initStartView() {
        binding.vm = viewModel
        initToolbar()
        initRecyclerView()
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        binding.nftHistoryAllBtn.setOnClickListener(nftHistoryTypeClickListener)
        binding.nftHistoryBuyBtn.setOnClickListener(nftHistoryTypeClickListener)
        binding.nftHistorySellBtn.setOnClickListener(nftHistoryTypeClickListener)
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_close_24)
        binding.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
    }

    private fun initRecyclerView() {
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

        binding.nftHistoryRecyclerView.apply {
            adapter = WalletNFTHistoryRecyclerViewAdapter(list)
            layoutManager = LinearLayoutManager(requireContext())
        }

        val WalletCardHolderModelList = mutableListOf(
            WalletCardHolderModel("20.09865", "KLAY"),
            WalletCardHolderModel("20.09865", "DIDA")
        )
        binding.walletCardRecyclerView.apply {
            adapter = WalletCardRecyclerViewAdapter(WalletCardHolderModelList)
            val mLayoutManager = LinearLayoutManager(requireContext())
            mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = mLayoutManager
        }
    }

    private val nftHistoryTypeClickListener: View.OnClickListener = View.OnClickListener {
        when (it.id) {
            R.id.nft_history_all_btn -> {
                viewModel.changeNFTHistoryFocusType(0)
            }
            R.id.nft_history_buy_btn -> {
                viewModel.changeNFTHistoryFocusType(1)
            }
            R.id.nft_history_sell_btn -> {
                viewModel.changeNFTHistoryFocusType(2)
            }
        }
    }
}