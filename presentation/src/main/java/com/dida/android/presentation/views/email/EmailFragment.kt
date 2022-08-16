package com.dida.android.presentation.views.email

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dida.android.R
import com.dida.android.databinding.FragmentEmailBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.views.password.PasswordDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class EmailFragment() : BaseFragment<FragmentEmailBinding, EmailViewModel>(R.layout.fragment_email) {

    private val TAG = "EmailFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_email // get() : 커스텀 접근자, 코틀린 문법

    var timer = Timer()

    override val viewModel : EmailViewModel by viewModels()
    val navController: NavController by lazy {
        Navigation.findNavController(requireView())
    }

    override fun initStartView() {
        viewModel.getSendEmail()
        showLoadingDialog()
    }

    override fun initDataBinding() {
        viewModel.sendEmailLiveData.observe(this) {
            dismissLoadingDialog()
            timeCheck()
        }

        viewModel.errorLiveData.observe(this) {
            dismissLoadingDialog()
            Toast.makeText(context, "네트워크 상황이 안좋습니다.", Toast.LENGTH_SHORT).show()
        }

        viewModel.createWalletLiveData.observe(this) {
            if(it) {
                dismissLoadingDialog()
                navController.previousBackStackEntry?.savedStateHandle?.set("WalletCheck", true)
                navController.popBackStack()
            }
        }
    }

    override fun initAfterBinding() {
        binding.emailCheck.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.verifyNumberCheck(binding.emailCheck.text.toString())
            }
        })

        binding.resentBtn.setOnClickListener {
            timeOver()
        }

        binding.okBtn.setOnClickListener {
            timer.cancel()
            if(viewModel.verifyCheck.value == true) {
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
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            viewModel.verifyNumberCheck("")
            Toast.makeText(requireContext(), "시간이 초과되어 다시 인증번호 전송을 합니다.", Toast.LENGTH_LONG).show()
            viewModel.getSendEmail()
            showLoadingDialog()
        }
    }

    fun timeToString(minute: Int, second: Int) {
        val handler = Handler(Looper.getMainLooper())
        if(second>=10) {
            handler.post {
                binding.timeTxt.text = "0$minute:$second"
            }
        }
        else {
            handler.post {
                binding.timeTxt.text = "0$minute:0$second"
            }
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

                timeToString(minute, second)
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