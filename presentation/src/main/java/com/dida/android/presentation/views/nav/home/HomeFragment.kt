package com.dida.android.presentation.views.nav.home

import android.annotation.SuppressLint
import android.os.Build
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dida.android.R
import com.dida.android.databinding.FragmentHomeBinding
import com.dida.android.presentation.adapter.home.*
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    private val TAG = "HomeFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    override val viewModel : HomeViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.vm = viewModel
        initToolbar()
        initAdapter()

        // main 화면 불러오는 함수
        viewModel.getMain()

        // test
        navController.navigate(R.id.action_homeFragment_to_emailFragment)
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collect {
                when(it) {
                    is HomeNavigationAction.NavigateToHotItem -> { checkNavigationDesination(R.id.action_homeFragment_to_detailNftFragment) }
                    is HomeNavigationAction.NavigateToHotSeller -> { }
                    is HomeNavigationAction.NavigateToSoldOut -> { checkNavigationDesination(R.id.action_homeFragment_to_detailNftFragment) }
                    is HomeNavigationAction.NavigateToRecentNftItem -> { checkNavigationDesination(R.id.action_homeFragment_to_detailNftFragment) }
                    is HomeNavigationAction.NavigateToCollection -> {}
                }
            }
        }
    }

    override fun initAfterBinding() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.homeScroll.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                // soldout
                if(binding.hotSellerRecycler.y+binding.hotSellerRecycler.height<= scrollY && scrollY < binding.soldoutMore.y) {
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(1))
                }
                // recent
                else if(binding.soldoutMore.y <= scrollY && scrollY < binding.recentnftRecycler.y+100) {
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(2))
                }
                // collection
                else if(binding.recentnftRecycler.y+100 <= scrollY) {
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(3))
                }
                // hot seller
                else{
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0))
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initAdapter() {
        binding.hotsRecycler.adapter = HotsAdapter(viewModel)
        binding.hotSellerRecycler.adapter = HotSellerAdapter(viewModel)
        binding.soldoutRecycler.adapter = SoldOutAdapter(viewModel)
        binding.collectionRecycler.adapter = CollectionAdapter(viewModel)

        binding.recentnftRecycler.apply {
            adapter = RecentNftAdapter(viewModel)
            layoutManager = GridLayoutManager(requireContext(),2)
            val px = ConvertDpToPx().convertDPtoPX(requireContext(),14)
            addItemDecoration(GridSpacing(px, px))
        }

        with(binding.tabLayout) {
            addTab(this.newTab().setText(R.string.home_hotitem_tab))
            addTab(this.newTab().setText(R.string.home_soldout_tab))
            addTab(this.newTab().setText(R.string.home_recentnft_tab))
            addTab(this.newTab().setText(R.string.home_collection_tab))

            for (i in 0 until (this.getChildAt(0) as LinearLayout).childCount) {
                (this.getChildAt(0) as LinearLayout).getChildAt(i).setOnTouchListener { _, _ ->
                    moveScroll(i)
                    true
                }
            }
        }
    }

    private fun initToolbar() {
        binding.homeToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_alarm -> {}
                R.id.action_search -> {}
            }
            true
        }
    }

    private fun checkNavigationDesination(toNav: Any) {
        if (navController.currentDestination?.id == R.id.homeFragment) {
            when(toNav) {
                is NavDirections -> navController.navigate(toNav)
                is Int -> navController.navigate(toNav)
            }
        }
    }

    private fun moveScroll(tabId: Int) {
        with(binding.homeScroll) {
            when(tabId) {
                0 -> { smoothScrollToView(binding.hotSellerRecycler, 100, 1000) }
                1 -> { smoothScrollToView(binding.soldoutTxt, 50, 1000) }
                2 -> { smoothScrollToView(binding.recentnftTxt, 50, 1000) }
                3 -> { smoothScrollToView(binding.collectionTxt, 0, 1000) }
            }
            binding.appBarLayout.setExpanded(false)
        }
    }
}
