package com.dida.android.presentation.views

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.R
import com.dida.common.adapter.CommentsAdapter
import com.dida.common.base.DefaultAlertDialog
import com.dida.common.util.repeatOnStarted
import com.dida.community_detail.DetailCommunityBottomSheetDialog
import com.dida.community_detail.DetailCommunityNavigationAction
import com.dida.community_detail.DetailCommunityViewModel
import com.dida.community_detail.MoreState
import com.dida.community_detail.databinding.FragmentDetailCommunityBinding
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

    val smoothScroller: RecyclerView.SmoothScroller by lazy { object : LinearSmoothScroller(requireContext()) {
            override fun getVerticalSnapPreference() = SNAP_TO_START
        }
    }

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
            viewModel.navigationEvent.collectLatest {
                when(it) {
                    is DetailCommunityNavigationAction.NavigateToCommentMore -> commentMoreBottomSheet(it.commentId)
                    is DetailCommunityNavigationAction.NavigateToCommunityMore -> communityMoreBottomSheet()
                    is DetailCommunityNavigationAction.NavigateToBack -> navController.popBackStack()
                    is DetailCommunityNavigationAction.NavigateToUpdateCommunity -> navigate(DetailCommunityFragmentDirections.actionCommunityDetailFragmentToCommunityCommunityInputFragment(cardId = 0, createState = false, postId = it.postId))
                    is DetailCommunityNavigationAction.NavigateToUserProfile -> navigate(DetailCommunityFragmentDirections.actionCommunityDetailFragmentToUserProfileFragment(it.userId))
                    is DetailCommunityNavigationAction.NavigateToCardDetail -> navigate(DetailCommunityFragmentDirections.actionCommunityDetailFragmentToDetailNftFragment(it.cardId))
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

    private fun communityMoreBottomSheet() {
        val morDialog = DetailCommunityBottomSheetDialog {
            when (it) {
                is MoreState.Update -> viewModel.updateCommunity()
                is MoreState.Delete -> deletePostAlert()
            }
        }
        morDialog.show(requireActivity().supportFragmentManager, morDialog.tag)
    }

    private fun deletePostAlert() {
        val res = com.dida.common.base.AlertModel(
            title = requireContext().getString(com.dida.common.R.string.delete_post_title),
            description = requireContext().getString(com.dida.common.R.string.delete_post_description),
            noButtonTitle = requireContext().getString(com.dida.common.R.string.cancel),
            yesButtonTitle = requireContext().getString(com.dida.common.R.string.ok)
        )
        val dialog = DefaultAlertDialog(
            alertModel = res,
            clickNegative = {},
            clickPositive = { viewModel.deleteCommunity() }
        )
        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
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

    private fun deleteCommentAlert(commentId: Long) {
        val res = com.dida.common.base.AlertModel(
            title = requireContext().getString(com.dida.common.R.string.delete_comment_title),
            description = requireContext().getString(com.dida.common.R.string.delete_comment_description),
            noButtonTitle = requireContext().getString(com.dida.common.R.string.cancel),
            yesButtonTitle = requireContext().getString(com.dida.common.R.string.ok)
        )
        val dialog = DefaultAlertDialog(
            alertModel = res,
            clickNegative = {},
            clickPositive = { viewModel.deleteComment(commentId = commentId) }
        )
        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
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
}
