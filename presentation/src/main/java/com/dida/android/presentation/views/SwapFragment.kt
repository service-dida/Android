package com.dida.android.presentation.views

import android.app.Service
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.common.util.Constants
import com.dida.common.util.performHapticEvent
import com.dida.common.util.removeTrailingDot
import com.dida.common.util.repeatOnResumed
import com.dida.common.util.repeatOnStarted
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

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        initSwipeRefresh()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is SwapNavigationAction.NavigateToPassword -> {
                        PasswordDialog(6, "비밀번호 입력", "6자리를 입력해주세요.") { success, password ->
                            if (success) {
                                navigate(
                                    SwapFragmentDirections.actionSwapFragmentToSwapLoadingFragment(
                                        removeTrailingDot(binding.topCoinAmountEt.text.toString()).toFloat(),
                                        password,
                                        viewModel.swapTypeState.value
                                    )
                                )
                            } else {
                                if (password == "reset") {
                                    navigate(SwapFragmentDirections.actionSwapFragmentToSettingFragment())
                                }
                            }
                        }.show(childFragmentManager, "AddFragment")
                    }
                }
            }
        }

        viewLifecycleOwner.repeatOnResumed {
            viewModel.walletExistsState.collectLatest {
                if (it) {
                    viewModel.initWalletAmount()
                } else {
                    showToastMessage("지갑을 생성해야 합니다!")
                    navigate(SwapFragmentDirections.actionSwapFragmentToEmailFragment(RequestEmailType.MAKE_WALLET))
                }
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.amountInputState.collectLatest {
                if (it == Constants.MAX_AMOUNT.toString()) {
                    val imm1 = requireContext().getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm1.hideSoftInputFromWindow(binding.topCoinAmountEt.windowToken, 0)
                    val imm2 = requireContext().getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm2.hideSoftInputFromWindow(binding.bottomCoinAmountTv.windowToken, 0)

                    Toast.makeText(requireContext(),"최대 ${Constants.MAX_AMOUNT_TEXT}까지 입력가능합니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun initAfterBinding() {}

    override fun onResume() {
        super.onResume()
        viewModel.getWalletExists()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    private fun initSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getWalletExists()
            requireView().performHapticEvent()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}
