package com.dida.android.presentation.views

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.buy.nft.BuyNftViewModel
import com.dida.buy.nft.R
import com.dida.buy.nft.databinding.FragmentBuyNftBinding
import com.dida.password.PasswordDialog
import dagger.hilt.android.AndroidEntryPoint

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
        binding.priceTv.text =  args.price
        binding.bottomPriceTv.text = args.price
    }

    override fun initAfterBinding() {
        binding.buyBtn.setOnClickListener {
            PasswordDialog(6,"비밀번호 입력","6자리를 입력해주세요."){ success, password ->
                if(success){
                    viewModel.buyNft(password,args.marketId)
                }
            }.show(childFragmentManager,"BuyNftFragment")
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
}
