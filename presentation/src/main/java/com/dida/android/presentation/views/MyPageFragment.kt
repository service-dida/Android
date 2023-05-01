package com.dida.android.presentation.views

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.dida.common.adapter.RecentNftAdapter
import com.dida.common.util.repeatOnResumed
import com.dida.common.util.successOrNull
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

    private var lastScrollY = 0

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initMyPage()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is MypageNavigationAction.NavigateToEmail -> navigate(MyPageFragmentDirections.actionMyPageFragmentToEmailFragment())
                    is MypageNavigationAction.NavigateToWallet -> navigate(MyPageFragmentDirections.actionMyPageFragmentToWalletFragment())
                    is MypageNavigationAction.NavigateToDetailNft -> navigate(MyPageFragmentDirections.actionMyPageFragmentToDetailNftFragment(it.cardId))
                    is MypageNavigationAction.NavigateToSettings -> navigate(MyPageFragmentDirections.actionMyPageFragmentToSettingFragment())
                    is MypageNavigationAction.NavigateToCreate -> navigate(MyPageFragmentDirections.actionMyPageFragmentToAddFragment())
                }
            }
        }

        viewLifecycleOwner.repeatOnResumed {
            viewModel.hasMyNftState.collectLatest {
                it.successOrNull()?.let { userCards ->
                    binding.emptyView.isVisible = userCards.isEmpty()
                    binding.rvUserNft.isVisible = userCards.isNotEmpty()
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMypage()
        getLastScrollY()
    }

    override fun onPause() {
        super.onPause()
        setLastScrollY()
    }

    private fun initMyPage() {
        initToolbar()
        initAdapter()
    }

    private fun initAdapter() {
        binding.rvUserNft.apply {
            adapter = RecentNftAdapter(viewModel)
            layoutManager = GridLayoutManager(context, 2)
        }
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
