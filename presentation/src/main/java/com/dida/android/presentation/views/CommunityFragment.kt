package com.dida.android.presentation.views

import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.android.R
import com.dida.common.adapter.CommunityAdapter
import com.dida.common.dialog.CompleteDialogFragment
import com.dida.common.dialog.DefaultDialogFragment
import com.dida.common.ui.report.ReportBottomSheet
import com.dida.common.util.DIDAINTENT
import com.dida.common.util.addOnPagingListener
import com.dida.common.util.repeatOnStarted
import com.dida.common.util.successOrNull
import com.dida.community.CommunityNavigationAction
import com.dida.community.CommunityViewModel
import com.dida.community.adapter.HotCardsContainerAdapter
import com.dida.community.databinding.FragmentCommunityBinding
import com.dida.domain.main.model.Block
import com.dida.domain.main.model.HotPosts
import com.dida.domain.main.model.Report
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// FIXME : Paging 수정 필요
@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding, CommunityViewModel>(com.dida.community.R.layout.fragment_community) {

    private val TAG = "CommunityFragment"

    override val layoutResourceId: Int
        get() = com.dida.community.R.layout.fragment_community

    override val viewModel : CommunityViewModel by viewModels()
    private val hotCardsContainerAdapter by lazy { HotCardsContainerAdapter(viewModel) }
    private val communityAdapter by lazy { CommunityAdapter(viewModel) }

    private var lastScrollY = 0

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        initRecyclerView()
        initSwipeRefresh()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is CommunityNavigationAction.NavigateToDetail -> navigate(CommunityFragmentDirections.actionCommunityFragmentToCommunityDetailFragment(it.postId))
                    is CommunityNavigationAction.NavigateToCommunityWrite -> navigate(CommunityFragmentDirections.actionCommunityFragmentToCreateCommunityFragment())
                    is CommunityNavigationAction.NavigateToNftDetail -> navigate(CommunityFragmentDirections.actionCommunityFragmentToDetailNftFragment(it.cardId))
                    is CommunityNavigationAction.NavigateToReport -> showReportDialog(it.postId)
                    is CommunityNavigationAction.NavigateToBlock -> showBlockPostDialog(postId = it.postId)
                    is CommunityNavigationAction.NavigateToUpdate -> {}
                    is CommunityNavigationAction.NavigateToDelete -> {}
                }
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            launch {
                viewModel.postsState.collectLatest {
                    communityAdapter.submitList(it.content)
                }
            }

            launch {
                viewModel.hotCardState.collectLatest {
                    it.successOrNull()?.let { hotCards ->
                        val item = if (hotCards.isNotEmpty()) listOf(HotPosts.Contents(hotCards)) else emptyList()
                        hotCardsContainerAdapter.submitList(item)
                    }
                }
            }

            launch {
                viewModel.navigateToReportEvent.collectLatest { (type, succes) ->
                    if (succes) {
                        when (type) {
                            Report.MEMBER -> showCompleteDialog(getString(R.string.report_user_dialog_message))
                            Report.POST -> showCompleteDialog(getString(R.string.report_dialog_message))
                            else -> {}
                        }
                        viewModel.getCommunity()
                    } else {
                        showToastMessage(requireContext().getString(R.string.already_report_message))
                    }

                }
            }

            launch {
                viewModel.navigateToBlockEvent.collectLatest { (type, succes) ->
                    if (succes) {
                        when (type) {
                            Block.MEMBER -> showCompleteDialog(getString(R.string.block_user_dialog_message))
                            Block.POST -> showCompleteDialog(getString(R.string.block_dialog_message))
                            else -> {}
                        }
                        viewModel.getCommunity()
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {}

    override fun onResume() {
        super.onResume()
        setFragmentResultListener(DIDAINTENT.RESULT_SCREEN_COMMUNITY) { _, bundle ->
            if (bundle.getBoolean(DIDAINTENT.RESULT_KEY_CREATE)) showCompleteDialog(getString(R.string.create_post_dialog_message))
            if (bundle.getBoolean(DIDAINTENT.RESULT_KEY_POST_REPORT)) showCompleteDialog(getString(R.string.report_dialog_message))
            if (bundle.getBoolean(DIDAINTENT.RESULT_KEY_USER_REPORT)) showCompleteDialog(getString(R.string.report_user_dialog_message))
            if (bundle.getBoolean(DIDAINTENT.RESULT_KEY_POST_BLOCK)) showCompleteDialog(getString(R.string.block_dialog_message))
            if (bundle.getBoolean(DIDAINTENT.RESULT_KEY_USER_BLOCK)) showCompleteDialog(getString(R.string.block_user_dialog_message))
            viewModel.getCommunity()
        }
        getLastScrollY()
    }

    private fun initSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getCommunity()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onPause() {
        super.onPause()
        setLastScrollY()
    }

    private fun initRecyclerView() {
        binding.activeCommunityRecyclerView.adapter = hotCardsContainerAdapter
        binding.communityRecyclerView.adapter = communityAdapter
        binding.communityRecyclerView.addOnPagingListener(
            arrivedBottom = { viewModel.onNextPage() }
        )
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

    private fun showBlockPostDialog(postId: Long) {
        DefaultDialogFragment.Builder()
            .title(getString(com.dida.community_detail.R.string.block_post_title))
            .message(getString(com.dida.community_detail.R.string.block_post_description))
            .positiveButton(getString(com.dida.community_detail.R.string.block_post_positive), object : DefaultDialogFragment.OnClickListener {
                override fun onClick() {
                    viewModel.onBlock(type = Block.POST, blockId = postId)
                }
            })
            .negativeButton(getString(com.dida.community_detail.R.string.block_post_negative))
            .build()
            .show(childFragmentManager, "block_user_dialog")

    }

    private fun showCompleteDialog(message: String) {
        CompleteDialogFragment.Builder()
            .message(message)
            .build()
            .show(childFragmentManager, "complete_dialog")
    }

    private fun showReportDialog(userId: Long) {
        ReportBottomSheet { confirm, content ->
            if (confirm) viewModel.onReport(
                type = Report.MEMBER,
                reportId = userId,
                content = content
            )
        }.show(childFragmentManager, "Report Dialog")
    }
}
