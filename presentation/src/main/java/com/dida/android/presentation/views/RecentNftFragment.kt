package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dida.common.util.addOnPagingListener
import com.dida.common.util.repeatOnStarted
import com.dida.recent_nft.RecentNftViewModel
import com.dida.recent_nft.adapter.RecentNftAdapter
import com.dida.recent_nft.databinding.FragmentRecentNftBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecentNftFragment : BaseFragment<FragmentRecentNftBinding, RecentNftViewModel>(com.dida.recent_nft.R.layout.fragment_recent_nft) {

    private val TAG = "RecentNftFragment"

    override val layoutResourceId: Int
        get() = com.dida.recent_nft.R.layout.fragment_recent_nft // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : RecentNftViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val recentNftAdapter by lazy { RecentNftAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        initToolbar()
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigateToNftDetail.collectLatest {
                navigate(RecentNftFragmentDirections.actionRecentNftFragmentToDetailNftFragment(cardId = it))
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.recentNftState.collectLatest {
                recentNftAdapter.submitList(it.content)
            }
        }
    }

    override fun initAfterBinding() {
        parentFragmentManager.addOnBackStackChangedListener {}
    }

    private fun initToolbar(){
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun initAdapter() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.recentNftRecycler.layoutManager = gridLayoutManager
        binding.recentNftRecycler.adapter = recentNftAdapter
        binding.recentNftRecycler.addOnPagingListener(
            arrivedBottom = { viewModel.nextPage() }
        )
    }
}
