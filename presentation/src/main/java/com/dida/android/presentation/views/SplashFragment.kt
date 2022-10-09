package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dida.android.R
import com.dida.android.databinding.FragmentSplashBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.base.successOrNull
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(R.layout.fragment_splash) {

    override val layoutResourceId: Int
        get() = R.layout.fragment_splash // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : SplashViewModel by viewModels()

    override fun initStartView() {
        viewModel.checkVersion()
        showLoadingDialog()
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenResumed {
            viewModel.appVersion.collect {
                dismissLoadingDialog()
                if(it.version.toString() == getString(R.string.app_version)) {
                    delay(1000L)
                    navigate(SplashFragmentDirections.actionMainFragment())
                }
            }
        }
    }

    override fun initAfterBinding() {
    }
}