package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.dida.buy.nft.R
import com.dida.buy.nft.databinding.FragmentBuySuccessBinding
import com.dida.buy.nft.success.BuySuccessNavigationAction
import com.dida.buy.nft.success.BuySuccessViewModel
import com.dida.nft.sale.AddSaleNftBottomSheet
import com.dida.password.PasswordDialog
import com.dida.swap.loading.SwapLoadingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BuySuccessFragment : BaseFragment<FragmentBuySuccessBinding, BuySuccessViewModel>(R.layout.fragment_buy_success) {

    private val TAG = "BuySuccessFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_buy_success

    @Inject
    lateinit var assistedFactory: BuySuccessViewModel.AssistedFactory
    override val viewModel: BuySuccessViewModel by viewModels {
        BuySuccessViewModel.provideFactory(
            assistedFactory,
            cardId = args.nftId
        )
    }

    private val args: BuySuccessFragmentArgs by navArgs()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is BuySuccessNavigationAction.NavigateToMypage -> { navigate(BuySuccessFragmentDirections.actionBuySuccessFragmentToMyPageFragment()) }
                    is BuySuccessNavigationAction.NavigateToSaleNft -> {
                        val dialog = AddSaleNftBottomSheet { price ->
                            PasswordDialog(6,"비밀번호 입력","6자리를 입력해주세요."){ success, password ->
                                if (success) {
                                    viewModel.sellNft(password, price.toDouble())
                                }else {
                                    if (password == "reset") {
                                        navigate(BuySuccessFragmentDirections.actionBuySuccessFragmentToSettingFragment())
                                    }
                                }
                            }.show(childFragmentManager,"DetailNftBottomSheet")
                        }
                        dialog.show(childFragmentManager, "DetailNftFragment")
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {}
}
