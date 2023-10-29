package com.dida.android.presentation.views

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.dida.common.adapter.UserCardAdapter
import com.dida.common.util.addOnPagingListener
import com.dida.common.util.repeatOnCreated
import com.dida.domain.main.model.Follow
import com.dida.mypage.MyPageViewModel
import com.dida.mypage.MypageNavigationAction
import com.dida.mypage.R
import com.dida.mypage.databinding.FragmentMypageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding, MyPageViewModel>(R.layout.fragment_mypage) {

    private val TAG = "MyPageFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_mypage

    override val viewModel: MyPageViewModel by viewModels()
    private val userCardAdapter: UserCardAdapter by lazy { UserCardAdapter(viewModel) }

    private var lastScrollY = 0

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
                    is MypageNavigationAction.NavigateToUserFollowedClicked -> navigate(MyPageFragmentDirections.actionMyPageFragmentToUserFollowedFragment(it.userId, Follow.FOLLOWER))
                    is MypageNavigationAction.NavigateToUserFollowingClicked -> navigate(MyPageFragmentDirections.actionMyPageFragmentToUserFollowedFragment(it.userId, Follow.FOLLOWING))
                    is MypageNavigationAction.NavigateToLikeButtonClicked -> userCardAdapter.changeNftLike(it.nftId)
                }
            }
        }

        viewLifecycleOwner.repeatOnCreated {
            viewModel.userCardState.collectLatest {
                binding.emptyView.visibility = if (it.content.isEmpty()) View.VISIBLE else View.GONE
                userCardAdapter.submitList(it.content)
            }
        }
    }

    override fun initAfterBinding() {
    }

    override fun onResume() {
        super.onResume()
        getLastScrollY()
    }

    override fun onPause() {
        super.onPause()
        setLastScrollY()
    }

    private fun initSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getUserInfo()
            viewModel.getUserNfts()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initAdapter() {
        binding.rvUserNft.apply {
            adapter = userCardAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        binding.rvUserNft.addOnPagingListener(
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

    private fun getLastScrollY() {
        if (lastScrollY > 0) {
            binding.mypageScroll.scrollTo(0, lastScrollY)
            lastScrollY = 0
        }
    }

    private fun setLastScrollY() {
        lastScrollY = binding.mypageScroll.scrollY
    }
}
