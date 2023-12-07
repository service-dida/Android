package com.dida.android.presentation.views

import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.dida.android.R
import com.dida.common.adapter.CommunityAdapter
import com.dida.common.dialog.CompleteDialogFragment
import com.dida.common.dialog.DefaultDialogFragment
import com.dida.common.ui.report.ReportBottomSheet
import com.dida.common.util.DIDAINTENT
import com.dida.common.util.addOnPagingListener
import com.dida.common.util.performHapticEvent
import com.dida.common.util.repeatOnStarted
import com.dida.common.util.successOrNull
import com.dida.community.CommunityNavigationAction
import com.dida.community.CommunityViewModel
import com.dida.community.adapter.CommunityHeaderAdapter
import com.dida.community.adapter.CommunityHeaderItem
import com.dida.community.adapter.HotPostHeaderAdapter
import com.dida.community.adapter.HotPostHeaderItem
import com.dida.community.adapter.HotPostContainerAdapter
import com.dida.community.databinding.FragmentCommunityBinding
import com.dida.domain.main.model.Block
import com.dida.domain.main.model.HotPosts
import com.dida.domain.main.model.Report
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding, CommunityViewModel>(com.dida.community.R.layout.fragment_community) {

    private val TAG = "CommunityFragment"

    override val layoutResourceId: Int
        get() = com.dida.community.R.layout.fragment_community

    override val viewModel : CommunityViewModel by viewModels()

    private lateinit var adapter: ConcatAdapter
    private val hotPostHeaderAdapter by lazy { HotPostHeaderAdapter() }
    private val hotPostContainerAdapter by lazy { HotPostContainerAdapter(viewModel) }
    private val communityHeaderAdapter by lazy { CommunityHeaderAdapter() }
    private val communityAdapter by lazy { CommunityAdapter(viewModel) }

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
                        hotPostContainerAdapter.submitList(item)
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
    }

    private fun initSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getCommunity()
            requireContext().performHapticEvent()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initRecyclerView() {
        val adapterConfig = ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(true)
            .setStableIdMode(ConcatAdapter.Config.StableIdMode.SHARED_STABLE_IDS)
            .build()

        adapter = ConcatAdapter(
            adapterConfig,
            hotPostHeaderAdapter,
            hotPostContainerAdapter,
            communityHeaderAdapter,
            communityAdapter
        )

        hotPostHeaderAdapter.submitList(listOf(HotPostHeaderItem))
        communityHeaderAdapter.submitList(listOf(CommunityHeaderItem))

        binding.communityRecyclerview.adapter = adapter
        binding.communityRecyclerview.addOnPagingListener(
            arrivedBottom = { viewModel.onNextPage() }
        )
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
