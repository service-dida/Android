package com.dida.android.presentation.views

import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager.LayoutParams
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.email.R
import com.dida.common.util.maskEmail
import com.dida.common.util.repeatOnResumed
import com.dida.common.widget.DefaultSnackBar
import com.dida.domain.main.model.RequestEmailType
import com.dida.email.EmailNavigationAction
import com.dida.email.EmailViewModel
import com.dida.email.databinding.FragmentEmailBinding
import com.dida.password.PasswordDialog
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmailFragment : BaseFragment<FragmentEmailBinding, EmailViewModel>(R.layout.fragment_email) {

    private val TAG = "EmailFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_email // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : EmailViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }
    private var countDownTimer :CountDownTimer? = null
    private val args: EmailFragmentArgs by navArgs()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        initToolbar()
        getMaskingEmail()
        requireActivity().window.setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {

            launch {
                viewModel.navigationEvent.collectLatest {
                    when (it) {
                        is EmailNavigationAction.SuccessCreateWallet -> {
                            showMessageSnackBar(getString(R.string.success_create_wallet))
                            navController.popBackStack()
                        }
                        is EmailNavigationAction.SuccessResetPassword -> {
                            showMessageSnackBar(getString(R.string.success_reset_password))
                            navController.popBackStack()
                        }
                    }
                }
            }

            launch {
                viewModel.sendEvent.collectLatest {
                    startTimer()
                }
            }
        }
        viewLifecycleOwner.repeatOnResumed {
            viewModel.retryEvent.collectLatest {
                showMessageSnackBar(getString(R.string.fail_notCorrect_password))
            }
        }
    }

    override fun initAfterBinding() {
        binding.okBtn.setOnClickListener {
            if (viewModel.verifyNumberCheck()) {
                if (args.requestEmailType == RequestEmailType.MAKE_WALLET) {
                    makeWallet()
                } else if (args.requestEmailType == RequestEmailType.RESET_PASSWORD) {
                    resetPassword()
                }
            } else {
                showMessageSnackBar(getString(R.string.email_response_not_correct))
            }
        }

        binding.sendBtn.setOnClickListener {
            binding.sendBtn.text = getString(R.string.email_resend)
            viewModel.getSendEmail()
        }
    }

    private fun makeWallet(){
        PasswordDialog(6,getString(R.string.create_wallet_title),getString(R.string.create_wallet_subTitle),true,false){ success, firstPassword ->
            if (success) {
                PasswordDialog(6,getString(R.string.reconfirm_password_title),getString(R.string.reconfirm_password_sibTitle),true,false) { success, secondPassword ->
                    viewModel.postCreateWallet(firstPassword, secondPassword)
                }.show(childFragmentManager,"EmailFragment")
            }
        }.show(childFragmentManager,"EmailFragment")
    }

    private fun resetPassword(){
        PasswordDialog(6,getString(R.string.reset_password_title),getString(R.string.reset_password_subTitle),true,false){ success, firstPassword ->
            if (success) {
                PasswordDialog(6,getString(R.string.reconfirm_password_title),getString(R.string.reconfirm_password_sibTitle),true,false) { success, secondPassword ->
                    viewModel.changePassword(firstPassword,secondPassword)
                }.show(childFragmentManager,"EmailFragment")
            }
        }.show(childFragmentManager,"EmailFragment")
    }
    private fun startTimer(){
        binding.timeoverTv.visibility = View.GONE
        binding.emailCheck.setBackgroundResource(com.dida.common.R.drawable.custom_brandlemon_radius10_surface5_width1)

        countDownTimer = object : CountDownTimer(300000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                viewModel.timeToString(minutes.toInt(),seconds.toInt())
            }

            override fun onFinish() {
                binding.timeoverTv.visibility = View.VISIBLE
                binding.emailCheck.errorEvent()
                binding.emailCheck.text?.clear()
            }
        }.start()
    }

    private fun getMaskingEmail(){
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                showToastMessage("사용자 정보 요청 실패")
            } else if (user != null) {
                if(user.kakaoAccount?.email.isNullOrEmpty()){
                    binding.emailMaskingTv.text = "이메일을 불러올 수 없습니다."
                }else{
                    binding.emailMaskingTv.text = "회원가입시 입력한 ${maskEmail(user.kakaoAccount?.email!!)}으로 인증번호가 전송됩니다."
                }
            }
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun showMessageSnackBar(message: String) {
        DefaultSnackBar.Builder()
            .view(binding.root)
            .message(message)
            .hasBottomMargin(false)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}
