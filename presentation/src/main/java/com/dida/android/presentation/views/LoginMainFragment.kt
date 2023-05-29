package com.dida.android.presentation.views

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.dida.android.R
import com.dida.android.util.toLoginFailure
import com.dida.android.util.toLoginSuccess
import com.dida.login.LoginMainViewModel
import com.dida.login.LoginNavigationAction
import com.dida.login.databinding.FragmentLoginmainBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is LoginNavigationAction.NavigateToNickname -> {
                        showToastMessage("회원가입이 필요합니다.")
                        navigate(LoginMainFragmentDirections.actionLoginMainFragmentToNicknameFragment(it.email))
                    }
                    is LoginNavigationAction.NavigateToHome -> {
                        showToastMessage("로그인에 성공하였습니다.")
                        this@LoginMainFragment.toLoginSuccess()
                    }
                    is LoginNavigationAction.NavigateToLogin -> kakaoLogin()
                }
            }
        }
    }

    override fun initAfterBinding() {
        binding.closeBtn.setOnClickListener {
            this@LoginMainFragment.toLoginFailure()
        }
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

        binding.composeView.apply {
            setContent {
                LoginScreen(
                    onKakaoLoginClicked = { },
                    onKakakoWebLoginClicked = {}
                )
            }
        }
    }

    private fun kakaoLogin() {
        val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when (error.toString()) {
                    AuthErrorCause.AccessDenied.toString() -> showToastMessage("접근이 거부 됨(동의 취소)")
                    AuthErrorCause.InvalidClient.toString() -> showToastMessage("유효하지 않은 앱")
                    AuthErrorCause.InvalidGrant.toString() -> showToastMessage("인증 수단이 유효하지 않아 인증할 수 없는 상태")
                    AuthErrorCause.InvalidRequest.toString() -> showToastMessage("요청 파라미터 오류")
                    AuthErrorCause.InvalidScope.toString() -> showToastMessage("유효하지 않은 scope ID")
                    AuthErrorCause.Misconfigured.toString() -> showToastMessage("설정이 올바르지 않음(android key hash)")
                    AuthErrorCause.ServerError.toString() -> showToastMessage("서버 내부 에러")
                    AuthErrorCause.Unauthorized.toString() -> showToastMessage("앱이 요청 권한이 없음")
                    else -> showToastMessage("카카오톡의 미로그인")
                }
            } else if (token != null) {
                viewModel.loginAPIServer(token.accessToken)
            }
        }

        // 카카오톡 설치여부 확인
        if (viewModel.kakaoTalkLoginState.value && UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = kakaoCallback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = kakaoCallback)
        }
    }

    @Composable
    fun LoginScreen(onKakaoLoginClicked: () -> Unit, onKakakoWebLoginClicked: () -> Unit) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                LogoImage()
                Spacer(modifier = Modifier.weight(1f))
                KakaoTalkButton(onKakaoLoginClicked)
                KakaoWebButton(onKakakoWebLoginClicked)
            }
        }
    }

    @Composable
    fun LogoImage() {
        AsyncImage(
                modifier = Modifier.width(128.dp).height(128.dp),
                model = painterResource(id = com.dida.common.R.mipmap.img_dida_logo_foreground),
                contentDescription = null,
                contentScale = ContentScale.Crop
        )
    }

    @Composable
    fun KakaoTalkButton(onKakaoLoginClicked: () -> Unit) {
        Button(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            onClick = onKakaoLoginClicked
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
            ) {
                AsyncImage(
                    modifier = Modifier.wrapContentSize(),
                    model = painterResource(id = R.drawable.kakao_logo_1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Text(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    text = requireContext().getString(R.string.kakao_login),
                )
            }
        }
    }

    @Composable
    fun KakaoWebButton(onKakakoWebLoginClicked: () -> Unit) {
        Button(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            onClick = onKakakoWebLoginClicked
        ) {
            Text(
                text = requireContext().getString(R.string.kakao_login_web),
                fontSize = 15.sp,
            )
        }
    }
}