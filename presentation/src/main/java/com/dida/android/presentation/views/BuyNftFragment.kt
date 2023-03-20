package com.dida.android.presentation.views

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.buy.nft.BuyNftNavigationAction
import com.dida.buy.nft.BuyNftViewModel
import com.dida.buy.nft.R
import com.dida.buy.nft.databinding.FragmentBuyNftBinding
import com.dida.common.base.DefaultAlertDialog
import com.dida.common.util.repeatOnResumed
import com.dida.password.PasswordDialog
import com.dida.recent_nft.RecentNftNavigationAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BuyNftFragment : BaseFragment<FragmentBuyNftBinding, BuyNftViewModel>(R.layout.fragment_buy_nft) {

    private val TAG = "BuyNftFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_buy_nft

    override val viewModel : BuyNftViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val args: BuyNftFragmentArgs by navArgs()
    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initData()
    }

    override fun initDataBinding() {
        binding.priceTv.text = args.price
        binding.bottomPriceTv.text = args.price

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is BuyNftNavigationAction.NavigateToSuccess -> navigate(BuyNftFragmentDirections.actionBuyNftFragmentToBuySuccessFragment(args.nftId))
                    is BuyNftNavigationAction.NavigateToFailAlert -> { failBuyAlert() }
                }
            }
        }
    }

    override fun initAfterBinding() {
        binding.buyBtn.setOnClickListener {
            PasswordDialog(6, "비밀번호 입력", "6자리를 입력해주세요.") { success, password ->
                if (success) {
                    viewModel.buyNft(password, args.marketId, args.price)
                }
            }.show(childFragmentManager, "BuyNftFragment")
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setTitleTextColor(ContextCompat.getColor(requireContext(), com.dida.android.R.color.white))
            this.setNavigationIcon(com.dida.android.R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun initData(){
        viewModel.initDetailNft(args.nftId,args.nftImg,args.nftTitle,args.userImg,args.userName,args.price,args.viewerNickname)
    }

    private fun failBuyAlert() {
        val res = com.dida.common.base.AlertModel(
            title = requireContext().getString(R.string.buy_fail_main_title),
            description = requireContext().getString(R.string.buy_fail_sub_title),
            noButtonTitle = requireContext().getString(com.dida.common.R.string.cancel),
            yesButtonTitle = requireContext().getString(R.string.buy_fail_ok_btn)
        )
        val dialog = DefaultAlertDialog(
            alertModel = res,
            clickNegative = {},
            clickPositive = {navController.navigate(BuyNftFragmentDirections.actionBuyNftFragmentToSwapFragment())}
        )
        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
    }
}
