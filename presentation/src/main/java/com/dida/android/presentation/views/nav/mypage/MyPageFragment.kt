package com.dida.android.presentation.views.nav.mypage

import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.dida.android.R
import com.dida.android.databinding.FragmentMypageBinding
import com.dida.android.presentation.adapter.mypage.UserNftAdapter
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.util.ConvertDpToPx
import com.dida.android.util.GridSpacing
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding, MyPageViewModel>(R.layout.fragment_mypage) {

    private val TAG = "MyPageFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_mypage

    override val viewModel: MyPageViewModel by viewModels()
    val navController: NavController by lazy { Navigation.findNavController(requireView()) }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initMyPage()
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collect {
                when(it) {
                    is MypageNavigationAction.NavigateToHome -> {
                        Toast.makeText(requireContext(), "로그아웃 하였습니다.", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                    is MypageNavigationAction.NavigateToEmail -> checkNavigationDesination(R.id.action_myPageFragment_to_emailFragment)
                    is MypageNavigationAction.NavigateToWallet -> checkNavigationDesination(R.id.action_myPageFragment_to_emailFragment)
                    is MypageNavigationAction.NavigateToDetailNft -> checkNavigationDesination(R.id.action_myPageFragment_to_detailNftFragment)
                }
            }
        }
    }
    
    override fun initAfterBinding() {
    }

    private fun initMyPage() {
        viewModel.initMyPageState()
        initToolbar()
        initAdapter()
        initSpinner()
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

    private fun initAdapter() {
        binding.rvUserNft.apply {
            adapter = UserNftAdapter(viewModel)
            val px = ConvertDpToPx().convertDPtoPX(requireContext(),14)
            addItemDecoration(GridSpacing(px, px))
        }
    }

    private fun checkNavigationDesination(toNav: Any) {
        if (navController.currentDestination?.id == R.id.myPageFragment) {
            when(toNav) {
                is NavDirections -> navController.navigate(toNav)
                is Int -> navController.navigate(toNav)
            }
        }
    }
}