package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.dida.android.R
import com.dida.common.adapter.UserCardAdapter
import com.dida.common.adapter.UserCardContainerAdapter
import com.dida.common.adapter.UserCardContainerItem
import com.dida.common.util.addOnPagingListener
import com.dida.common.util.performHapticEvent
import com.dida.common.util.repeatOnCreated
import com.dida.common.util.successOrNull
import com.dida.common.widget.DefaultSnackBar
import com.dida.mypage.adapter.MyPageEmptyAdapter
import com.dida.mypage.adapter.MyPageEmptyItem
import com.dida.mypage.adapter.MyPageHeaderAdapter
import com.dida.mypage.adapter.MyPageSortAdapter
import com.dida.user_profile.UserMessageAction
import com.dida.user_profile.UserProfileNavigationAction
import com.dida.user_profile.UserProfileViewModel
import com.dida.user_profile.adapter.UserProfileHeaderAdapter
import com.dida.user_profile.adapter.UserProfileSortAdapter
import com.dida.user_profile.databinding.FragmentUserProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UserProfileFragment :
    BaseFragment<FragmentUserProfileBinding, UserProfileViewModel>(com.dida.user_profile.R.layout.fragment_user_profile) {

    private val TAG = "UserProfileFragment"

    override val layoutResourceId: Int = com.dida.user_profile.R.layout.fragment_user_profile

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

    private lateinit var adapter: ConcatAdapter
    private val userProfileHeaderAdapter by lazy { UserProfileHeaderAdapter(viewModel) }
    private val userProfileSortAdapter by lazy { UserProfileSortAdapter(viewModel) }
    private val userCardAdapter: UserCardContainerAdapter by lazy { UserCardContainerAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        initToolbar()
        initAdapter()
        initSwipeRefresh()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.navigationEvent.collectLatest {
                    when (it) {
                        is UserProfileNavigationAction.NavigateToDetailNft -> navigate(UserProfileFragmentDirections.actionUserProfileFragmentToDetailNftFragment(it.cardId))
                    }
                }
            }

            launch {
                viewModel.messageEvent.collectLatest {
                    when(it) {
                        is UserMessageAction.UserFollowMessage -> showMessageSnackBar(String.format(getString(R.string.user_follow_message), it.nickname))
                        is UserMessageAction.UserUnFollowMessage -> showMessageSnackBar(getString(R.string.user_unfollow_message))
                        is UserMessageAction.NftBookmarkMessage -> {
                            if (it.liked) {
                                showMessageSnackBar(getString(R.string.add_bookmark_message))
                            } else {
                                showMessageSnackBar(getString(R.string.delete_bookmark_message))
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.repeatOnCreated {
            launch {
                viewModel.userCardState.collectLatest {
                    userCardAdapter.submitList(listOf(UserCardContainerItem(it.content)))
                }
            }

            launch {
                viewModel.userProfileState.collectLatest {
                    it.successOrNull()?.let { profile ->
                        userProfileHeaderAdapter.submitList(listOf(profile))
                    }
                }
            }

            launch {
                viewModel.cardSortTypeState.collectLatest {
                    userProfileSortAdapter.submitList(listOf(it))
                }
            }
        }
    }

    override fun initAfterBinding() {}

    private fun initSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getUserProfile()
            viewModel.getUserNfts()
            requireView().performHapticEvent()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initAdapter() {

        val adapterConfig = ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(true)
            .setStableIdMode(ConcatAdapter.Config.StableIdMode.SHARED_STABLE_IDS)
            .build()

        adapter = ConcatAdapter(
            adapterConfig,
            userProfileHeaderAdapter,
            userProfileSortAdapter,
            userCardAdapter
        )

        binding.userProfileRecyclerview.adapter = adapter
        binding.userProfileRecyclerview.addOnPagingListener(
            arrivedBottom = { viewModel.onNextPage() }
        )
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
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
