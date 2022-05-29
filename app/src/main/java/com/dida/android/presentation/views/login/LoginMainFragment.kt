package com.dida.android.presentation.views.login

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.dida.android.R
import com.dida.android.databinding.FragmentLoginmainBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.viewmodel.login.LoginMainViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginMainFragment : BaseFragment<FragmentLoginmainBinding, LoginMainViewModel>(R.layout.fragment_nickname) {

    override val layoutResourceId: Int
        get() = R.layout.fragment_loginmain // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : LoginMainViewModel by viewModels()


    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.kakaoLoginButton.setOnClickListener {
            //TODO : 해당 코드를 Viewmodel로 옮기기
            val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    // 로그인 실패
                    Toast.makeText(requireContext(), "로그인 실패", Toast.LENGTH_SHORT).show()
                } else if (token != null) {
                    //로그인 성공
                    Toast.makeText(requireContext(), "로그인 성공 \n${token.accessToken}", Toast.LENGTH_SHORT).show()
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