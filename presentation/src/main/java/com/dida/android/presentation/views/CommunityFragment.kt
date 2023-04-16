package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.common.adapter.CommunityPagingAdapter
import com.dida.common.util.repeatOnStarted
import com.dida.common.util.successOrNull
import com.dida.community.CommunityNavigationAction
import com.dida.community.CommunityViewModel
import com.dida.community.adapter.HotCardAdapter
import com.dida.community.adapter.HotCardsContainerAdapter
import com.dida.community.databinding.FragmentCommunityBinding
import com.dida.domain.model.main.HotCards
import com.dida.domain.model.main.HotItems
import com.dida.home.adapter.HotsContainerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding, CommunityViewModel>(com.dida.community.R.layout.fragment_community) {

    private val TAG = "CommunityFragment"

    override val layoutResourceId: Int
        get() = com.dida.community.R.layout.fragment_community

    override val viewModel : CommunityViewModel by viewModels()
    private val hotCardsContainerAdapter by lazy { HotCardsContainerAdapter(viewModel) }
    private val communityPagingAdapter by lazy { CommunityPagingAdapter(viewModel) }

    private var lastScrollY = 0

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initRecyclerView()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is CommunityNavigationAction.NavigateToDetail -> navigate(CommunityFragmentDirections.actionCommunityFragmentToCommunityDetailFragment(it.postId))
                    is CommunityNavigationAction.NavigateToCommunityWrite -> navigate(CommunityFragmentDirections.actionCommunityFragmentToCreateCommunityFragment())
                    is CommunityNavigationAction.NavigateToNftDetail -> navigate(CommunityFragmentDirections.actionCommunityFragmentToDetailNftFragment(it.cardId))
                }
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            launch {
                viewModel.postsState.collectLatest {
                    communityPagingAdapter.submitData(it)
                }
            }

            launch {
                viewModel.hotCardState.collectLatest {
                    it.successOrNull()?.let { hotCards ->
                        val item = if (hotCards.isNotEmpty()) listOf(HotCards.Contents(hotCards)) else emptyList()
                        hotCardsContainerAdapter.submitList(item)
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    override fun onResume() {
        super.onResume()
        communityPagingAdapter.refresh()
        getLastScrollY()
    }

    override fun onPause() {
        super.onPause()
        setLastScrollY()
    }

    private fun initRecyclerView() {
        binding.activeCommunityRecyclerView.adapter = hotCardsContainerAdapter
        binding.communityRecyclerView.adapter = communityPagingAdapter
    }

    private fun setLastScrollY() {
        lastScrollY = binding.communityScroll.scrollY
    }

    private fun getLastScrollY() {
        if (lastScrollY > 0) {
            binding.communityScroll.scrollTo(0, lastScrollY)
            lastScrollY = 0
        }
    }
}
