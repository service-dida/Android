package com.dida.android.presentation.views

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dida.android.GlobalApplication.Companion.editor
import com.dida.android.R
import com.dida.android.databinding.FragmentSplashBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(R.layout.fragment_splash) {

    override val layoutResourceId: Int
        get() = R.layout.fragment_splash // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : SplashViewModel by viewModels()
    lateinit var navController: NavController
    var jwt: String? = null

    override fun initStartView() {
        navController = Navigation.findNavController(requireView())
        viewModel.checkVersion()
//        val dialog = PasswordBottomSheetDialog()
//        dialog.show(childFragmentManager, "SplashFragment")
    }

    override fun initDataBinding() {
//        viewModel.kakaoLoginResponse.observe(this, Observer {
//            if(it.isSuccess){
//                if(it.result.jwt != null){
//                    editor.putString("X-ACCESS-TOKEN", it.result.jwt)
//                }
//                editor.putString("uuid", it.result.uuid)
//                editor.commit()
//                navController.navigate(R.id.action_splashFragment_to_mainFragment)
//            }
//            else{
//                Toast.makeText(context, "카카오 로그인을 다시 진행해주세요", Toast.LENGTH_SHORT)
//            }
//        })
    }

    override fun initAfterBinding() {

        // kakao login
//        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
//            if (error != null) {
//                Log.e("response!!", error.toString())
//                when {
//                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
//                        Toast.makeText(context, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
//                        Toast.makeText(context, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
//                        Toast.makeText(context, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
//                        Toast.makeText(context, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
//                        Toast.makeText(context, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
//                        Toast.makeText(context, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                    error.toString() == AuthErrorCause.ServerError.toString() -> {
//                        Toast.makeText(context, "서버 내부 에러", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
//                        Toast.makeText(context, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
//                    }
//                    else -> { // Unknown
//                        Toast.makeText(context, "카카오톡의 미로그인", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            } else if (token != null) {
//                Log.d("reponse!!", token.accessToken)
//                val request = SocialLoginRequest(token.accessToken)
//                viewModel.kakaoLogin(request)
//            }
//        }

//        binding.kakaoLogin.setOnClickListener {
//            if(UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())){
//                UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = callback)
//            }else {
//                UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
//            }
//        }
    }
}