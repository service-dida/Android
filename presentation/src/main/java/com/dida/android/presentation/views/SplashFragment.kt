package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dida.android.R
import com.dida.android.databinding.FragmentSplashBinding
import com.dida.android.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(R.layout.fragment_splash) {

    override val layoutResourceId: Int
        get() = R.layout.fragment_splash // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : SplashViewModel by viewModels()
    lateinit var navController: NavController

    override fun initStartView() {
        navController = Navigation.findNavController(requireView())
        viewModel.checkVersion()
        showLoadingDialog()
    }

    override fun initDataBinding() {
        viewModel.appVersion.observe(this) {
            dismissLoadingDialog()
            if(it.version == 0) {

            }
        }
    }

    override fun initAfterBinding() {
    }
}