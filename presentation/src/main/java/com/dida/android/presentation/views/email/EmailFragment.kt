package com.dida.android.presentation.views.email

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.android.databinding.FragmentEmailBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.views.password.InputNumberDialog
import com.dida.android.presentation.views.password.PasswordDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class EmailFragment() : BaseFragment<FragmentEmailBinding, EmailViewModel>(R.layout.fragment_email) {

    private val TAG = "EmailFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_email // get() : 커스텀 접근자, 코틀린 문법

    var timer = Timer()

    override val viewModel : EmailViewModel by viewModels()
    val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        viewModel.getSendEmail()
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.sendEmailState.collect {
                    timeCheck()
                }
            }

            launch {
                viewModel.createWalletState.collect { result ->
                    if(result) {
                        setFragmentResult("walletCheck", bundleOf("hasWallet" to true))
                        navController.popBackStack()
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {
        binding.resentBtn.setOnClickListener {
            timeOver()
        }

        binding.okBtn.setOnClickListener {
            timer.cancel()
            if(viewModel.verifyCheckState.value) {
                InputNumberDialog(6,"비밀번호 입력","6자리를 입력해주세요."){success, password ->
                    if(success){
                        viewModel.postCreateWallet(password, password)
                    }else{
                        navController.popBackStack()
                    }
                }.show(childFragmentManager,"AddFragment")
            }
        }
    }

    private fun timeOver() {
        timer.cancel()
        lifecycleScope.launch {
            toastMessage("시간이 초과되어 다시 인증번호 전송을 합니다.")
            viewModel.getSendEmail()
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
                if(minute < 0){
                    timer.cancel()
                    timeOver()
                }
            }
        }
        // timer 실행
        timer.schedule(timerTask, 0, 1000)
    }
}