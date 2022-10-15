package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dida.android.R
import com.dida.android.databinding.FragmentSplashBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.util.AppLog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(R.layout.fragment_splash) {

    override val layoutResourceId: Int
        get() = R.layout.fragment_splash // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : SplashViewModel by viewModels()

    override fun initStartView() {
        viewModel.checkVersion()
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.appVersion.collect {
                    if(it.version.toString() == getString(R.string.app_version)) { getToken() }
                }
            }

            launch {
                viewModel.navigateToHome.collect {
                    if(it) {
                        delay(300L)
                        navigate(SplashFragmentDirections.actionMainFragment())
                    }
                }
            }

        }
    }

    override fun initAfterBinding() {
    }

    private fun getToken() {
        //토큰값을 받아옵니다.
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    AppLog.e("Fetching FCM registration token failed", task.exception.toString())
                    return@OnCompleteListener
                }
                // Get new FCM registration token
                val token = task.result
                viewModel.setDeviceToken(token)
                AppLog.e("Fetching FCM registration token Success", token)
            })
    }
}