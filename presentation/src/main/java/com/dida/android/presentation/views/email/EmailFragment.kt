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
    lateinit var navController: NavController
    var emailCheck: String = ""

    override fun initStartView() {
        navController = Navigation.findNavController(requireView())
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        viewModel.setVerify(false)
        viewModel.getSendEmail()
//        timeCheck()
    }

    override fun initDataBinding() {
        viewModel.sendEmailLiveData.observe(this) {
            emailCheck = it
            timeCheck()
        }

        viewModel.errorLiveData.observe(this) {
            Toast.makeText(context, "네트워크 상황이 안좋습니다.", Toast.LENGTH_SHORT).show()
        }

        viewModel.createWalletLiveData.observe(this) {
            if(it) {
                navController.previousBackStackEntry?.savedStateHandle?.set("WalletCheck", true)
                navController.popBackStack()
            }
        }
    }

    override fun initAfterBinding() {
        binding.emailCheck.addTextChangedListener(object : TextWatcher {
            private var searchFor = ""
            var job: Job? = null

            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                if (searchText == searchFor)
                    return

                job?.cancel()
                searchFor = searchText
                job = lifecycleScope.launch {
                    delay(500)  //debounce timeOut
                    if (searchText != searchFor)
                        return@launch

                    if(s.isEmpty() || s.length > 8){
                        viewModel.setVerify(false)
                    }
                    else if(emailCheck == binding.emailCheck.text.toString()){
                        viewModel.setVerify(true)

                    }
                }
            }
        })

        binding.resentBtn.setOnClickListener {
            timeOver()
            viewModel.getSendEmail()
        }

        binding.okBtn.setOnClickListener {
            timer.cancel()
            if(viewModel.verifyCheck.value == true) {
                val passwordDialog = PasswordDialog {
                    viewModel.postCreateWallet(it, it)
                }
                passwordDialog.show(requireActivity().supportFragmentManager, passwordDialog.tag)
            }
        }
    }

    private fun timeOver() {
        timer.cancel()
        viewModel.setVerify(false)
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            binding.okBtn.let { item ->
                item.setBackgroundResource(R.drawable.custom_okbtn_fail_custom)
                item.setTextColor(ContextCompat.getColor(requireContext(), R.color.surface6))
            }
            Toast.makeText(requireContext(), "시간이 초과되어 다시 인증번호 전송을 합니다.", Toast.LENGTH_LONG).show()
            viewModel.getSendEmail()
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
                if(minute < 4){
                    timer.cancel()
                    timeOver()
                }
            }
        }
        // timer 실행
        timer.schedule(timerTask, 0, 1000)
    }
}