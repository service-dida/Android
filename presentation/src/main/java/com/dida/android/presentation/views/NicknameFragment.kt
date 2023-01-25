package com.dida.android.presentation.views

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.android.databinding.FragmentNicknameBinding
import com.dida.android.presentation.activities.NavHostActivity
import com.dida.nickname.NicknameNavigationAction
import com.dida.nickname.NicknameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NicknameFragment : BaseFragment<FragmentNicknameBinding, NicknameViewModel>(R.layout.fragment_nickname) {

    private val TAG = "NicknameFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_nickname // get() : 커스텀 접근자, 코틀린 문법

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
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.errorEvent.collectLatest {
                    showToastMessage(it)
                }
            }

            launch {
                viewModel.navigationEvent.collectLatest {
                    when(it) {
                        is NicknameNavigationAction.NavigateToHome -> {
                            var intent = Intent(requireActivity(), NavHostActivity::class.java)
                            activity?.let { activity ->
                                toastMessage("회원가입에 성공하였습니다.")
                                activity.setResult(9001, intent)
                                activity.finish()
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {
    }
}
