package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.common.adapter.CommunityPagingAdapter
import com.dida.community.CommunityNavigationAction
import com.dida.community.CommunityViewModel
import com.dida.community.adapter.HotCardAdapter
import com.dida.community.databinding.FragmentCommunityBinding
import com.dida.domain.model.nav.community.ReservationNFTHolderModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding, CommunityViewModel>(com.dida.community.R.layout.fragment_community) {

    private val TAG = "CommunityFragment"

    override val layoutResourceId: Int
        get() = com.dida.community.R.layout.fragment_community

    override val viewModel : CommunityViewModel by viewModels()
    private val hotCardAdapter by lazy { HotCardAdapter(viewModel) }
    private val communityPagingAdapter by lazy { CommunityPagingAdapter(viewModel) }


    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initRecyclerView()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.navigationEvent.collectLatest {
                    when(it) {
                        is CommunityNavigationAction.NavigateToDetail -> navigate(CommunityFragmentDirections.actionCommunityFragmentToCommunityDetailFragment(it.postId))
                        is CommunityNavigationAction.NavigateToCommunityWrite -> navigate(CommunityFragmentDirections.actionCommunityFragmentToCreateCommunityFragment())
                        is CommunityNavigationAction.NavigateToNftDetail -> navigate(CommunityFragmentDirections.actionCommunityFragmentToDetailNftFragment(it.cardId))
                    }
                }
            }

            launch {
                viewModel.postsState.collectLatest {
                    communityPagingAdapter.submitData(it)
                }
            }

            launch {
                viewModel.hotCardState.collectLatest {
                    hotCardAdapter.submitList(it)
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initRecyclerView(){
        binding.activeCommunityRecyclerView.adapter = hotCardAdapter
        binding.communityRecyclerView.adapter = communityPagingAdapter
    }
}
