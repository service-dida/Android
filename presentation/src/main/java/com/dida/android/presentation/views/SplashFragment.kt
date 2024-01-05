package com.dida.android.presentation.views

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.android.R
import com.dida.common.dialog.DefaultDialogFragment
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

    override val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            initSplashScreen()
        }
    }

    override fun initStartView() {
        viewModel.onVersionCheck(requireContext().getString(R.string.app_version).toLong())
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.navigateToHome.collectLatest {
                    navigate(SplashFragmentDirections.actionMainFragment())
                }
            }

            launch {
                viewModel.forceUpdate.collectLatest { forceUpdate ->
                    when (forceUpdate) {
                        true -> { showForceUpdateDialog() }
                        false -> { getFirebaseToken() }
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {}

    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                AppLog.e("Fetching FCM registration token failed", task.exception.toString())
                viewModel.onGoogleServiceError()
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            viewModel.onAppSetUp(token)
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

    private fun showForceUpdateDialog() {
        DefaultDialogFragment.Builder()
            .title(getString(com.dida.common.R.string.app_force_update_dialog_label))
            .message(getString(com.dida.common.R.string.app_force_update_dialog_message))
            .positiveButton(getString(com.dida.common.R.string.app_force_update_dialog_positive_button_label), object : DefaultDialogFragment.OnClickListener {
                override fun onClick() {
                    // TODO : 앱 출시후 해당 내용 추가하기
                }
            })
            .negativeButton(getString(com.dida.common.R.string.app_force_update_dialog_negative_button_label), object : DefaultDialogFragment.OnClickListener {
                override fun onClick() {
                    requireActivity().finishAffinity()
                }
            })
            .build()
            .show(childFragmentManager, "force_update_dialog")
    }
}
