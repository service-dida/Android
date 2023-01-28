package com.dida.android.presentation.views

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.android.R
import com.dida.common.util.AppLog
import com.dida.splash.SplashViewModel
import com.dida.splash.databinding.FragmentSplashBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(com.dida.splash.R.layout.fragment_splash) {

    override val layoutResourceId: Int
        get() = com.dida.splash.R.layout.fragment_splash // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            initSplashScreen()
        }
    }

    override fun initStartView() {
        viewModel.checkVersion()
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenResumed {
            launch {
                // 추후 배포시 엡데이트 로직 추가
                viewModel.appVersion.collectLatest {
                    if(it.toString() == getString(R.string.app_version)) getToken()
                }
            }

            launch {
                viewModel.navigateToHome.collectLatest {
                    navigate(SplashFragmentDirections.actionMainFragment())
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

    private fun initSplashScreen() {
        val content: View = requireActivity().findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (viewModel.splashScreenGone.value) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else false
                }
            }
        )
    }
}
