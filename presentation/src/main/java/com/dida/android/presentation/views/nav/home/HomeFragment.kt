package com.dida.android.presentation.views.nav.home

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dida.android.R
import com.dida.android.databinding.FragmentHomeBinding
import com.dida.android.presentation.adapter.home.*
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.util.*
import com.dida.domain.model.nav.home.Hots
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    private val TAG = "HomeFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    override val viewModel : HomeViewModel by viewModels()
    lateinit var navController: NavController

    private val hotsAdapter: HotsAdapter = HotsAdapter()
    private val hotSellerAdapter: HotSellerAdapter = HotSellerAdapter()
    private val soldOutAdapter: SoldOutAdapter = SoldOutAdapter()
    private val collectionAdapter: CollectionAdapter = CollectionAdapter()

    private val recentNftAdapter: RecentNftAdapter = RecentNftAdapter(
        clickUnit = { nftId ->
            showDetailPage(nftId)
        }
    )

    override fun initStartView() {
        binding.vm = viewModel
        navController = Navigation.findNavController(requireView())
        initToolbar()
        initAdapter()

        // main 화면 불러오는 함수
        viewModel.getMain()
        viewModel.getSoldOut(7)
        startShimmerHome()
    }

    override fun initDataBinding() {
        viewModel.mainLiveData.observe(this) {
            hotsAdapter.addAll(it.getHotItems)
            hotSellerAdapter.addAll(it.getHotSellers)
            recentNftAdapter.addAll(it.getRecentCards)
            collectionAdapter.addAll(it.getHotUsers)
            stopShimmerHome()
        }

        viewModel.soldoutLiveData.observe(this) {
            soldOutAdapter.addAll(it)
        }

        viewModel.errorLiveData.observe(this) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show().let {
                viewModel.getMain()
                viewModel.getSoldOut(7)
                startShimmerHome()
            }
        }
    }

    override fun initAfterBinding() {
        binding.day7Btn.setOnClickListener(termClickListener)
        binding.day30Btn.setOnClickListener(termClickListener)
        binding.day180Btn.setOnClickListener(termClickListener)
        binding.day365Btn.setOnClickListener(termClickListener)

        // move detail
        soldOutAdapter.nextItemClickListener(object : SoldOutAdapter.OnItemClickEventListener {
            override fun onItemClick(a_position: Int) {
                navController.navigate(R.id.action_homeFragment_to_detailNftFragment)
            }
        })

        binding.homeScroll.setOnScrollChangeListener { v, _, scrollY, _, _ ->
            // soldout
            if(binding.hotSellerRecycler.y+binding.hotSellerRecycler.height<= scrollY &&
                    scrollY < binding.soldoutMore.y) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(1))
            }
            // recent
            else if(binding.soldoutMore.y <= scrollY &&
                scrollY < binding.recentnftRecycler.y+100) {
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

    private fun initAdapter() {
        binding.hotitemTxt.text = "HOT Item \uD83D\uDD25"
        binding.hotsRecycler.run {
            adapter = hotsAdapter
            setHasFixedSize(true)
        }

        binding.hotSellerRecycler.run {
            adapter = hotSellerAdapter
            setHasFixedSize(true)
        }

        binding.soldoutTxt.text = "Sold Out \uD83D\uDC8E"
        binding.soldoutRecycler.run {
            adapter = soldOutAdapter
            setHasFixedSize(true)
        }

        binding.recentnftTxt.text = "최신 NFT \uD83C\uDF40"
        binding.recentnftRecycler.apply {
            adapter = recentNftAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
            val px = ConvertDpToPx().convertDPtoPX(requireContext(),14)
            addItemDecoration(GridSpacing(px, px))
        }

        binding.collectionTxt.text = "활발한 활동 \uD83C\uDF55"
        binding.collectionRecycler.run {
            adapter = collectionAdapter
            setHasFixedSize(true)
        }

        with(binding.tabLayout) {
            addTab(this.newTab().setText("Hot Seller"))
            addTab(this.newTab().setText("Sold Out"))
            addTab(this.newTab().setText("최신 NFT"))
            addTab(this.newTab().setText("활동"))

            for (i in 0 until (this.getChildAt(0) as LinearLayout).childCount) {
                (this.getChildAt(0) as LinearLayout).getChildAt(i).setOnTouchListener { _, _ ->
                    moveScroll(i)
                    true }
            }
        }
    }

    private fun initToolbar() {
        binding.homeToolbar.inflateMenu(R.menu.menu_home_toolbar)
        binding.homeToolbar.title = "DIDA"
        binding.homeToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_alarm -> {
                }
                R.id.action_search -> {
                }
            }
            true
        }
    }

    private fun showDetailPage(nftId : Int){
        findNavController().navigate(R.id.action_homeFragment_to_detailNftFragment)
    }

    private fun moveScroll(tabId: Int) {
        with(binding.homeScroll) {
            when(tabId) {
                0 -> {
                    this.smoothScrollToView(binding.hotSellerRecycler, 100, 1800)
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0))
                }
                1 -> {
                    this.smoothScrollToView(binding.soldoutTxt, 50, 1800)
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(1))
                }
                2 -> {
                    this.smoothScrollToView(binding.recentnftTxt, 50, 1800)
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(2))
                }
                3 -> {
                    this.smoothScrollToView(binding.collectionTxt, 0, 1800)
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(3))
                }
            }
            binding.appBarLayout.setExpanded(false)
        }

    }

    private fun NestedScrollView.computeDistanceToView(view: View): Int {
        return abs(calculateRectOnScreen(this).top - (this.scrollY + calculateRectOnScreen(view).top))
    }

    private fun calculateRectOnScreen(view: View): Rect {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        return Rect(
            location[0],
            location[1],
            location[0] + view.measuredWidth,
            location[1] + view.measuredHeight
        )
    }

    private fun NestedScrollView.smoothScrollToView(
        view: View,
        marginTop: Int = 0,
        maxDuration: Long = 500L,
        onEnd: () -> Unit = {}
    ) {
        if (this.getChildAt(0).height <= this.height) { // 스크롤의 의미가 없다.
            onEnd()
            return
        }
        val y = computeDistanceToView(view) - marginTop
        val ratio = abs(y - this.scrollY) / (this.getChildAt(0).height - this.height).toFloat()
        ObjectAnimator.ofInt(this, "scrollY", y).apply {
            duration = (maxDuration * ratio).toLong()
            interpolator = AccelerateDecelerateInterpolator()
            doOnEnd {
                onEnd()
            }
            start()
        }
    }

    private val termClickListener: View.OnClickListener = View.OnClickListener {
        when (it.id) {
            R.id.day7_btn -> {
                viewModel.getSoldOut(7)
            }
            R.id.day30_btn -> {
                viewModel.getSoldOut(30)
            }
            R.id.day180_btn -> {
                viewModel.getSoldOut(60)
            }
            R.id.day365_btn -> {
                viewModel.getSoldOut(365)
            }
        }
    }

    private fun startShimmerHome() {
        startShimmer(binding.hotsShimmer, binding.hotsRecycler)
        startShimmer(binding.hotsSellerShimmer, binding.hotSellerRecycler)
        startShimmer(binding.soldoutShimmer, binding.soldoutRecycler)
        startShimmer(binding.collectionShimmer, binding.collectionRecycler)
        startShimmer(binding.recentnftShimmer, binding.recentnftRecycler)
    }

    private fun stopShimmerHome() {
        stopShimmer(binding.hotsShimmer, binding.hotsRecycler)
        stopShimmer(binding.hotsSellerShimmer, binding.hotSellerRecycler)
        stopShimmer(binding.soldoutShimmer, binding.soldoutRecycler)
        stopShimmer(binding.collectionShimmer, binding.collectionRecycler)
        stopShimmer(binding.recentnftShimmer, binding.recentnftRecycler)
    }
}
