package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.common.adapter.CommunityAdapter
import com.dida.common.util.repeatOnStarted
import com.dida.common.util.successOrNull
import com.dida.nft.sale.AddSaleNftBottomSheet
import com.dida.nft_detail.DetailNftNavigationAction
import com.dida.nft_detail.DetailNftViewModel
import com.dida.nft_detail.bottom.DetailNftBottomSheet
import com.dida.nft_detail.bottom.DetailNftMenuType
import com.dida.nft_detail.databinding.FragmentDetailNftBinding
import com.dida.password.PasswordDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailNftFragment : BaseFragment<FragmentDetailNftBinding, DetailNftViewModel>(com.dida.nft_detail.R.layout.fragment_detail_nft) {

    private val TAG = "DetailNftFragment"

    override val layoutResourceId: Int
        get() = com.dida.nft_detail.R.layout.fragment_detail_nft

    @Inject
    lateinit var assistedFactory: DetailNftViewModel.AssistedFactory
    override val viewModel: DetailNftViewModel by viewModels {
        DetailNftViewModel.provideFactory(
            assistedFactory,
            cardId = args.cardId
        )
    }

    private val navController: NavController by lazy { findNavController() }
    private val args: DetailNftFragmentArgs by navArgs()
    private val communityAdapter by lazy { CommunityAdapter(viewModel) }

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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when(it) {
                    is DetailNftNavigationAction.NavigateToCommunity -> navigate(DetailNftFragmentDirections.actionDetailNftFragmentToCommunityFragment())
                    is DetailNftNavigationAction.NavigateToItemCommunity -> navigate(DetailNftFragmentDirections.actionDetailNftFragmentToCommunityDetailFragment(it.postId))
                    is DetailNftNavigationAction.NavigateToCreateCommunity -> navigate(DetailNftFragmentDirections.actionDetailNftFragmentToCreateCommunityFragment())
                    is DetailNftNavigationAction.NavigateToBuyNft -> navigate(
                        DetailNftFragmentDirections.actionDetailNftFragmentToBuyNftFragment(
                            it.nftId,
                            it.nftImg,
                            it.nftTitle,
                            it.userImg,
                            it.userName,
                            it.price,
                            it.viewerNickName,
                            it.marketId
                        )
                    )
                    is DetailNftNavigationAction.NavigateToHome -> navigate(DetailNftFragmentDirections.actionDetailNftFragmentToHomeFragment())
                    is DetailNftNavigationAction.NavigateToBack -> navController.popBackStack()
                    is DetailNftNavigationAction.NavigateToUserProfile -> navigate(DetailNftFragmentDirections.actionDetailNftFragmentToUserProfileFragment(it.userId))
                    is DetailNftNavigationAction.NavigateToSell -> showSellNftDialog()
                    is DetailNftNavigationAction.NavigateToReport -> {}
                    is DetailNftNavigationAction.NavigateToBlock -> {}
                    is DetailNftNavigationAction.NavigateToUpdate -> {}
                    is DetailNftNavigationAction.NavigateToDelete -> {}
                }
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.communityState.collectLatest {
                communityAdapter.submitList(it)
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    com.dida.nft_detail.R.id.action_heart -> viewModel.onLikePost()
                    com.dida.nft_detail.R.id.action_more -> {
                        val dialog = DetailNftBottomSheet(viewModel.detailOwnerTypeState.value) { type ->
                            when(type){
                                DetailNftMenuType.SELL -> showSellNftDialog()
                                DetailNftMenuType.CANCEL -> {}
                                DetailNftMenuType.REMOVE -> showDeleteNftDialog()
                                DetailNftMenuType.HIDE -> viewModel.onHideCard()
                                DetailNftMenuType.REPORT -> {}
                            }
                        }
                        dialog.show(childFragmentManager, TAG)
                    }
                }
                true
            }

            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun initAdapter() {
        binding.communityRecycler.adapter = communityAdapter
    }

    private fun showSellNftDialog(){
        val dialog = AddSaleNftBottomSheet { price ->
            PasswordDialog(6,"비밀번호 입력","6자리를 입력해주세요."){ success, password ->
                if(success){
                    viewModel.onSellCard(password, price.toDouble())
                }
            }.show(childFragmentManager,"DetailNftBottomSheet")
        }
        dialog.show(childFragmentManager, "DetailNftFragment")
    }

    private fun showDeleteNftDialog(){
        if (viewModel.detailNftState.value.successOrNull()?.price == "NOT SALE") {
            PasswordDialog(6, "비밀번호 입력", "6자리를 입력해주세요.") { success, password ->
                if (success) {
                    viewModel.deleteNft(password)
                }
            }.show(childFragmentManager, "DetailNftBottomSheet")
        } else {
            toastMessage("마켓에 올라가 있는 NFT는 삭제 할 수 없습니다.")
        }
    }
}
