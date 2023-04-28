package com.dida.android.presentation.views

import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.common.util.EVENT
import com.dida.common.util.SCREEN
import com.dida.common.util.repeatOnStarted
import com.dida.create_community_input.CreateCommunityInputNavigationAction
import com.dida.create_community_input.CreateCommunityInputViewModel
import com.dida.create_community_input.databinding.FragmentCreateCommunityInputBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CreateCommunityInputFragment : BaseFragment<FragmentCreateCommunityInputBinding, CreateCommunityInputViewModel>(com.dida.create_community_input.R.layout.fragment_create_community_input) {

    private val TAG = "CreateCommunityFragment"

    override val layoutResourceId: Int
        get() = com.dida.create_community_input.R.layout.fragment_create_community_input

    @Inject
    lateinit var assistedFactory: CreateCommunityInputViewModel.AssistedFactory
    override val viewModel: CreateCommunityInputViewModel by viewModels {
        CreateCommunityInputViewModel.provideFactory(
            assistedFactory,
            createdState = args.createState
        )
    }

    private val navController by lazy { findNavController() }
    private val args: CreateCommunityInputFragmentArgs by navArgs()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initView()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is CreateCommunityInputNavigationAction.NavigateToBack -> navController.popBackStack()
                    is CreateCommunityInputNavigationAction.NavigateToCommunity -> {
                        setFragmentResult(SCREEN.COMMUNITY, bundleOf(EVENT.CREATE to true))
                        navigate(CreateCommunityInputFragmentDirections.actionCommunityCommunityInputFragmentToCommunityFragment())
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initView() {
        if(viewModel.createdState) viewModel.getCardDetail(cardId = args.cardId)
        else viewModel.getPostDetail(postId = args.postId)
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
