package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dida.mypage.R
import com.dida.common.adapter.RecentNftAdapter
import com.dida.mypage.MypageNavigationAction
import com.dida.mypage.databinding.FragmentMypageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMypageBinding, com.dida.mypage.MyPageViewModel>(R.layout.fragment_mypage) {

    private val TAG = "MyPageFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_mypage

    override val viewModel: com.dida.mypage.MyPageViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initMyPage()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is MypageNavigationAction.NavigateToEmail -> navigate(
                        MyPageFragmentDirections.actionMyPageFragmentToEmailFragment()
                    )
                    is MypageNavigationAction.NavigateToWallet -> navigate(
                        MyPageFragmentDirections.actionMyPageFragmentToWalletFragment()
                    )
                    is MypageNavigationAction.NavigateToDetailNft -> navigate(
                        MyPageFragmentDirections.actionMyPageFragmentToDetailNftFragment(it.cardId)
                    )
                    is MypageNavigationAction.NavigateToSettings -> navigate(
                        MyPageFragmentDirections.actionMyPageFragmentToSettingFragment()
                    )
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMypage()
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
}
