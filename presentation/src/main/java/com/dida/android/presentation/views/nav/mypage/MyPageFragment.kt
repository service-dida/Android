package com.dida.android.presentation.views.nav.mypage

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.dida.android.R
import com.dida.android.databinding.FragmentMypageBinding
import com.dida.android.presentation.adapter.mypage.MyPageUserCardsRecyclerViewAdapter
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.util.AppLog
import com.dida.android.util.ConvertDpToPx
import com.dida.android.util.GridSpacing
import com.dida.data.DataApplication.Companion.mySharedPreferences
import com.dida.domain.model.nav.mypage.UserCardsResponseModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMypageBinding, MyPageViewModel>(R.layout.fragment_mypage) {

    private val TAG = "MyPageFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_mypage

    override val viewModel: MyPageViewModel by viewModels()
    val navController: NavController by lazy {
        Navigation.findNavController(requireView())
    }

    override fun initStartView() {
        binding.vm = viewModel
        initToolbar()
        initSpinner()
        initUserInfo()
        showLoadingDialog()
    }

    override fun initDataBinding() {
        viewModel.userCardsLiveData.observe(viewLifecycleOwner) {
            initRecyclerView(it)
            dismissLoadingDialog()
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT)
        }
    }
    
    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        binding.toolbar.inflateMenu(R.menu.menu_mypage_toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_wallet -> {
                    if(viewModel.getWalletValue){
                        val directions = MyPageFragmentDirections.actionMyPageFragmentToWalletFragment()
                        findNavController().navigate(directions)
                    }else{
                        // 지갑 만드는 곳으로 이동.
                        Toast.makeText(requireContext(), "지갑생성이 필요합니다.", Toast.LENGTH_SHORT).show()
                        navController.navigate(R.id.action_myPageFragment_to_emailFragment)
                    }
                }
                R.id.action_setting -> {
                    mySharedPreferences.removeAccessToken()
                    Toast.makeText(requireContext(), "로그아웃하였습니다.", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
            true
        }
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.mypage_spinner_list,
            R.layout.holder_mypage_nft_type_spinner
        )
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinner.adapter = adapter
            }
    }

    private fun initUserInfo(){
        viewModel.getUserProfile()
        viewModel.getUserCards()
    }

    private fun initRecyclerView(list: List<UserCardsResponseModel>) {
        binding.recyclerView.apply {
            //TODO : 테스트 끝나면 exampleList -> list 로 변경
            adapter = MyPageUserCardsRecyclerViewAdapter(list, ::showDetailPage)
            layoutManager = GridLayoutManager(requireContext(), 2)
            val px = ConvertDpToPx().convertDPtoPX(requireContext(),14)
            addItemDecoration(GridSpacing(px, px))
        }
    }

    private fun showDetailPage(nftId: Int) {
        findNavController().navigate(R.id.action_myPageFragment_to_detailNftFragment)
    }

}