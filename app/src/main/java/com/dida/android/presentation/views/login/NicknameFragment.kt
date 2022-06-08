package com.dida.android.presentation.views.login

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.android.databinding.FragmentNicknameBinding
import com.dida.android.domain.model.login.CreateUserRequestModel
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.viewmodel.login.NicknameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NicknameFragment : BaseFragment<FragmentNicknameBinding, NicknameViewModel>(R.layout.fragment_nickname) {

    private val TAG = "NicknameFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_nickname // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : NicknameViewModel by viewModels()
    lateinit var navController: NavController

    lateinit var email: String
    var nextCheck = false

    override fun initStartView() {
        navController = Navigation.findNavController(requireView())
        val args: NicknameFragmentArgs by navArgs()
        email = args.email
    }

    override fun initDataBinding() {
        viewModel.nickNameSuccessLiveData.observe(this) {
            if (!it) {
                Log.d(TAG, "사용가능한 닉네임")
                nextCheck = true
                binding.okBtn.let { item ->
                    item.setBackgroundResource(R.drawable.okbtn_success_custom)
                    item.setTextColor(ContextCompat.getColor(requireContext(),R.color.mainblack))
                }
                binding.nicknameCheck.setImageResource(R.drawable.ic_nickname_success)
                binding.nicknameCheckTxt.text = "사용 가능한 닉네임 입니다."
                binding.nicknameCheckTxt.let { item ->
                    item.text = "사용 가능한 닉네임 입니다."
                    item.setTextColor(ContextCompat.getColor(requireContext(),R.color.notice_green))
                }
            } else {
                nextCheck = false
                Log.d(TAG, "중복된 닉네임")
                binding.okBtn.let { item ->
                    item.setBackgroundResource(R.drawable.okbtn_fail_custom)
                    item.setTextColor(ContextCompat.getColor(requireContext(),R.color.surface6))
                }
                binding.nicknameCheck.setImageResource(R.drawable.ic_nickname_fail)
                binding.nicknameCheckTxt.let { item ->
                    item.text = "중복된 닉네임 입니다."
                    item.setTextColor(ContextCompat.getColor(requireContext(),R.color.notice_red))
                }
            }
        }

        viewModel.createUserSuccessLiveData.observe(this) {
            if (it) {
                navController.popBackStack()
            }
            else {
                Toast.makeText(requireContext(), "사용할 수 없는 닉네임 입니다.", Toast.LENGTH_SHORT)
            }
        }
    }

    override fun initAfterBinding() {
        binding.nickNameEdit.addTextChangedListener(object : TextWatcher {
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
                        Log.d(TAG, "중복된 닉네임")
                        binding.okBtn.let { item ->
                            item.setBackgroundResource(R.drawable.okbtn_fail_custom)
                            item.setTextColor(ContextCompat.getColor(requireContext(),R.color.surface6))
                        }
                        binding.nicknameCheck.setImageResource(R.drawable.ic_nickname_fail)
                        binding.nicknameCheckTxt.let { item ->
                            item.text = "닉네임은 8글자 이하입니다."
                            item.setTextColor(ContextCompat.getColor(requireContext(),R.color.notice_red))
                        }
                    }
                    else{
                        viewModel.nicknameAPIServer(searchFor)
                    }
                }
            }
        })

        binding.okBtn.setOnClickListener {
            if(nextCheck){
                val request = CreateUserRequestModel(email, binding.nickNameEdit.text.toString())
                viewModel.createUserAPIServer(request)
            }
        }
    }
}