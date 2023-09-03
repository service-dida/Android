package com.dida.android.presentation.views

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.add.R
import com.dida.add.databinding.FragmentAddBinding
import com.dida.add.main.AddViewModel
import com.dida.password.PasswordDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddFragment : BaseFragment<FragmentAddBinding, AddViewModel>(R.layout.fragment_add) {

    private val TAG = "AddFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_add

    override val viewModel: AddViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getWalletExists()
    }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.walletExistsState.collectLatest { existed ->
                    if (existed) {
                        PasswordDialog(6, "비밀번호 입력", "6자리를 입력해주세요.") { success, msg ->
                            if (!success) {
                                if (msg == "reset") navigate(AddFragmentDirections.actionAddFragmentToSettingFragment())
                                else navController.popBackStack()
                            }
                        }.show(childFragmentManager, "AddFragment")
                    } else {
                        showToastMessage("지갑을 생성해야 합니다!")
                        navigate(AddFragmentDirections.actionAddFragmentToEmailFragment(RequestEmailType.MAKE_WALLET))
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {
        binding.aiMode.setOnClickListener {
            navigate(AddFragmentDirections.actionAddFragmentToKeywordProductFragment())
        }

        binding.galleryMode.setOnClickListener {
            navigate(AddFragmentDirections.actionAddFragmentToCreateNftFragment())
        }
    }


    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
