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
import com.dida.common.actionhandler.ReportActionHandler
import com.dida.common.actionhandler.UpdateActionHandler
import com.dida.common.adapter.CommentsAdapter
import com.dida.common.ballon.ReportBalloon
import com.dida.common.ballon.UpdateBalloon
import com.dida.common.dialog.DefaultDialogFragment
import com.dida.common.util.repeatOnStarted
import com.dida.common.widget.MessageSnackBar
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
                        is DetailCommunityNavigationAction.NavigateToCommentMore -> commentMoreBottomSheet(it.commentId)
                        is DetailCommunityNavigationAction.NavigateToCommunityMore -> showReportBalloon(userId = it.userId, viewModel, binding.moreButton)
                        is DetailCommunityNavigationAction.NavigateToMyMore -> showUpdateBalloon(viewModel, binding.moreButton)
                        is DetailCommunityNavigationAction.NavigateToBack -> navController.popBackStack()
                        is DetailCommunityNavigationAction.NavigateToUpdateCommunity -> navigate(DetailCommunityFragmentDirections.actionCommunityDetailFragmentToCommunityCommunityInputFragment(cardId = 0, createState = false, postId = it.postId))
                        is DetailCommunityNavigationAction.NavigateToUserProfile -> navigate(DetailCommunityFragmentDirections.actionCommunityDetailFragmentToUserProfileFragment(it.userId))
                        is DetailCommunityNavigationAction.NavigateToCardDetail -> navigate(DetailCommunityFragmentDirections.actionCommunityDetailFragmentToDetailNftFragment(it.cardId))
                        is DetailCommunityNavigationAction.NavigateToDelete -> deletePostAlert()
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

    private fun deletePostAlert() {
        DefaultDialogFragment.Builder()
            .title(getString(com.dida.common.R.string.delete_post_title))
            .message(getString(com.dida.common.R.string.delete_post_description))
            .positiveButton(getString(com.dida.common.R.string.delete_post_positive), object : DefaultDialogFragment.OnClickListener {
                override fun onClick() {
                    viewModel.onDeletePost()
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
        MessageSnackBar.make(binding.root, message).show()
    }

    private fun showReportBalloon(
        userId: Long,
        eventListener: ReportActionHandler,
        view: View
    ) {
        val balloon = ReportBalloon(userId = userId, eventListener = eventListener)
            .create(context = view.context, lifecycle = view.findViewTreeLifecycleOwner())
        view.showAlignBottom(balloon)
    }

    private fun showUpdateBalloon(
        eventListener: UpdateActionHandler,
        view: View
    ) {
        val balloon = UpdateBalloon(eventListener = eventListener)
            .create(context = view.context, lifecycle = view.findViewTreeLifecycleOwner())
        view.showAlignBottom(balloon)
    }

    private fun commentMoreBottomSheet(commentId: Long) {
        val morDialog = DetailCommunityBottomSheetDialog {
            when (it) {
                is MoreState.Update -> {}
                is MoreState.Delete -> deleteCommentAlert(commentId = commentId)
            }
        }
        morDialog.show(requireActivity().supportFragmentManager, morDialog.tag)
    }
}
