package com.dida.android.presentation.views.nav.mypage

import android.content.Intent
import android.util.Log
import androidx.fragment.app.viewModels
import com.dida.android.GlobalApplication
import com.dida.android.R
import com.dida.android.databinding.FragmentNicknameBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.viewmodel.nav.mypage.MyPageViewModel
import com.dida.android.presentation.views.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentNicknameBinding, MyPageViewModel>(R.layout.fragment_mypage) {

    private val TAG = "MyPageFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_mypage

    override val viewModel : MyPageViewModel by viewModels()


    override fun initStartView() {
        //loginCheck()
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
}