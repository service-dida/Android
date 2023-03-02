package com.dida.android.presentation.views

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dida.android.R
import com.dida.recent_nft.RecentNftNavigationAction
import com.dida.recent_nft.RecentNftViewModel
import com.dida.recent_nft.adapter.CardPagingAdapter
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
    private val cardPagingAdapter by lazy { CardPagingAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initAdapter()
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenStarted {
            viewModel.errorEvent.collectLatest {
                showToastMessage(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.navigationEvent.collectLatest {
                    when(it) {
                        is RecentNftNavigationAction.NavigateToRecentNftItem -> navigate(RecentNftFragmentDirections.actionRecentNftFragmentToDetailNftFragment(cardId = it.nftId.toLong()))
                        is RecentNftNavigationAction.NavigateToCardRefresh -> cardPagingAdapter.refresh()
                    }
                }
            }

            launch {
                viewModel.cardsState.collectLatest {
                    cardPagingAdapter.submitData(it)
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar(){
        binding.toolbar.apply {
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun initAdapter() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.recentNftRecycler.layoutManager = gridLayoutManager
        binding.recentNftRecycler.adapter = cardPagingAdapter
    }
}
