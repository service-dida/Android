package com.dida.android.presentation.views

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.create_community_input.databinding.FragmentCreateCommunityInputBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateCommunityInputFragment : BaseFragment<FragmentCreateCommunityInputBinding, com.dida.create_community_input.CreateCommunityInputViewModel>(com.dida.create_community_input.R.layout.fragment_create_community_input) {

    private val TAG = "CreateCommunityFragment"

    override val layoutResourceId: Int
        get() = com.dida.create_community_input.R.layout.fragment_create_community_input

    override val viewModel : com.dida.create_community_input.CreateCommunityInputViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val args: CreateCommunityInputFragmentArgs by navArgs()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        viewModel.setCreateState(args.createState)
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when(it) {
                    is com.dida.create_community_input.CreateCommunityInputNavigationAction.NavigateToBack -> navController.popBackStack()
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.title = if(args.createState) resources.getString(R.string.create_community_title) else resources.getString(R.string.create_community_input_update)
            this.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}