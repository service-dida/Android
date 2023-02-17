package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.dida.common.adapter.RecentNftAdapter
import com.dida.user_profile.UserProfileNavigationAction
import com.dida.user_profile.UserProfileViewModel
import com.dida.user_profile.databinding.FragmentUserProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class UserProfileFragment :
    BaseFragment<FragmentUserProfileBinding, UserProfileViewModel>(com.dida.user_profile.R.layout.fragment_user_profile) {

    private val TAG = "UserProfileFragment"

    override val layoutResourceId: Int
        get() = com.dida.user_profile.R.layout.fragment_user_profile

    override val viewModel: UserProfileViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }
    private val args: UserProfileFragmentArgs by navArgs()

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
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is UserProfileNavigationAction.NavigateToDetailNft -> navigate(UserProfileFragmentDirections.actionUserProfileFragmentToDetailNftFragment(it.cardId))
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserProfile(userId = args.userId)
    }

    private fun initAdapter() {
        binding.rvUserNft.apply {
            adapter = RecentNftAdapter(viewModel)
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.android.R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
