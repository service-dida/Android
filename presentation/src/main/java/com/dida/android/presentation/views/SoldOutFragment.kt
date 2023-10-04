package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.soldout.SoldOutViewModel
import com.dida.soldout.databinding.FragmentSoldOutBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SoldOutFragment : BaseFragment<FragmentSoldOutBinding, SoldOutViewModel>(com.dida.soldout.R.layout.fragment_sold_out) {

    private val TAG = "SoldOutFragment"

    override val layoutResourceId: Int
        get() = com.dida.soldout.R.layout.fragment_sold_out

    override val viewModel: SoldOutViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {}
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
