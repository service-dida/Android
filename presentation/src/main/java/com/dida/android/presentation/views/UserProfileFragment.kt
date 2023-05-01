package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.dida.android.R
import com.dida.common.adapter.UserCardAdapter
import com.dida.common.util.repeatOnCreated
import com.dida.common.util.repeatOnResumed
import com.dida.common.widget.DefaultSnackBar
import com.dida.data.DataApplication.Companion.dataStorePreferences
import com.dida.user_profile.UserMessageAction
import com.dida.user_profile.UserProfileNavigationAction
import com.dida.user_profile.UserProfileViewModel
import com.dida.user_profile.databinding.FragmentUserProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UserProfileFragment :
    BaseFragment<FragmentUserProfileBinding, UserProfileViewModel>(com.dida.user_profile.R.layout.fragment_user_profile) {

    private val TAG = "UserProfileFragment"

    override val layoutResourceId: Int
        get() = com.dida.user_profile.R.layout.fragment_user_profile

    @Inject
    lateinit var assistedFactory: UserProfileViewModel.AssistedFactory
    override val viewModel: UserProfileViewModel by viewModels {
        UserProfileViewModel.provideFactory(
            assistedFactory,
            userId = args.userId
        )
    }

    private val navController: NavController by lazy { findNavController() }
    private val args: UserProfileFragmentArgs by navArgs()
    private val userCardAdapter by lazy { UserCardAdapter(viewModel) }

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
                    when (it) {
                        is UserProfileNavigationAction.NavigateToCardLikeButtonClicked -> userCardAdapter.refresh()
                        is UserProfileNavigationAction.NavigateToDetailNft -> navigate(UserProfileFragmentDirections.actionUserProfileFragmentToDetailNftFragment(it.cardId))
                    }
                }
            }

            launch {
                viewModel.messageEvent.collectLatest {
                    when(it) {
                        is UserMessageAction.UserFollowMessage -> showMessageSnackBar(String.format(getString(R.string.user_follow_message), it.nickname))
                        is UserMessageAction.UserUnFollowMessage -> showMessageSnackBar(getString(R.string.user_unfollow_message))
                        is UserMessageAction.AddCardBookmarkMessage -> {
                            showActionSnackBar(
                                message = getString(R.string.add_bookmark_message),
                                label = getString(R.string.add_bookmark_action_title_message),
                                onClickListener = object : DefaultSnackBar.OnClickListener {
                                    override fun onClick() {}
                                }
                            )
                        }
                        is UserMessageAction.DeleteCardBookmarkMessage -> showMessageSnackBar(getString(R.string.delete_bookmark_message))
                    }

                }
            }
        }

        viewLifecycleOwner.repeatOnCreated {
            if(args.userId == dataStorePreferences.getUserId()) {
                navigate(UserProfileFragmentDirections.actionUserProfileFragmentToMyPageFragment())
            }
        }

        viewLifecycleOwner.repeatOnResumed {
            viewModel.userCardState.collectLatest {
                userCardAdapter.submitData(it)
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
            adapter = userCardAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun showMessageSnackBar(message: String) {
        DefaultSnackBar.Builder()
            .view(binding.root)
            .message(message)
            .hasBottomMargin(false)
            .build()
    }

    private fun showActionSnackBar(message: String, label: String, onClickListener: DefaultSnackBar.OnClickListener) {
        DefaultSnackBar.Builder()
            .view(binding.root)
            .message(message)
            .actionButton(label, onClickListener)
            .hasBottomMargin(false)
            .build()
    }
}
