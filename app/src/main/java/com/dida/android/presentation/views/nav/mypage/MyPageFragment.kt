package com.dida.android.presentation.views.nav.mypage

import android.content.Intent
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.dida.android.GlobalApplication
import com.dida.android.R
import com.dida.android.databinding.FragmentMypageBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.viewmodel.nav.mypage.MyPageViewModel
import com.dida.android.presentation.views.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding, MyPageViewModel>(R.layout.fragment_mypage) {

    private val TAG = "MyPageFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_mypage

    override val viewModel : MyPageViewModel by viewModels()


    override fun initStartView() {
        //loginCheck()
        initToolbar()
        initSpinner()
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

    private fun loginCheck(){
        val accessToken = GlobalApplication.mySharedPreferences.getAccessToken()
        if(accessToken.isNullOrEmpty()){
            val loginIntent = Intent(activity, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

    private fun initToolbar(){
        binding.toolbar.inflateMenu(R.menu.menu_mypage_toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_wallet ->{

                }
                R.id.action_example1 ->{

                }
            }
            true
        }
    }

    private fun initSpinner(){
        ArrayAdapter.createFromResource(requireContext(),R.array.mypage_spinner_list, R.layout.item_mypage_nft_type_spinner)
            .also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }
    }
}