package com.dida.android.presentation.views

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dida.android.R
import com.dida.hot_user.HotUserViewModel
import com.dida.hot_user.databinding.FragmentHotUserBinding
import com.dida.recent_nft.RecentNftNavigationAction
import com.dida.recent_nft.RecentNftViewModel
import com.dida.recent_nft.adapter.CardPagingAdapter
import com.dida.recent_nft.databinding.FragmentRecentNftBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HotUserFragment : BaseFragment<FragmentHotUserBinding, HotUserViewModel>(com.dida.hot_user.R.layout.fragment_hot_user) {

    private val TAG = "HotUserFragment"

    override val layoutResourceId: Int
        get() = com.dida.hot_user.R.layout.fragment_hot_user // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : HotUserViewModel by viewModels()
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
        lifecycleScope.launchWhenStarted {
            viewModel.errorEvent.collectLatest {
                showToastMessage(it)
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar(){
        binding.toolbar.apply {
            this.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
