package com.dida.android.presentation.views

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.buy.nft.BuyNftNavigationAction
import com.dida.buy.nft.BuyNftViewModel
import com.dida.buy.nft.R
import com.dida.buy.nft.databinding.FragmentBuyNftBinding
import com.dida.common.dialog.DefaultDialogFragment
import com.dida.common.dialog.ImageDialogFragment
import com.dida.password.PasswordDialog
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
        viewModel.initDetailNft(
            cardId = args.nftId,
            imgUrl = args.nftImg,
            title = args.nftTitle,
            profileUrl = args.userImg,
            nickname = args.userName,
            price = args.price,
            viewerNickname = args.viewerNickname
        )
    }

    private fun failBuyAlert() {
        ImageDialogFragment.Builder()
            .title(getString(R.string.buy_fail_main_title))
            .message(getString(R.string.buy_fail_sub_title))
            .image(com.dida.common.R.drawable.ic_dida_dialog_foreground)
            .positiveButton(getString(R.string.buy_fail_ok_btn), object : ImageDialogFragment.OnClickListener {
                override fun onClick() {
                    navController.navigate(BuyNftFragmentDirections.actionBuyNftFragmentToSwapFragment())
                }
            })
            .negativeButton(getString(com.dida.common.R.string.cancel))
            .build()
            .show(childFragmentManager, "fail_buy_dialog")
    }
}
