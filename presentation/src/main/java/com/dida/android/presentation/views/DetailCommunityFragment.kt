package com.dida.android.presentation.views

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.common.adapter.CommentsAdapter
import com.dida.common.ballon.DefaultBalloon
import com.dida.common.dialog.DefaultDialogFragment
import com.dida.common.util.repeatOnStarted
import com.dida.common.widget.DefaultSnackBar
import com.dida.community_detail.*
import com.dida.community_detail.databinding.FragmentDetailCommunityBinding
import com.skydoves.balloon.showAlignBottom
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailCommunityFragment : BaseFragment<FragmentDetailCommunityBinding, DetailCommunityViewModel>(com.dida.community_detail.R.layout.fragment_detail_community) {

    private val TAG = "DetailCommunityFragment"

    override val layoutResourceId: Int
        get() = com.dida.community_detail.R.layout.fragment_detail_community

    override val viewModel : DetailCommunityViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val args: DetailCommunityFragmentArgs by navArgs()
    private val commentsAdapter by lazy { CommentsAdapter(viewModel) }

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
            launch {
                viewModel.navigationEvent.collectLatest {
                    when(it) {
                        is DetailCommunityNavigationAction.NavigateToNotWriterMore -> showReportBalloon(userId = it.userId, view = binding.moreButton)
                        is DetailCommunityNavigationAction.NavigateToWriterMore -> showUpdateBalloon(postId = it.postId, view = binding.moreButton)
                        is DetailCommunityNavigationAction.NavigateToBack -> navController.popBackStack()
                        is DetailCommunityNavigationAction.NavigateToUserProfile -> navigate(DetailCommunityFragmentDirections.actionCommunityDetailFragmentToUserProfileFragment(it.userId))
                        is DetailCommunityNavigationAction.NavigateToCardDetail -> navigate(DetailCommunityFragmentDirections.actionCommunityDetailFragmentToDetailNftFragment(it.cardId))
                        is DetailCommunityNavigationAction.NavigateToReport -> {}
                        is DetailCommunityNavigationAction.NavigateToBlock -> {}
                        is DetailCommunityNavigationAction.NavigateToUpdate -> {}
                        is DetailCommunityNavigationAction.NavigateToDelete -> deleteCommentAlert(commentId = it.commentId)
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
            viewModel.commentList.collectLatest {
                commentsAdapter.submitList(it)
                if (viewModel.isWrite.value) keyboardHide()
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
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun initAdapter() {
        binding.detailCommunityMain.adapter = commentsAdapter
    }

    private fun deletePostAlert(postId: Long) {
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

    private fun deleteCommentAlert(commentId: Long) {
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

    private fun keyboardHide() {
        (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(binding.editComments.windowToken, 0)
        scrollToDown()
    }

    private fun scrollToDown() {
        Handler(Looper.getMainLooper()).post {
            binding.detailCommunityScroll.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }

    private fun showMessageSnackBar(message: String) {
        DefaultSnackBar.Builder()
            .view(binding.root)
            .message(message)
            .build()
    }

    private fun showReportBalloon(
        userId: Long,
        view: View
    ) {
        val balloon = DefaultBalloon.Builder()
            .firstButton(
                label = getString(com.dida.common.R.string.report_message_balloon),
                icon = com.dida.common.R.drawable.ic_report,
                listener = object : DefaultBalloon.OnClickListener {
                    override fun onClick() {}
                })
            .secondButton(
                label = getString(com.dida.common.R.string.block_message_balloon),
                icon = com.dida.common.R.drawable.ic_block,
                listener = object : DefaultBalloon.OnClickListener {
                    override fun onClick() {}
                })
            .build()
            .create(context = view.context, lifecycle = view.findViewTreeLifecycleOwner())

        view.showAlignBottom(balloon)
    }

    private fun showUpdateBalloon(
        postId: Long,
        view: View
    ) {
        val balloon = DefaultBalloon.Builder()
            .firstButton(
                label = getString(com.dida.common.R.string.update_message_balloon),
                icon = com.dida.common.R.drawable.ic_profile_edit,
                listener = object : DefaultBalloon.OnClickListener {
                    override fun onClick() {
                        navigate(DetailCommunityFragmentDirections.actionCommunityDetailFragmentToCommunityCommunityInputFragment(cardId = 0, createState = false, postId = postId))
                    }
                })
            .secondButton(
                label = getString(com.dida.common.R.string.delete_message_balloon),
                icon = com.dida.common.R.drawable.ic_delete,
                listener = object : DefaultBalloon.OnClickListener {
                    override fun onClick() {
                        deletePostAlert(postId)
                    }
                })
            .build()
            .create(context = view.context, lifecycle = view.findViewTreeLifecycleOwner())
        view.showAlignBottom(balloon)
    }
}
