package com.dida.android.presentation.views.nav.home

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import androidx.core.animation.doOnEnd
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dida.android.R
import com.dida.android.databinding.FragmentHomeBinding
import com.dida.android.presentation.adapter.home.CollectionAdapter
import com.dida.android.presentation.adapter.home.HotSellerAdapter
import com.dida.android.presentation.adapter.home.HotsAdapter
import com.dida.android.presentation.adapter.home.SoldOutAdapter
import com.dida.android.presentation.adapter.mypage.MyPageUserCardsRecyclerViewAdapter
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.util.ConvertDpToPx
import com.dida.android.util.GridSpacing
import com.dida.domain.model.nav.home.Collection
import com.dida.domain.model.nav.home.HotSeller
import com.dida.domain.model.nav.home.Hots
import com.dida.domain.model.nav.home.SoldOut
import com.dida.domain.model.nav.mypage.UserCardsResponseModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
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


    override fun initStartView() {
        binding.vm = viewModel
        navController = Navigation.findNavController(requireView())
        initToolbar()

        binding.hotsRecycler.run {
            adapter = hotsAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            setHasFixedSize(true)
        }

        binding.hotSellerRecycler.run {
            adapter = hotSellerAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            setHasFixedSize(true)
        }

        binding.soldoutRecycler.run {
            adapter = soldOutAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }

        val list = mutableListOf(
            UserCardsResponseModel(0,"user name here","NFT name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2",1.65),
            UserCardsResponseModel(1,"user name here","NFT name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2",1.65),
            UserCardsResponseModel(2,"user name here","NFT name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2",1.65),
            UserCardsResponseModel(3,"user name here","NFT name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2",1.65),
        )

        binding.recentnftRecycler.apply {
            adapter = MyPageUserCardsRecyclerViewAdapter(list,::showDetailPage)
            layoutManager = GridLayoutManager(requireContext(),2)
            val px = ConvertDpToPx().convertDPtoPX(requireContext(),14)
            addItemDecoration(GridSpacing(px, px))
        }

        binding.collectionRecycler.run {
            adapter = collectionAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }

        val tabLayout: TabLayout = binding.tabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Hot Seller"))
        tabLayout.addTab(tabLayout.newTab().setText("Sold Out"))
        tabLayout.addTab(tabLayout.newTab().setText("최신 NFT"))
        tabLayout.addTab(tabLayout.newTab().setText("컬렉션"))

        val tabStrip = binding.tabLayout.getChildAt(0) as LinearLayout
        for (i in 0 until tabStrip.childCount) {
            tabStrip.getChildAt(i).setOnTouchListener { _, _ ->
                moveScroll(i)
                true }
        }

        binding.soldoutTxt.text = "Sold Out \uD83D\uDC8E"
        binding.hotitemTxt.text = "HOT Item \uD83D\uDD25"
        binding.recentnftTxt.text = "최신 NFT \uD83C\uDF40"
        binding.collectionTxt.text = "활발한 활동 \uD83C\uDF55"
    }

    override fun initDataBinding() {
        // test
        val item = Hots("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT name here", 3.2, 1.65)
        for(i in 0..5){
            hotsAdapter.addItem(item)
        }
        hotsAdapter.notifyDataSetChanged()

        val item2 = HotSeller("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "name here")
        for(i in 0..5){
            hotSellerAdapter.addItem(item2)
        }
        hotSellerAdapter.notifyDataSetChanged()

        val item3 = SoldOut("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT name here", "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "user name here", 325.91)
        for(i in 0..3){
            soldOutAdapter.addItem(item3)
        }
        soldOutAdapter.notifyDataSetChanged()

        val item4 = Collection("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "user name here", "12 작품", false)
        val item5 = Collection("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "user name here", "12 작품", true)
        val item6 = Collection("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "user name here", "12 작품", false)
        collectionAdapter.addItem(item4)
        collectionAdapter.addItem(item5)
        collectionAdapter.addItem(item6)
        collectionAdapter.notifyDataSetChanged()
    }

    override fun initAfterBinding() {
        // move detail
        soldOutAdapter.nextItemClickListener(object : SoldOutAdapter.OnItemClickEventListener {
            override fun onItemClick(a_position: Int) {
                navController.navigate(R.id.action_homeFragment_to_detailNftFragment)
            }
        })

        binding.homeScroll.setOnScrollChangeListener { v, _, scrollY, _, _ ->
            Log.d("scroll_response", scrollY.toString())
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

    private fun showDetailPage(nftId : Long){
        findNavController().navigate(R.id.action_homeFragment_to_detailNftFragment)
    }

    private fun moveScroll(tabId: Int) {
        with(binding.homeScroll) {
            when(tabId) {
                0 -> {
//                    binding.homeScroll.smoothScrollToView(binding.hotSellerRecycler.top - 100, 3000)
                    binding.homeScroll.smoothScrollToView(binding.hotSellerRecycler, 100, 1800)
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0))
                }
                1 -> {
                    binding.homeScroll.smoothScrollToView(binding.soldoutTxt, 50, 1800)
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(1))
                }
                2 -> {
                    binding.homeScroll.smoothScrollToView(binding.recentnftTxt, 50, 1800)
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(2))
                }
                3 -> {
                    binding.homeScroll.smoothScrollToView(binding.collectionTxt, 0, 1800)
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
}
