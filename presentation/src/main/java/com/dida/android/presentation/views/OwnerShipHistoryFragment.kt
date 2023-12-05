package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dida.ownership_history.R
import com.dida.ownership_history.OwnerShipHistoryViewModel
import com.dida.ownership_history.databinding.FragmentOwnershipHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OwnerShipHistoryFragment : BaseFragment<FragmentOwnershipHistoryBinding, OwnerShipHistoryViewModel>(
    R.layout.fragment_ownership_history) {

    private val TAG = "OwnerShipHistoryFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_ownership_history // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : OwnerShipHistoryViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        initToolbar()
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
            }
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setTitleTextColor(ContextCompat.getColor(requireContext(), com.dida.common.R.color.white))
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
