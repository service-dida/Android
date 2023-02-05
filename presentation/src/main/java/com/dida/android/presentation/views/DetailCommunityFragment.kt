package com.dida.android.presentation.views

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.common.adapter.CommentsAdapter
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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.navigationEvent.collectLatest {
                    when(it) {
                        is DetailCommunityNavigationAction.NavigateToCommentMore -> commentMoreBottomSheet(it.commentId)
                        is DetailCommunityNavigationAction.NavigateToCommunityMore -> communityMoreBottomSheet()
                        is DetailCommunityNavigationAction.NavigateToBack -> navController.popBackStack()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            launch {
                viewModel.commentList.collectLatest {
                    commentsAdapter.submitList(it)
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
            this.title = resources.getString(R.string.detail_community_title)
            this.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
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
                is MoreState.Update -> {}
                is MoreState.Delete -> viewModel.deleteCommunity()
            }
        }
        morDialog.show(requireActivity().supportFragmentManager, morDialog.tag)
    }

    private fun commentMoreBottomSheet(commentId: Long) {
        val morDialog = DetailCommunityBottomSheetDialog {
            when (it) {
                is MoreState.Update -> {}
                is MoreState.Delete -> viewModel.deleteComment(commentId = commentId)
            }
        }
        morDialog.show(requireActivity().supportFragmentManager, morDialog.tag)
    }
}
