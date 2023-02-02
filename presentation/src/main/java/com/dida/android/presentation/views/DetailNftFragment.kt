package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.common.adapter.CommunityAdapter
import com.dida.nft_detail.DetailNftNavigationAction
import com.dida.nft_detail.DetailNftViewModel
import com.dida.nft_detail.databinding.FragmentDetailNftBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailNftFragment : BaseFragment<FragmentDetailNftBinding, DetailNftViewModel>(com.dida.nft_detail.R.layout.fragment_detail_nft) {

    private val TAG = "DetailNftFragment"

    override val layoutResourceId: Int
        get() = com.dida.nft_detail.R.layout.fragment_detail_nft

    override val viewModel : DetailNftViewModel by viewModels()
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
        viewModel.getDetailNft(args.cardId)
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when(it) {
                    is DetailNftNavigationAction.NavigateToCommunity -> navigate(DetailNftFragmentDirections.actionDetailNftFragmentToCommunityFragment())
                    is DetailNftNavigationAction.NavigateToItemCommunity -> navigate(DetailNftFragmentDirections.actionDetailNftFragmentToCommunityDetailFragment(0))
                    is DetailNftNavigationAction.NavigateToCreateCommunity -> navigate(DetailNftFragmentDirections.actionDetailNftFragmentToCreateCommunityFragment())
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            // 우측 메뉴
            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    com.dida.nft_detail.R.id.action_heart -> viewModel.postlikeNft(args.cardId)
                    com.dida.nft_detail.R.id.action_more -> {}
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
}
