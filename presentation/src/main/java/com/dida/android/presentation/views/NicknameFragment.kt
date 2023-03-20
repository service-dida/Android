package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.dida.android.util.toLoginSuccess
import com.dida.nickname.NicknameNavigationAction
import com.dida.nickname.NicknameViewModel
import com.dida.nickname.databinding.FragmentNicknameBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NicknameFragment : BaseFragment<FragmentNicknameBinding, NicknameViewModel>(com.dida.nickname.R.layout.fragment_nickname) {

    private val TAG = "NicknameFragment"

    override val layoutResourceId: Int
        get() = com.dida.nickname.R.layout.fragment_nickname // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : NicknameViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        val args: NicknameFragmentArgs by navArgs()
        viewModel.emailState.value = args.email
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is NicknameNavigationAction.NavigateToHome -> {
                        toastMessage("회원가입에 성공 하였습니다.")
                        this@NicknameFragment.toLoginSuccess()
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {
    }
}
