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
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding, MyPageViewModel>(R.layout.fragment_mypage) {

    private val TAG = "MyPageFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_mypage

    override val viewModel: MyPageViewModel by viewModels()
    val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initMyPage()
        viewModel.initMyPageState()
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collect {
                when(it) {
                    is MypageNavigationAction.NavigateToHome -> {
                        toastMessage("로그아웃 하였습니다.")
                        navController.popBackStack()
                    }
                    is MypageNavigationAction.NavigateToEmail -> navigate(MyPageFragmentDirections.actionMyPageFragmentToEmailFragment())
                    is MypageNavigationAction.NavigateToWallet -> navigate(MyPageFragmentDirections.actionMyPageFragmentToEmailFragment())
                    is MypageNavigationAction.NavigateToDetailNft -> navigate(MyPageFragmentDirections.actionMyPageFragmentToDetailNftFragment())
                }
            }
        }
    }
    
    override fun initAfterBinding() {
    }

    private fun initMyPage() {
        initToolbar()
        initSpinner()
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
                    R.id.action_setting -> viewModel.onSettingClicked()
                }
                true
            }
        }
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.mypage_spinner_list,
            R.layout.holder_mypage_nft_type_spinner
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }
    }
}