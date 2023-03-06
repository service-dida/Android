package com.dida.android.presentation.views

import android.app.Service
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.password.PasswordDialog
import com.dida.swap.SwapNavigationAction
import com.dida.swap.SwapViewModel
import com.dida.swap.databinding.FragmentSwapBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SwapFragment : BaseFragment<FragmentSwapBinding, SwapViewModel>(com.dida.swap.R.layout.fragment_swap) {

    private val TAG = "SwapFragment"

    override val layoutResourceId: Int
        get() = com.dida.swap.R.layout.fragment_swap

    override val viewModel : SwapViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        if(!viewModel.walletCheckState.value) viewModel.getWalletExists()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }
    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.navigationEvent.collectLatest {
                    when (it) {
                        SwapNavigationAction.NavigateToPassword -> {
                            PasswordDialog(6, "비밀번호 입력", "6자리를 입력해주세요.") { success, password ->
                                if (success) {
                                    //viewModel.swap(password,binding.topCoinAmountEt.text.toString().toDouble())
                                    navigate(
                                        SwapFragmentDirections.actionSwapFragmentToSwapLoadingFragment(
                                            binding.topCoinAmountEt.text.toString().toFloat(),
                                            password,
                                            viewModel.swapTypeState.value
                                        )
                                    )
                                }
                            }.show(childFragmentManager, "AddFragment")
                        }
                    }
                }
            }
          launch {
            viewModel.walletExistsState.collectLatest {
                    if (it) {
                        viewModel.initWalletAmount()
                    } else {
                        toastMessage("지갑을 생성해야 합니다!")
                        navigate(SwapFragmentDirections.actionSwapFragmentToEmailFragment())
                    }
                }
            }

            launch {
                viewModel.amountInputState.collectLatest {
                    if(it == Int.MAX_VALUE.toString()){
                        val imm1 = requireContext().getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm1.hideSoftInputFromWindow(binding.topCoinAmountEt.windowToken, 0);

                        val imm2 = requireContext().getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm2.hideSoftInputFromWindow(binding.bottomCoinAmountTv.windowToken, 0);

                        Toast.makeText(requireContext(),"최대 ${Int.MAX_VALUE}까지 입력가능합니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {

    }
}
