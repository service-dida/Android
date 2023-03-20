package com.dida.android.presentation.views

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.hot_seller.HotSellerViewModel
import com.dida.hot_seller.databinding.FragmentHotSellerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HotSellerFragment : BaseFragment<FragmentHotSellerBinding, HotSellerViewModel>(com.dida.hot_seller.R.layout.fragment_hot_seller) {

    private val TAG = "HotSellerFragment"

    override val layoutResourceId: Int
        get() = com.dida.hot_seller.R.layout.fragment_hot_seller // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : HotSellerViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar(){
        binding.toolbar.apply {
            this.title = resources.getString(R.string.hot_seller)
            this.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
