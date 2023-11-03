package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.dida.common.adapter.UserCardContainerAdapter
import com.dida.common.adapter.UserCardContainerItem
import com.dida.common.util.addOnPagingListener
import com.dida.common.util.repeatOnCreated
import com.dida.common.util.successOrNull
import com.dida.domain.main.model.Follow
import com.dida.mypage.MyPageEmptyAdapter
import com.dida.mypage.MyPageEmptyItem
import com.dida.mypage.MyPageHeaderAdapter
import com.dida.mypage.MyPageSortAdapter
import com.dida.mypage.MyPageViewModel
import com.dida.mypage.MypageNavigationAction
import com.dida.mypage.R
import com.dida.mypage.databinding.FragmentMypageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMypageBinding, MyPageViewModel>(R.layout.fragment_mypage) {

    private val TAG = "MyPageFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_mypage

    override val viewModel: MyPageViewModel by viewModels()

    private lateinit var adapter: ConcatAdapter
    private val myPageHeaderAdapter by lazy { MyPageHeaderAdapter(viewModel) }
    private val myPageSortAdapter by lazy { MyPageSortAdapter(viewModel) }
    private val myPageEmptyAdapter by lazy { MyPageEmptyAdapter(viewModel) }
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
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is MypageNavigationAction.NavigateToEmail -> navigate(MyPageFragmentDirections.actionMyPageFragmentToEmailFragment(RequestEmailType.MAKE_WALLET))
                    is MypageNavigationAction.NavigateToWallet -> navigate(MyPageFragmentDirections.actionMyPageFragmentToWalletFragment())
                    is MypageNavigationAction.NavigateToDetailNft -> navigate(MyPageFragmentDirections.actionMyPageFragmentToDetailNftFragment(it.cardId))
                    is MypageNavigationAction.NavigateToSettings -> navigate(MyPageFragmentDirections.actionMyPageFragmentToSettingFragment())

                    is MypageNavigationAction.NavigateToCreate -> navigate(MyPageFragmentDirections.actionMyPageFragmentToAddFragment())
                    is MypageNavigationAction.NavigateToUserFollowedClicked -> navigate(
                        MyPageFragmentDirections.actionMyPageFragmentToUserFollowedFragment(
                            it.userId,
                            Follow.FOLLOWER
                        )
                    )

                    is MypageNavigationAction.NavigateToUserFollowingClicked -> navigate(
                        MyPageFragmentDirections.actionMyPageFragmentToUserFollowedFragment(
                            it.userId,
                            Follow.FOLLOWING
                        )
                    )

//                    is MypageNavigationAction.NavigateToLikeButtonClicked -> userCardAdapter.changeNftLike(it.nftId)
                    is MypageNavigationAction.NavigateToLikeButtonClicked -> {}
                }
            }
        }

        viewLifecycleOwner.repeatOnCreated {
            launch {
                viewModel.userCardState.collectLatest {
                    if (it.content.isEmpty()) {
                        myPageEmptyAdapter.submitList(listOf(MyPageEmptyItem))
                    } else {
                        myPageEmptyAdapter.submitList(emptyList())
                        userCardAdapter.submitList(listOf(UserCardContainerItem(it.content)))
                    }
                }
            }

            launch {
                viewModel.myPageState.collectLatest {
                    it.successOrNull()?.let { profile ->
                        myPageHeaderAdapter.submitList(listOf(profile))
                    }
                }
            }

            launch {
                viewModel.cardSortTypeState.collectLatest {
                    myPageSortAdapter.submitList(listOf(it))
                }
            }
        }
    }

    override fun initAfterBinding() {}

    private fun initSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getUserInfo()
            viewModel.getUserNfts()
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
            myPageHeaderAdapter,
            myPageSortAdapter,
            myPageEmptyAdapter,
            userCardAdapter
        )

        binding.myPageRecyclerview.adapter = adapter
        binding.myPageRecyclerview.addOnPagingListener(
            arrivedBottom = { viewModel.onNextPage() }
        )
    }


    private fun initToolbar() {
        binding.toolbar.apply {
            this.inflateMenu(R.menu.menu_mypage_toolbar)
            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_wallet -> viewModel.onWalletClicked()
                    R.id.action_setting -> viewModel.onSettingsClicked()
                }
                true
            }
        }
    }
}
