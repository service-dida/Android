package com.dida.android.presentation.views

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.android.R
import com.dida.android.util.toLoginFailure
import com.dida.android.util.toLoginSuccess
import com.dida.compose.theme.DIDA_THEME
import com.dida.compose.theme.DidaTypography
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
class LoginMainFragment :
    BaseFragment<FragmentLoginmainBinding, LoginMainViewModel>(com.dida.login.R.layout.fragment_loginmain) {

    private val TAG = "LoginMainFragment"

    override val layoutResourceId: Int
        get() = com.dida.login.R.layout.fragment_loginmain // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: LoginMainViewModel by viewModels()

    private val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
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
                }
            }
        }
    }

    override fun initAfterBinding() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: back key 이벤트 시 필요한 코드 추가
        view.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.KEYCODE_BACK) {
                this@LoginMainFragment.toLoginFailure()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        binding.composeView.apply {
            setContent {
                LoginScreen(
                    onKakaoLoginClicked = { onKakaoTalkLogin() },
                    onKakakoWebLoginClicked = { onKakaoWebLogin() },
                    onCloseButtonClicked = { this@LoginMainFragment.toLoginFailure() }
                )
            }
        }
    }

    private fun onKakaoTalkLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = kakaoCallback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = kakaoCallback)
        }
    }

    private fun onKakaoWebLogin() {
        UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = kakaoCallback)
    }
}

@Composable
fun LoginScreen(
    onKakaoLoginClicked: () -> Unit,
    onKakakoWebLoginClicked: () -> Unit,
    onCloseButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        Image(
            modifier = Modifier
                .padding(16.dp)
                .clickable { onCloseButtonClicked() },
            painter = painterResource(id = com.dida.common.R.drawable.ic_close_white),
            contentDescription = "닫기 버튼"
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier
                .size(132.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = com.dida.common.R.mipmap.img_dida_logo_foreground),
            contentDescription = "디다 로고"
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "빠르게 로그인하기",
            style = DidaTypography.caption,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        onKakaoLoginButton(onKakaoLoginClicked)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        onKakaoWebLoginButton(onKakakoWebLoginClicked)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun onKakaoLoginButton(onKakaoLoginClicked: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        color = Color(0xFFFEE500),
        shape = RoundedCornerShape(8.dp),
        onClick = { onKakaoLoginClicked() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.kakao_logo_1),
                contentDescription = "카카오 로고"
            )
            Spacer(modifier = Modifier.size(14.dp))
            Text(
                text = "카카오톡으로 시작하기",
                style = DidaTypography.h3,
                fontSize = 15.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun onKakaoWebLoginButton(onKakakoWebLoginClicked: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
        color = Color(0xFF121212),
        shape = RoundedCornerShape(8.dp),
        onClick = { onKakakoWebLoginClicked() }
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            text = "카카오계정 직접 입력하기",
            style = DidaTypography.h3,
            fontSize = 15.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}


@Preview
@Composable
fun LoginPreview() {
    DIDA_THEME {
        LoginScreen(
            onKakaoLoginClicked = {},
            onKakakoWebLoginClicked = {},
            onCloseButtonClicked = {}
        )
    }
}