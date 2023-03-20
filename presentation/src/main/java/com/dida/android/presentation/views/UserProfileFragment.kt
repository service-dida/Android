package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.dida.common.adapter.RecentNftAdapter
import com.dida.common.util.repeatOnCreated
import com.dida.data.DataApplication.Companion.dataStorePreferences
import com.dida.user_profile.UserProfileNavigationAction
import com.dida.user_profile.UserProfileViewModel
import com.dida.user_profile.databinding.FragmentUserProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        viewModel.setUserId(args.userId)
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is UserProfileNavigationAction.NavigateToDetailNft -> navigate(UserProfileFragmentDirections.actionUserProfileFragmentToDetailNftFragment(it.cardId))
                }
            }
        }

        lifecycleScope.launch {
            repeatOnCreated {
                if(args.userId == dataStorePreferences.getUserId()) {
                    navigate(UserProfileFragmentDirections.actionUserProfileFragmentToMyPageFragment())
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserProfile()
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
