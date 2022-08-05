package com.dida.android.presentation.views.email

import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dida.android.R
import com.dida.android.databinding.FragmentEmailBinding
import com.dida.android.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class EmailFragment : BaseFragment<FragmentEmailBinding, EmailViewModel>(R.layout.fragment_email) {

    private val TAG = "EmailFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_email // get() : 커스텀 접근자, 코틀린 문법

    val timer = Timer()

    override val viewModel : EmailViewModel by viewModels()
    lateinit var navController: NavController
    var emailCheck: String = ""
    var nextCheck = false

    override fun initStartView() {
        navController = Navigation.findNavController(requireView())

        viewModel.getSendEmail()

        // ui test용
//        timeCheck()
    }

    override fun initDataBinding() {
        viewModel.sendEmailLiveData.observe(this) {
            emailCheck = it
            timeCheck()
        }

        viewModel.errorLiveData.observe(this) {
            Toast.makeText(context, "네트워크 상황이 안좋습니다.", Toast.LENGTH_SHORT).let {
//                viewModel.getSendEmail()
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
                        nextCheck = false
                        binding.okBtn.let { item ->
                            item.setBackgroundResource(R.drawable.custom_okbtn_fail_custom)
                            item.setTextColor(ContextCompat.getColor(requireContext(),R.color.surface6))
                        }
                    }
                    else if(emailCheck == binding.emailCheck.text.toString()){
                        nextCheck = true
                        binding.okBtn.let { item ->
                            item.setBackgroundResource(R.drawable.custom_okbtn_success)
                            item.setTextColor(ContextCompat.getColor(requireContext(),R.color.mainblack))
                        }
                    }
                }
            }
        })

        binding.resentBtn.setOnClickListener {
            timer.cancel()
            timeCheck()
        }

        binding.okBtn.setOnClickListener {
            if(nextCheck) {
            }
        }
    }

    private fun timeOver() {
        nextCheck = false
        binding.okBtn.let { item ->
            item.setBackgroundResource(R.drawable.custom_okbtn_fail_custom)
            item.setTextColor(ContextCompat.getColor(requireContext(),R.color.surface6))
        }
        Toast.makeText(context, "시간이 초과되어 다시 인증번호 전송을 합니다.", Toast.LENGTH_SHORT).let {
            viewModel.getSendEmail()
        }
    }

    private fun timeCheck() {
        var minute = 4
        var second = 60

        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                // 반복실행할 구문
                second -= 1
                if(second == 0){
                    second = 60
                    minute -= 1
                }

                if(second<10) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        binding.timeTxt.text = "0$minute:0$second"
                    }
                }
                else {
                    lifecycleScope.launch(Dispatchers.Main) {
                        binding.timeTxt.text = "0$minute:$second"
                    }
                }

                if(minute < 0){
                    timeOver()
                    timer.cancel()
                }
            }
        }

        // timer 실행
        timer.schedule(timerTask, 0, 300)
    }
}