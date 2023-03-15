package com.dida.android.presentation.views

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.android.util.toLoginFailure
import com.dida.android.util.toLoginSuccess
import com.dida.common.util.repeatOnResumed
import com.dida.login.LoginMainViewModel
import com.dida.login.LoginNavigationAction
import com.dida.login.databinding.FragmentLoginmainBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginMainFragment : BaseFragment<FragmentLoginmainBinding, LoginMainViewModel>(com.dida.login.R.layout.fragment_loginmain) {

    private val TAG = "LoginMainFragment"

    override val layoutResourceId: Int
        get() = com.dida.login.R.layout.fragment_loginmain // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : LoginMainViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {
        viewLifecycleOwner.repeatOnResumed {
            viewModel.navigationEvent.collectLatest {
                when(it){
                    is LoginNavigationAction.NavigateToLoginFail -> toastMessage("로그인에 실패하였습니다.")
                    is LoginNavigationAction.NavigateToNickname -> {
                        toastMessage("회원가입이 필요합니다.")
                        val action = LoginMainFragmentDirections.actionLoginMainFragmentToNicknameFragment(it.email)
                        navigate(action)
                    }
                    is LoginNavigationAction.NavigateToHome -> {
                        toastMessage("로그인에 성공하였습니다.")
                        this@LoginMainFragment.toLoginSuccess()
                    }
                    is LoginNavigationAction.NavigateToLogin -> kakaoLogin()
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: back key 이벤트 시 필요한 코드 추가
        view.setOnKeyListener { _, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.KEYCODE_BACK) {
                this@LoginMainFragment.toLoginFailure()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun kakaoLogin() {
        val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            // 로그인 실패
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> toastMessage("접근이 거부 됨(동의 취소)")
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> toastMessage("유효하지 않은 앱")
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> toastMessage("인증 수단이 유효하지 않아 인증할 수 없는 상태")
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> toastMessage("요청 파라미터 오류")
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> toastMessage("유효하지 않은 scope ID")
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> toastMessage("설정이 올바르지 않음(android key hash)")
                    error.toString() == AuthErrorCause.ServerError.toString() -> toastMessage("서버 내부 에러")
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> toastMessage("앱이 요청 권한이 없음")
                    else -> toastMessage("카카오톡의 미로그인")
                }
            }
            //로그인 성공
            else if (token != null) {
                viewModel.loginAPIServer(token.accessToken)
            }
        }

        // 카카오톡 설치여부 확인
        if(viewModel.kakaoTalkLoginState.value && UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = kakaoCallback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = kakaoCallback)
        }
    }
}
