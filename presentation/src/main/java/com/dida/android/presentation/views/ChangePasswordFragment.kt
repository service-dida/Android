package com.dida.android.presentation.views

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.change_password.ChangePasswordViewModel
import com.dida.change_password.databinding.FragmentChangePasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding, ChangePasswordViewModel>(com.dida.change_password.R.layout.fragment_change_password) {

    private val TAG = "ChangePasswordFragment"

    override val layoutResourceId: Int
        get() = com.dida.change_password.R.layout.fragment_change_password // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : ChangePasswordViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                navController.popBackStack()
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar(){
        binding.toolbar.apply {
            this.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
