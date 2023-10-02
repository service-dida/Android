package com.dida.android.presentation.views

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.android.presentation.adapter.CreateCommunityNftPagerAdapter
import com.dida.create_community.CreateCommunityNavigationAction
import com.dida.create_community.CreateCommunityViewModel
import com.dida.create_community.databinding.FragmentCreateCommunityBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateCommunityFragment : BaseFragment<FragmentCreateCommunityBinding, CreateCommunityViewModel>(com.dida.create_community.R.layout.fragment_create_community) {

    private val TAG = "CreateCommunityFragment"

    override val layoutResourceId: Int
        get() = com.dida.create_community.R.layout.fragment_create_community

    override val viewModel : CreateCommunityViewModel by viewModels()
    private val navController by lazy { findNavController() }

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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is CreateCommunityNavigationAction.NavigateToSelectNft ->
                        navigate(CreateCommunityFragmentDirections.actionCreateCommunityFragmentToCommunityCommunityInputFragment(it.cardId, true))
                    is CreateCommunityNavigationAction.NavigateToLike -> navigate(CreateCommunityFragmentDirections.actionCreateCommunityFragmentToRecentNftFragment())
                    is CreateCommunityNavigationAction.NavigateToCreate -> navigate(CreateCommunityFragmentDirections.actionCreateCommunityFragmentToAddFragment())
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initAdapter() {
        val tabTitleArray = arrayOf(getString(R.string.create_community_like_nft), getString(R.string.create_community_my_nft))

        val viewPager = binding.viewpagerNft
        val tabLayout = binding.tabNft

        viewPager.adapter = CreateCommunityNftPagerAdapter(this@CreateCommunityFragment, lifecycle)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
