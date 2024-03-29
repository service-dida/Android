package com.dida.android.presentation.views

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.common.adapter.CommunityAdapter
import com.dida.common.ui.report.ReportBottomSheet
import com.dida.common.util.likeAnimation
import com.dida.common.util.performHapticEvent
import com.dida.common.util.repeatOnStarted
import com.dida.common.util.successOrNull
import com.dida.domain.main.model.Report
import com.dida.image_viewer.ImageViewerActivity
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

@AndroidEntryPoint
class DetailNftFragment : BaseFragment<FragmentDetailNftBinding, DetailNftViewModel>(com.dida.nft_detail.R.layout.fragment_detail_nft) {

    private val TAG = "DetailNftFragment"

    override val layoutResourceId: Int
        get() = com.dida.nft_detail.R.layout.fragment_detail_nft

    override val viewModel: DetailNftViewModel by viewModels()

    private val navController: NavController by lazy { findNavController() }
    private val args: DetailNftFragmentArgs by navArgs()
    private val communityAdapter by lazy { CommunityAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        viewModel.setCardId(args.cardId)
        initToolbar()
        initAdapter()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when(it) {
                    is DetailNftNavigationAction.NavigateToCommunity -> navigate(DetailNftFragmentDirections.actionDetailNftFragmentToCommunityFragment())
                    is DetailNftNavigationAction.NavigateToItemCommunity -> navigate(DetailNftFragmentDirections.actionDetailNftFragmentToCommunityDetailFragment(it.postId))
                    is DetailNftNavigationAction.NavigateToCreateCommunity -> navigate(DetailNftFragmentDirections.actionDetailNftFragmentToCreateCommunityFragment())
                    is DetailNftNavigationAction.NavigateToBuyNft -> navigate(DetailNftFragmentDirections.actionDetailNftFragmentToBuyNftFragment(it.nftId))
                    is DetailNftNavigationAction.NavigateToHome -> navigate(DetailNftFragmentDirections.actionDetailNftFragmentToHomeFragment())
                    is DetailNftNavigationAction.NavigateToBack -> navController.popBackStack()
                    is DetailNftNavigationAction.NavigateToUserProfile -> navigate(DetailNftFragmentDirections.actionDetailNftFragmentToUserProfileFragment(it.userId))
                    is DetailNftNavigationAction.NavigateToSell -> showSellNftDialog()
                    is DetailNftNavigationAction.NavigateToReport -> {}
                    is DetailNftNavigationAction.NavigateToBlock -> {}
                    is DetailNftNavigationAction.NavigateToUpdate -> {}
                    is DetailNftNavigationAction.NavigateToDelete -> {}
                    is DetailNftNavigationAction.NavigateToOwnerShipHistory -> navigate(DetailNftFragmentDirections.actionDetailNftFragmentToOwnerShipHIstoryFragment(it.nftId))
                    is DetailNftNavigationAction.NavigateToCancel -> { viewModel.setCardId(args.cardId) }
                    is DetailNftNavigationAction.NavigateToWritePost -> navigate(DetailNftFragmentDirections.actionDetailNftFragmentToCommunityCommunityInputFragment(args.cardId, true))
                    is DetailNftNavigationAction.NavigateToImageDetail -> {
                        val intent = ImageViewerActivity.starterIntent(
                            context = requireContext(),
                            imageUrl = it.imageUrl,
                            imageTitle = it.imageTitle,
                            imageDescription = it.imageDescription
                        )
                        startActivity(intent)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.nftLikeState.collectLatest {
                binding.likeImage.likeAnimation(it)
                requireContext().performHapticEvent()
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            launch {
                viewModel.communityState.collectLatest {
                    binding.emptyView.isVisible = it.isEmpty()
                    binding.communityRecycler.isVisible = it.isNotEmpty()
                    communityAdapter.submitList(it)
                }
            }

            launch {
                viewModel.navigateToReportEvent.collectLatest {
                    if (it.second) navController.popBackStack()
                    else showToastMessage(requireContext().getString(R.string.already_report_message))
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    com.dida.nft_detail.R.id.action_heart -> viewModel.onLikeNft()
                    com.dida.nft_detail.R.id.action_more -> {
                        DetailNftBottomSheet(viewModel.detailOwnerTypeState.value) { type ->
                            when(type){
                                DetailNftMenuType.SELL -> showSellNftDialog()
                                DetailNftMenuType.CANCEL -> showCancelNftDialog()
                                DetailNftMenuType.REMOVE -> showDeleteNftDialog()
                                DetailNftMenuType.HIDE -> viewModel.onHideCard()
                                DetailNftMenuType.REPORT -> showReportDialog(args.cardId)
                            }
                        }.show(childFragmentManager, TAG)
                    }
                }
                true
            }

            // 뒤로가기 버튼
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun initAdapter() {
        binding.communityRecycler.adapter = communityAdapter
    }

    private fun showSellNftDialog(){
        AddSaleNftBottomSheet { price ->
            PasswordDialog(6, "비밀번호 입력", "6자리를 입력해주세요.") { success, password ->
                if (success) {
                    viewModel.onSellCard(password, price.toDouble())
                } else {
                    if (password == "reset") {
                        navigate(DetailNftFragmentDirections.actionDetailNftFragmentToSettingFragment())
                    }
                }
            }.show(childFragmentManager, "DetailNftBottomSheet")
        }.show(childFragmentManager, "DetailNftFragment")
    }

    private fun showDeleteNftDialog(){
        if (viewModel.detailNftState.value.successOrNull()?.nftInfo?.price == "NOT SALE") {
            PasswordDialog(6, "비밀번호 입력", "6자리를 입력해주세요.") { success, password ->
                if (success) {
                    viewModel.deleteNft(password)
                }else {
                    if (password == "reset") {
                        navigate(DetailNftFragmentDirections.actionDetailNftFragmentToSettingFragment())
                    }
                }
            }.show(childFragmentManager, "DetailNftBottomSheet")
        } else {
            showToastMessage("마켓에 올라가 있는 NFT는 삭제 할 수 없습니다.")
        }
    }

    private fun showCancelNftDialog(){
        PasswordDialog(6, "비밀번호 입력", "6자리를 입력해주세요.") { success, password ->
            if (success) {
                viewModel.onCancelCard(password)
            } else {
                if (password == "reset") {
                    navigate(DetailNftFragmentDirections.actionDetailNftFragmentToSettingFragment())
                }
            }
        }.show(childFragmentManager, "DetailNftBottomSheet")
    }


    private fun showReportDialog(cardId: Long) {
        ReportBottomSheet { confirm, content ->
            if (confirm) viewModel.onReport(
                type = Report.NFT,
                reportId = cardId,
                content = content
            )
        }.show(childFragmentManager, "Report Dialog")
    }
}
