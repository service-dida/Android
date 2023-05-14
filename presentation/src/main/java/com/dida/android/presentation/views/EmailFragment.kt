package com.dida.android.presentation.views

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.email.R
import com.dida.common.util.maskEmail
import com.dida.common.util.repeatOnResumed
import com.dida.common.widget.DefaultSnackBar
import com.dida.email.EmailNavigationAction
import com.dida.email.EmailViewModel
import com.dida.email.databinding.FragmentEmailBinding
import com.dida.password.PasswordDialog
import com.dida.settings.SettingsNavigationAction
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

enum class RequestEmailType{
    MAKE_WALLET,
    RESET_PASSWORD
}

@AndroidEntryPoint
class EmailFragment : BaseFragment<FragmentEmailBinding, EmailViewModel>(R.layout.fragment_email) {

    private val TAG = "EmailFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_email // get() : 커스텀 접근자, 코틀린 문법

    var timer = Timer()

    override val viewModel : EmailViewModel by viewModels()
    val navController: NavController by lazy { findNavController() }

    private val args: EmailFragmentArgs by navArgs()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        getMaskingEmail()
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is EmailNavigationAction.SuccessCreateWallet -> {
                        toastMessage(getString(R.string.success_create_wallet))
                        navController.popBackStack()
                    }
                    is EmailNavigationAction.SuccessResetPassword -> {
                        toastMessage(getString(R.string.success_reset_password))
                        navController.popBackStack()
                    }
                }
            }

            viewLifecycleOwner.repeatOnResumed {
                viewModel.retryEvent.collectLatest {
                    toastMessage(getString(R.string.fail_notCorrect_password))
                }
            }
        }
    }

    override fun initAfterBinding() {

        binding.okBtn.setOnClickListener {
            timer.cancel()
            if(viewModel.verifyNumberCheck()) {
                if(args.requestEmailType == RequestEmailType.MAKE_WALLET){
                    makeWallet()
                }else if(args.requestEmailType == RequestEmailType.RESET_PASSWORD){
                    resetPassword()
                }
            }else{
                showMessageSnackBar(getString(R.string.email_response_not_correct))
            }
        }

        binding.sendBtn.setOnClickListener {
            viewModel.getSendEmail()
            timeCheck()
        }
    }

    private fun makeWallet(){
        PasswordDialog(6,getString(R.string.create_wallet_title),getString(R.string.create_wallet_subTitle),true){ success, firstPassword ->
            if (success) {
                PasswordDialog(6,getString(R.string.reconfirm_password_title),getString(R.string.reconfirm_password_sibTitle),true) { success, secondPassword ->
                    viewModel.postCreateWallet(firstPassword, secondPassword)
                }.show(childFragmentManager,"EmailFragment")
            }
        }.show(childFragmentManager,"EmailFragment")
    }

    private fun resetPassword(){
        PasswordDialog(6,getString(R.string.reset_password_title),getString(R.string.reset_password_subTitle),true){ success, firstPassword ->
            if (success) {
                PasswordDialog(6,getString(R.string.reconfirm_password_title),getString(R.string.reconfirm_password_sibTitle),true) { success, secondPassword ->
                    viewModel.changePassword(firstPassword,secondPassword)
                }.show(childFragmentManager,"EmailFragment")
            }
        }.show(childFragmentManager,"EmailFragment")
    }

    private fun timeOver() {
        timer.cancel()
        lifecycleScope.launch {
            //TODO : 비밀번호 다시 전송하기로 ui 변경
        }
    }

    private fun timeCheck() {
        var minute = 4
        var second = 60
        timer = Timer()

        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                // 반복실행할 구문
                second -= 1
                if(second == 0){
                    second = 60
                    minute -= 1
                }
                lifecycleScope.launch {
                    viewModel.timeToString(minute, second)
                }
                if (minute < 0) {
                    timer.cancel()
                    timeOver()
                }
            }
        }
        // timer 실행
        timer.schedule(timerTask, 0, 1000)
    }
    private fun getMaskingEmail(){
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                toastMessage("사용자 정보 요청 실패")
            } else if (user != null) {
                if(user.kakaoAccount?.email.isNullOrEmpty()){
                    binding.emailMaskingTv.text = "이메일을 불러올 수 없습니다."
                }else{
                    binding.emailMaskingTv.text = "회원가입시 입력한 ${maskEmail(user.kakaoAccount?.email!!)}으로 인증번호가 전송됩니다."
                }
            }
        }
    }

    private fun showMessageSnackBar(message: String) {
        DefaultSnackBar.Builder()
            .view(binding.root)
            .message(message)
            .hasBottomMargin(false)
            .build()
    }
}
