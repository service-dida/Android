package com.dida.android.presentation.views

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.common.dialog.CentralDialogFragment
import com.dida.common.dialog.CompleteDialogFragment
import com.dida.common.util.repeatOnStarted
import com.dida.create_community_input.CreateCommunityInputNavigationAction
import com.dida.create_community_input.CreateCommunityInputViewModel
import com.dida.create_community_input.databinding.FragmentCreateCommunityInputBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateCommunityInputFragment : BaseFragment<FragmentCreateCommunityInputBinding, CreateCommunityInputViewModel>(com.dida.create_community_input.R.layout.fragment_create_community_input) {

    private val TAG = "CreateCommunityFragment"

    override val layoutResourceId: Int
        get() = com.dida.create_community_input.R.layout.fragment_create_community_input

    override val viewModel : CreateCommunityInputViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val args: CreateCommunityInputFragmentArgs by navArgs()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        viewModel.createState.value = args.createState
        viewModel.isNewCreate(isCreate = args.createState)

    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is CreateCommunityInputNavigationAction.NavigateToBack -> navController.popBackStack()
                    is CreateCommunityInputNavigationAction.NavigateToCommunity -> {
                        showCreateCompleteDialog()
                        navigate(CreateCommunityInputFragmentDirections.actionCommunityCommunityInputFragmentToCommunityFragment())
                    }
                }
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.isNewCreate.collectLatest {
                if(it) viewModel.getCardDetail(cardId = args.cardId)
                else viewModel.getPostDetail(postId = args.postId)
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun showCreateCompleteDialog() {
        CompleteDialogFragment.Builder()
            .message(getString(R.string.create_post_dialog_message))
            .build()
            .show(childFragmentManager, "complete_dialog")
    }
}
