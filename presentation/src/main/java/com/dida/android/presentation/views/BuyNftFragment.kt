package com.dida.android.presentation.views

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dida.buy.nft.BuyNftViewModel
import com.dida.buy.nft.R
import com.dida.buy.nft.databinding.FragmentBuyNftBinding

class BuyNftFragment : BaseFragment<FragmentBuyNftBinding, BuyNftViewModel>(R.layout.fragment_buy_nft) {

    private val TAG = "BuyNftFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_buy_nft

    override val viewModel : BuyNftViewModel by viewModels()

    private val navController by lazy { findNavController() }

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
            this.setTitleTextColor(ContextCompat.getColor(requireContext(), com.dida.android.R.color.white))
            this.setNavigationIcon(com.dida.android.R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
