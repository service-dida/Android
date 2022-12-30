package com.dida.android.presentation.views.nav.mypage

import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dida.android.R
import com.dida.android.databinding.FragmentMypageBinding
import com.dida.android.presentation.adapter.home.RecentNftAdapter
import com.dida.android.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding, MyPageViewModel>(R.layout.fragment_mypage) {

    private val TAG = "MyPageFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_mypage

    override val viewModel: MyPageViewModel by viewModels()
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
                when(it) {
                    is MypageNavigationAction.NavigateToHome -> {
                        toastMessage("로그아웃 하였습니다.")
                        navController.popBackStack()
                    }
                    is MypageNavigationAction.NavigateToEmail -> navigate(MyPageFragmentDirections.actionMyPageFragmentToEmailFragment())
                    is MypageNavigationAction.NavigateToWallet -> navigate(MyPageFragmentDirections.actionMyPageFragmentToWalletFragment())
                    is MypageNavigationAction.NavigateToUpdateProfile -> navigate(MyPageFragmentDirections.actionMyPageFragmentToUpdateProfileFragment(it.image,it.nickname,it.description))
                    is MypageNavigationAction.NavigateToDetailNft -> navigate(MyPageFragmentDirections.actionMyPageFragmentToDetailNftFragment(it.cardId))
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
                    R.id.action_setting -> viewModel.onLogoutClicked()
                    R.id.action_profileImg -> viewModel.onUpdateProfileClicked()
                    R.id.action_temporary_password -> viewModel.tempPassword()
                    R.id.action_change_password -> viewModel.changePassword("203057","000000")
                }
                true
            }
        }
    }
}