package com.dida.android.presentation.views.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.android.databinding.FragmentLoginmainBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.views.nav.NavHostActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginMainFragment : BaseFragment<FragmentLoginmainBinding, LoginMainViewModel>(R.layout.fragment_loginmain) {

    private val TAG = "LoginMainFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_loginmain // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : LoginMainViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent

        // test
//        val action = LoginMainFragmentDirections.actionLoginMainFragmentToNicknameFragment("test")
//        navController.navigate(action)
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collect {
                dismissLoadingDialog()
                when(it){
                    is LoginNavigationAction.NavigateToLoginFail -> {
                        Toast.makeText(requireContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show()
                    }
                    is LoginNavigationAction.NavigateToNickname -> {
                        Toast.makeText(requireContext(),"회원가입이 필요합니다.",Toast.LENGTH_SHORT).show()
                        val action = LoginMainFragmentDirections.actionLoginMainFragmentToNicknameFragment(it.email)
                        navController.navigate(action)
                    }
                    is LoginNavigationAction.NavigateToHome -> {
                        Toast.makeText(requireContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show()
                        var intent = Intent(requireActivity(), NavHostActivity::class.java)
                        activity?.setResult(9001,intent)
                        activity?.finish()
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {
        binding.kakaoLoginButton.setOnClickListener {
            val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                // 로그인 실패
                if (error != null) {
                    Log.d(TAG, "kakaoLogin 실패 ${error.message}")
                }
                //로그인 성공
                else if (token != null) {
                    Log.d(TAG, "kakaoLogin 성공 ${token.accessToken} ")
                    viewModel.loginAPIServer(token.accessToken)
                    showLoadingDialog()
                }
            }

            // 카카오톡 설치여부 확인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
                UserApiClient.instance.loginWithKakaoTalk(
                    requireContext(),
                    callback = kakaoCallback
                )
            } else {
                UserApiClient.instance.loginWithKakaoAccount(
                    requireContext(),
                    callback = kakaoCallback
                )
            }
        }
    }
}