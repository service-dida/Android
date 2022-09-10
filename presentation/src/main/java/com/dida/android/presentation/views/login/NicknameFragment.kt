package com.dida.android.presentation.views.login

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.android.databinding.FragmentNicknameBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.views.nav.NavHostActivity
import com.dida.domain.model.login.CreateUserRequestModel
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

    override fun initStartView() {
        navController = Navigation.findNavController(requireView())
        val args: NicknameFragmentArgs by navArgs()
        email = args.email
    }

    override fun initDataBinding() {
        viewModel.errorLiveDate.observe(this) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.nickNameSuccessLiveData.observe(this) {
            if (it) {
                // 닉네임 사용중
                viewModel.setNicknameVerify(2)
            } else {
                // 닉네임 사용가능
                viewModel.setNicknameVerify(3)
            }
        }

        viewModel.createUserSuccessLiveData.observe(this) {
            if (it) {
                var intent = Intent(requireActivity(), NavHostActivity::class.java)
                activity?.setResult(9001,intent)
                Toast.makeText(requireContext(), "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(requireContext(), "통신상태가 원활하지 않습니다.", Toast.LENGTH_SHORT).show()
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
                        // 8글자를 초과한 경우
                        viewModel.setNicknameVerify(1)
                    }
                    else{
                        viewModel.setNicknameVerify(0)
                        viewModel.nicknameAPIServer(searchFor)
                    }
                }
            }
        })

        binding.okBtn.setOnClickListener {
            if(viewModel.nickNameSuccessLiveData.value == true){
                val request = CreateUserRequestModel(email, binding.nickNameEdit.text.toString())
                viewModel.createUserAPIServer(request)
            }
        }
    }
}