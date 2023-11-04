package com.dida.android.presentation.views

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import com.dida.android.R
import com.dida.common.adapter.CommentsAdapter
import com.dida.common.dialog.DefaultDialogFragment
import com.dida.common.ui.report.ReportBottomSheet
import com.dida.common.util.DIDAINTENT
import com.dida.common.util.addOnPagingListener
import com.dida.common.util.repeatOnStarted
import com.dida.common.widget.DefaultSnackBar
import com.dida.community_detail.DetailCommunityMessageAction
import com.dida.community_detail.DetailCommunityNavigationAction
import com.dida.community_detail.DetailCommunityViewModel
import com.dida.community_detail.adapter.CommentEmptyAdapter
import com.dida.community_detail.adapter.CommentEmptyItem
import com.dida.community_detail.adapter.DetailCommunityHeaderAdapter
import com.dida.community_detail.databinding.FragmentDetailCommunityBinding
import com.dida.domain.main.model.Block
import com.dida.domain.main.model.Report
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// FIXME : 댓글 수정 화면 필요
@AndroidEntryPoint
class DetailCommunityFragment : BaseFragment<FragmentDetailCommunityBinding, DetailCommunityViewModel>(com.dida.community_detail.R.layout.fragment_detail_community) {

    private val TAG = "DetailCommunityFragment"

    override val layoutResourceId: Int
        get() = com.dida.community_detail.R.layout.fragment_detail_community

    override val viewModel: DetailCommunityViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val args: DetailCommunityFragmentArgs by navArgs()

    private lateinit var adapter: ConcatAdapter
    private val detailCommunityHeaderAdapter by lazy { DetailCommunityHeaderAdapter(viewModel) }
    private val commentEmptyAdapter by lazy { CommentEmptyAdapter() }
    private val commentsAdapter by lazy { CommentsAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        initToolbar()
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.navigationEvent.collectLatest {
                    when(it) {
                        is DetailCommunityNavigationAction.NavigateToBack -> navController.popBackStack()
                        is DetailCommunityNavigationAction.NavigateToUserProfile -> navigate(DetailCommunityFragmentDirections.actionCommunityDetailFragmentToUserProfileFragment(it.userId))
                        is DetailCommunityNavigationAction.NavigateToCardDetail -> navigate(DetailCommunityFragmentDirections.actionCommunityDetailFragmentToDetailNftFragment(it.cardId))
                        is DetailCommunityNavigationAction.NavigateToUserReport -> showReportDialog(it.userId)
                        is DetailCommunityNavigationAction.NavigateToUserBlock -> showBlockUserDialog(userId = it.userId)
                        is DetailCommunityNavigationAction.NavigateToUpdate -> {}
                        is DetailCommunityNavigationAction.NavigateToDelete -> showDeleteCommentDialog(commentId = it.commentId)
                        is DetailCommunityNavigationAction.NavigateToPostReport -> showPostReportDialog(postId = it.postId)
                        is DetailCommunityNavigationAction.NavigateToPostBlock -> showBlockPostDialog(postId = it.postId)
                        is DetailCommunityNavigationAction.NavigateToDeletePostDialog -> showDeleteDialog(postId = it.postId)
                        is DetailCommunityNavigationAction.NavigateToUpdatePost -> navigate(DetailCommunityFragmentDirections.actionCommunityDetailFragmentToCommunityCommunityInputFragment(cardId = 0, createState = false, postId = it.postId))
                    }
                }
            }

            launch {
                viewModel.messageEvent.collectLatest {
                    when(it) {
                        is DetailCommunityMessageAction.DeletePostMessage -> showMessageSnackBar(getString(R.string.delete_post_message))
                        is DetailCommunityMessageAction.DeleteReplyMessage -> showMessageSnackBar(getString(R.string.delete_reply_message))
                    }
                }
            }
        }

        viewLifecycleOwner.repeatOnStarted {

            launch {
                viewModel.postState.collectLatest {
                    it?.let {
                        detailCommunityHeaderAdapter.submitList(listOf(it))
                    }
                }
            }

            launch {
                viewModel.comments.collectLatest {
                    if (it.content.isEmpty()) {
                        if (it.pageSize != 0) commentEmptyAdapter.submitList(listOf(CommentEmptyItem))
                    } else {
                        commentEmptyAdapter.submitList(emptyList())
                        commentsAdapter.submitList(it.content)
                    }

                    if (viewModel.isWrite.value) keyboardHide()
                }
            }

            launch {
                viewModel.navigateToReportEvent.collectLatest {
                    if (it.second) {
                        when (it.first) {
                            Report.POST -> setFragmentResult(DIDAINTENT.RESULT_SCREEN_COMMUNITY, bundleOf(DIDAINTENT.RESULT_KEY_POST_REPORT to true))
                            Report.MEMBER -> setFragmentResult(DIDAINTENT.RESULT_SCREEN_COMMUNITY, bundleOf(DIDAINTENT.RESULT_KEY_USER_REPORT to true))
                            Report.NFT -> setFragmentResult(DIDAINTENT.RESULT_SCREEN_COMMUNITY, bundleOf(DIDAINTENT.RESULT_KEY_CARD_REPORT to true))
                            else -> {}
                        }
                        navController.popBackStack()
                    } else {
                        showToastMessage(requireContext().getString(R.string.already_report_message))
                    }
                }
            }

            launch {
                viewModel.navigateToBlockEvent.collectLatest {
                    if (it.second) {
                        when (it.first) {
                            Block.POST -> setFragmentResult(DIDAINTENT.RESULT_SCREEN_COMMUNITY, bundleOf(DIDAINTENT.RESULT_KEY_POST_BLOCK to true))
                            Block.MEMBER -> setFragmentResult(DIDAINTENT.RESULT_SCREEN_COMMUNITY, bundleOf(DIDAINTENT.RESULT_KEY_USER_BLOCK to true))
                            Block.NFT -> setFragmentResult(DIDAINTENT.RESULT_SCREEN_COMMUNITY, bundleOf(DIDAINTENT.RESULT_KEY_CARD_BLOCK to true))
                            else -> {}
                        }
                        navController.popBackStack()
                    }
                }
            }

        }
    }

    override fun initAfterBinding() {}

    override fun onResume() {
        super.onResume()
        viewModel.getPost(args.postId)
    }

    private fun initToolbar(){
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun initAdapter() {
        val adapterConfig = ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(true)
            .setStableIdMode(ConcatAdapter.Config.StableIdMode.SHARED_STABLE_IDS)
            .build()

        adapter = ConcatAdapter(
            adapterConfig,
            detailCommunityHeaderAdapter,
            commentEmptyAdapter,
            commentsAdapter,
        )

        binding.detailCommunityRecyclerview.adapter = adapter
        binding.detailCommunityRecyclerview.addOnPagingListener(
            arrivedBottom = { viewModel.nextPage(args.postId) }
        )
    }

    private fun showDeleteDialog(postId: Long) {
        DefaultDialogFragment.Builder()
            .title(getString(com.dida.common.R.string.delete_post_title))
            .message(getString(com.dida.common.R.string.delete_post_description))
            .positiveButton(getString(com.dida.common.R.string.delete_post_positive), object : DefaultDialogFragment.OnClickListener {
                override fun onClick() {
                    viewModel.onDeletePost(postId)
                }
            })
            .negativeButton(getString(com.dida.common.R.string.delete_post_negative))
            .build()
            .show(childFragmentManager, "delete_post_dialog")
    }

    private fun showDeleteCommentDialog(commentId: Long) {
        DefaultDialogFragment.Builder()
            .title(getString(com.dida.common.R.string.delete_comment_title))
            .message(getString(com.dida.common.R.string.delete_comment_description))
            .positiveButton(getString(com.dida.common.R.string.delete_post_positive), object : DefaultDialogFragment.OnClickListener {
                override fun onClick() {
                    viewModel.deleteComment(commentId = commentId)
                }
            })
            .negativeButton(getString(com.dida.common.R.string.delete_post_negative))
            .build()
            .show(childFragmentManager, "delete_comment_dialog")
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
            .show(childFragmentManager, "block_post_dialog")
    }

    private fun showBlockUserDialog(userId: Long) {
        DefaultDialogFragment.Builder()
            .title(getString(com.dida.community_detail.R.string.block_user_title))
            .message(getString(com.dida.community_detail.R.string.block_user_description))
            .positiveButton(getString(com.dida.community_detail.R.string.block_post_positive), object : DefaultDialogFragment.OnClickListener {
                override fun onClick() {
                    viewModel.onBlock(type = Block.MEMBER, blockId = userId)
                }
            })
            .negativeButton(getString(com.dida.community_detail.R.string.block_post_negative))
            .build()
            .show(childFragmentManager, "block_user_dialog")
    }

    private fun keyboardHide() {
        (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(binding.editComments.windowToken, 0)
    }

    private fun showMessageSnackBar(message: String) {
        DefaultSnackBar.Builder()
            .view(binding.root)
            .message(message)
            .build()
        viewModel.getPost(args.postId)
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

    private fun showPostReportDialog(postId: Long) {
        ReportBottomSheet { confirm, content ->
            if (confirm) viewModel.onReport(
                type = Report.POST,
                reportId = postId,
                content = content
            )
        }.show(childFragmentManager, "Report Dialog")
    }
}
