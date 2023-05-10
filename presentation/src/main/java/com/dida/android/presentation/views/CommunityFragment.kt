package com.dida.android.presentation.views

import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.android.R
import com.dida.common.adapter.CommunityPagingAdapter
import com.dida.common.dialog.CompleteDialogFragment
import com.dida.common.ui.report.ReportBottomSheet
import com.dida.common.ui.report.ReportType
import com.dida.common.util.*
import com.dida.community.CommunityNavigationAction
import com.dida.community.CommunityViewModel
import com.dida.community.adapter.HotCardsContainerAdapter
import com.dida.community.databinding.FragmentCommunityBinding
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
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is CommunityNavigationAction.NavigateToDetail -> navigate(CommunityFragmentDirections.actionCommunityFragmentToCommunityDetailFragment(it.postId))
                    is CommunityNavigationAction.NavigateToCommunityWrite -> navigate(CommunityFragmentDirections.actionCommunityFragmentToCreateCommunityFragment())
                    is CommunityNavigationAction.NavigateToNftDetail -> navigate(CommunityFragmentDirections.actionCommunityFragmentToDetailNftFragment(it.cardId))
                    is CommunityNavigationAction.NavigateToReport -> showReportDialog(it.postId)
                    is CommunityNavigationAction.NavigateToBlock -> {}
                    is CommunityNavigationAction.NavigateToUpdate -> {}
                    is CommunityNavigationAction.NavigateToDelete -> {}
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
        setFragmentResultListener(SCREEN.COMMUNITY) { _, bundle ->
            if (bundle.getBoolean(EVENT.CREATE)) showCreateCompleteDialog()
            if (bundle.getBoolean(EVENT.REPORT)) showReportCompleteDialog()
            if (bundle.getBoolean(EVENT.BLOCK)) showBlockCompleteDialog()
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

    private fun showBlockCompleteDialog() {
        CompleteDialogFragment.Builder()
            .message(getString(R.string.block_dialog_message))
            .build()
            .show(childFragmentManager, "complete_dialog")
    }

    private fun showReportDialog(postId: Long) {
        ReportBottomSheet { confirm, content ->
            if (confirm) viewModel.onReport(
                type = ReportType.POST,
                reportId = postId,
                content = content
            )
        }.show(childFragmentManager, "Report Dialog")
    }
}
