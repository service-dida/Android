package com.dida.android.presentation.views

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.common.util.repeatOnCreated
import com.dida.common.util.repeatOnResumed
import com.dida.email.EmailViewModel
import com.dida.email.databinding.FragmentEmailBinding
import com.dida.password.PasswordDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class EmailFragment : BaseFragment<FragmentEmailBinding, EmailViewModel>(com.dida.email.R.layout.fragment_email) {

    private val TAG = "EmailFragment"

    override val layoutResourceId: Int
        get() = com.dida.email.R.layout.fragment_email // get() : 커스텀 접근자, 코틀린 문법

    var timer = Timer()

    override val viewModel : EmailViewModel by viewModels()
    val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.createWalletState.collect { result ->
                if(result) {
                    setFragmentResult("walletCheck", bundleOf("hasWallet" to true))
                    navController.popBackStack()
                }
            }
        }

        viewLifecycleOwner.repeatOnResumed {
            viewModel.retryEvent.collectLatest {
                toastMessage("두 비밀번호가 일치하지않습니다. 다시입력해주세요.")
                makePassword()
            }
        }
    }

    override fun initAfterBinding() {

        binding.okBtn.setOnClickListener {
            timer.cancel()
            if(viewModel.verifyNumberCheck()) {
                makePassword()
            }
        }

        binding.sendBtn.setOnClickListener {
            viewModel.getSendEmail()
            timeCheck()
        }
    }

    private fun makePassword(){
        PasswordDialog(6,"지갑 비밀번호 설정","NFT 거래 시 사용할 비밀번호를 설정해주세요. ",true){ success, firstPassword ->
            if (success) {
                PasswordDialog(6,"비밀번호 확인","비밀번호를 다시 한번 입력해주세요.",true) { success, secondPassword ->
                    viewModel.postCreateWallet(firstPassword, secondPassword)
                }
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
}
