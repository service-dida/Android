package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.password.PasswordDialog
import com.dida.swap.SwapNavigationAction
import com.dida.swap.SwapViewModel
import com.dida.swap.databinding.FragmentSwapBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SwapFragment : BaseFragment<FragmentSwapBinding, SwapViewModel>(com.dida.swap.R.layout.fragment_swap) {

    private val TAG = "SwapFragment"

    override val layoutResourceId: Int
        get() = com.dida.swap.R.layout.fragment_swap

    override val viewModel : SwapViewModel by viewModels()


    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        viewModel.initWalletAmount()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when(it) {
                    SwapNavigationAction.NavigateToPassword -> {
                        PasswordDialog(6, "비밀번호 설정", "본인 확인 시 사용됩니다.") { success, password ->
                            if(success){
                                //viewModel.swap(password,binding.topCoinAmountEt.text.toString().toDouble())
                                navigate(
                                    SwapFragmentDirections.actionSwapFragmentToSwapLoadingFragment(binding.topCoinAmountEt.text.toString().toFloat(),password,SwapViewModel.SwapType.KLAY_TO_DIDA)
                                )
                            }
                        }.show(childFragmentManager, "AddFragment")
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {

    }
}
