package com.dida.android.presentation.views

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.android.R
import com.dida.common.adapter.CommunityPagingAdapter
import com.dida.common.dialog.CompleteDialogFragment
import com.dida.common.dialog.DefaultDialogFragment
import com.dida.common.ui.report.ReportBottomSheet
import com.dida.common.ui.report.ReportType
import com.dida.common.util.*
import com.dida.common.widget.DefaultSnackBar
import com.dida.community.CommunityMessageAction
import com.dida.community.CommunityNavigationAction
import com.dida.community.CommunityViewModel
import com.dida.community.adapter.HotCardsContainerAdapter
import com.dida.community.databinding.FragmentCommunityBinding
import com.dida.community_detail.DetailCommunityMessageAction
import com.dida.community_detail.DetailCommunityNavigationAction
import com.dida.domain.model.main.HotCards
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
            launch {
                viewModel.navigationEvent.collectLatest {
                    when (it) {
                        is CommunityNavigationAction.NavigateToDetail -> navigate(CommunityFragmentDirections.actionCommunityFragmentToCommunityDetailFragment(it.postId))
                        is CommunityNavigationAction.NavigateToCommunityWrite -> navigate(CommunityFragmentDirections.actionCommunityFragmentToCreateCommunityFragment())
                        is CommunityNavigationAction.NavigateToNftDetail -> navigate(CommunityFragmentDirections.actionCommunityFragmentToDetailNftFragment(it.cardId))
                        is CommunityNavigationAction.NavigateToReport -> showReportDialog(it.postId)
                        is CommunityNavigationAction.NavigateToBlock -> showBlockPostDialog(postId = it.postId)
                        is CommunityNavigationAction.NavigateToUpdate -> {}
                        is CommunityNavigationAction.NavigateToDelete -> {}
                        is CommunityNavigationAction.NavigateToRefresh -> communityPagingAdapter.retry()
                    }
                }
            }


            launch {
                viewModel.blockEvent.collectLatest {
                    showMessageSnackBar(getString(R.string.delete_reply_message))
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

            launch {
                viewModel.navigateToReportEvent.collectLatest {
                    if (it) {
                        showReportCompleteDialog()
                        communityPagingAdapter.refresh()
                    } else {
                        showToastMessage(requireContext().getString(R.string.already_report_message))
                    }

                }
            }

            launch {
                viewModel.navigateToBlockEvent.collectLatest {
                    if (it) {
                        showBlockCompleteDialog()
                        communityPagingAdapter.refresh()
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {}

    override fun onResume() {
        super.onResume()
        setFragmentResultListener(DIDAINTENT.RESULT_SCREEN_COMMUNITY) { _, bundle ->
            if (bundle.getBoolean(DIDAINTENT.RESULT_KEY_CREATE)) showCreateCompleteDialog()
            if (bundle.getBoolean(DIDAINTENT.RESULT_KEY_REPORT)) showReportCompleteDialog()
            if (bundle.getBoolean(DIDAINTENT.RESULT_KEY_BLOCK)) showBlockCompleteDialog()
        }
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

    private fun showCreateCompleteDialog() {
        CompleteDialogFragment.Builder()
            .message(getString(R.string.create_post_dialog_message))
            .build()
            .show(childFragmentManager, "complete_dialog")
    }

    private fun showReportCompleteDialog() {
        CompleteDialogFragment.Builder()
            .message(getString(R.string.report_dialog_message))
            .build()
            .show(childFragmentManager, "complete_dialog")
    }

    private fun showBlockPostDialog(postId: Long) {
        DefaultDialogFragment.Builder()
            .title(getString(com.dida.community_detail.R.string.block_post_title))
            .message(getString(com.dida.community_detail.R.string.block_post_description))
            .positiveButton(getString(com.dida.community_detail.R.string.block_post_positive), object : DefaultDialogFragment.OnClickListener {
                override fun onClick() {
                    viewModel.onPostBlock(postId)
                }
            })
            .negativeButton(getString(com.dida.community_detail.R.string.block_post_negative))
            .build()
            .show(childFragmentManager, "block_post_dialog")

    }

    private fun showBlockCompleteDialog() {
        CompleteDialogFragment.Builder()
            .message(getString(R.string.block_dialog_message))
            .build()
            .show(childFragmentManager, "complete_dialog")
    }

    private fun showReportDialog(userId: Long) {
        ReportBottomSheet { confirm, content ->
            if (confirm) viewModel.onReport(
                type = ReportType.USER,
                reportId = userId,
                content = content
            )
        }.show(childFragmentManager, "Report Dialog")
    }

    private fun showMessageSnackBar(message: String) {
        DefaultSnackBar.Builder()
            .view(binding.root)
            .message(message)
            .build()
    }
}
