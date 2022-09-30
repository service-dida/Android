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
        showLoadingDialog()
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.sendEmailState.collect {
                    dismissLoadingDialog()
                    timeCheck()
                }
            }

            launch {
                viewModel.createWalletState.collect { result ->
                    if(result) {
                        dismissLoadingDialog()
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
                // 지갑이 없으므로 false로 넘겨줘야 한다.
                val passwordDialog = PasswordDialog(false) {
                    viewModel.postCreateWallet(it, it)
                    showLoadingDialog()
                }
                passwordDialog.show(requireActivity().supportFragmentManager, passwordDialog.tag)
            }
        }
    }

    private fun timeOver() {
        timer.cancel()
        lifecycleScope.launch {
            toastMessage("시간이 초과되어 다시 인증번호 전송을 합니다.")
            viewModel.getSendEmail()
            showLoadingDialog()
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