package com.dida.android.presentation.views

import android.annotation.SuppressLint
import android.widget.LinearLayout
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.dida.android.R
import com.dida.android.util.permission.PermissionManagerImpl
import com.dida.android.util.permission.PermissionRequester
import com.dida.android.util.permission.Permissions
import com.dida.common.util.*
import com.dida.common.widget.DefaultSnackBar
import com.dida.domain.main.model.HotItems
import com.dida.home.HomeMessageAction
import com.dida.home.HomeNavigationAction
import com.dida.home.HomeViewModel
import com.dida.home.adapter.*
import com.dida.home.databinding.FragmentHomeBinding
import com.dida.home.smoothScrollToView
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Objects


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(com.dida.home.R.layout.fragment_home),
    AppBarLayout.OnOffsetChangedListener {

    private val TAG = "HomeFragment"

    override val layoutResourceId: Int
        get() = com.dida.home.R.layout.fragment_home

    override val viewModel: HomeViewModel by viewModels()
    private val hotsContainerAdapter by lazy { HotsContainerAdapter(viewModel) }

    private val permissionManager = PermissionManagerImpl(this)
    private val notificationPermissionRequest: PermissionRequester =
        permissionManager.forPermission(Permissions.PostNotification)
            .subscribe(this)

    private lateinit var hotSellerConcatAdapter: ConcatAdapter
    private lateinit var collectionConcatAdapter: ConcatAdapter

    private val hotSellerAdapter by lazy { HotSellerAdapter(viewModel) }
    private val hotSellerMoreAdapter by lazy { HotSellerMoreAdapter(viewModel) }
    private val homeEmptyAdapter by lazy { HomeEmptyAdapter(viewModel) }
    private val collectionAdapter by lazy { CollectionAdapter(viewModel) }
    private val collectionMoreAdapter by lazy { CollectionMoreAdapter(viewModel) }
    private val collectionEmptyAdapter by lazy { HomeEmptyAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initAdapter()
        initSwipeRefresh()
        initNotificationPermission()
        initScrollListener()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.navigationEvent.collectLatest {
                    when (it) {
                        is HomeNavigationAction.NavigateToHotItem -> navigate(HomeFragmentDirections.actionHomeFragmentToDetailNftFragment(it.cardId))
                        is HomeNavigationAction.NavigateToHotSeller -> navigate(HomeFragmentDirections.actionHomeFragmentToUserProfileFragment(it.userId))
                        is HomeNavigationAction.NavigateToSoldOut -> navigate(HomeFragmentDirections.actionHomeFragmentToDetailNftFragment(it.cardId))
                        is HomeNavigationAction.NavigateToRecentNftItem -> navigate(HomeFragmentDirections.actionHomeFragmentToDetailNftFragment(it.nftId))
                        is HomeNavigationAction.NavigateToCollection -> navigate(HomeFragmentDirections.actionHomeFragmentToUserProfileFragment(it.userId))
                        is HomeNavigationAction.NavigateToHotSellerMore -> navigate(HomeFragmentDirections.actionHomeFragmentToHotSellerFragment())
                        is HomeNavigationAction.NavigateToSoldOutMore -> navigate(HomeFragmentDirections.actionHomeFragmentToSoldOutFragment(viewModel.termState.value))
                        is HomeNavigationAction.NavigateToRecentNftMore -> navigate(HomeFragmentDirections.actionHomeFragmentToRecentNftFragment())
                        is HomeNavigationAction.NavigateToCollectionMore -> navigate(HomeFragmentDirections.actionHomeFragmentToHotUserFragment())
                        is HomeNavigationAction.NavigateToCreateCard -> navigate(HomeFragmentDirections.actionAddFragment())
                    }
                }
            }

            launch {
                viewModel.messageEvent.collectLatest {
                    when(it) {
                        is HomeMessageAction.UserFollowMessage -> showMessageSnackBar(String.format(getString(R.string.user_follow_message), it.nickname))
                        is HomeMessageAction.UserUnFollowMessage -> showMessageSnackBar(getString(R.string.user_unfollow_message))
                        is HomeMessageAction.AddCardBookmarkMessage -> {
                            showActionSnackBar(
                                message = getString(R.string.add_bookmark_message),
                                label = getString(R.string.add_bookmark_action_title_message),
                                onClickListener = object : DefaultSnackBar.OnClickListener {
                                    override fun onClick() {}
                                }
                            )
                        }
                        is HomeMessageAction.DeleteCardBookmarkMessage -> showMessageSnackBar(getString(R.string.delete_bookmark_message))
                    }

                }
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.homeState.collectLatest {
                it.successOrNull()?.let { home ->
                    val item = if (home.getHotItems.isNotEmpty()) listOf(HotItems.Contents(home.getHotItems)) else emptyList()
                    hotsContainerAdapter.submitList(item)

                    if (home.getHotSellers.isNotEmpty()) {
                        hotSellerAdapter.submitList(home.getHotSellers)
                        hotSellerMoreAdapter.submitList(listOf(HotSellerMoreItem(0)))
                    } else {
                        homeEmptyAdapter.submitList(listOf(HomeEmptyItem(0)))
                    }

                    if (home.getHotMembers.isNotEmpty()) {
                        collectionAdapter.submitList(home.getHotMembers)
                        collectionMoreAdapter.submitList(listOf(CollectionMoreItem(0)))
                    } else {
                        collectionEmptyAdapter.submitList(listOf(HomeEmptyItem(0)))
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {}

    override fun onResume() {
        super.onResume()
        binding.appBarLayout.addOnOffsetChangedListener(this)
        viewModel.getHome()
    }

    override fun onPause() {
        super.onPause()
        binding.appBarLayout.removeOnOffsetChangedListener(this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val isExpanded = verticalOffset == 0
        if (isExpanded != binding.swipeRefreshLayout.isEnabled) {
            binding.swipeRefreshLayout.isEnabled = isExpanded
        }
    }

    private fun initSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getHome()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initScrollListener() {
        binding.homeScroll.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (binding.hotSellerRecycler.y + binding.hotSellerRecycler.height <= scrollY && scrollY < binding.soldoutMore.y) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(1))
            } else if (binding.soldoutMore.y <= scrollY && scrollY < binding.recentnftRecycler.y + 100) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(2))
            } else if (binding.recentnftRecycler.y + 100 <= scrollY) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(3))
            } else {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0))
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initAdapter() {
        val adapterConfig = ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build()

        hotSellerConcatAdapter = ConcatAdapter(
            adapterConfig,
            hotSellerAdapter,
            hotSellerMoreAdapter,
            homeEmptyAdapter
        )

        collectionConcatAdapter = ConcatAdapter(
            adapterConfig,
            collectionAdapter,
            collectionMoreAdapter,
            collectionEmptyAdapter
        )

        binding.hotSellerRecycler.adapter = hotSellerConcatAdapter
        binding.hotsRecycler.adapter = hotsContainerAdapter
        binding.soldoutRecycler.adapter = SoldOutAdapter(viewModel)
        binding.collectionRecycler.adapter = collectionConcatAdapter

        binding.recentnftRecycler.apply {
            adapter = HomeRecentNftAdapter(viewModel)
            layoutManager = GridLayoutManager(context, 2)
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
                com.dida.common.R.id.action_alarm -> {}
            }
            true
        }
    }

    private fun moveScroll(tabId: Int) {
        with(binding.homeScroll) {
            when (tabId) {
                0 -> smoothScrollToView(binding.hotSellerRecycler, 100, 750L)
                1 -> smoothScrollToView(binding.soldoutTxt, 50, 750L)
                2 -> smoothScrollToView(binding.recentnftTxt, 50, 750L)
                3 -> smoothScrollToView(binding.collectionTxt, 0, 750L)
            }
            binding.appBarLayout.setExpanded(false)
        }
    }

    private fun initNotificationPermission() {
        if (NotificationManagerCompat.from(requireContext()).areNotificationsEnabled().not()) {
            notificationPermissionRequest.request()
        }
    }

    private fun showMessageSnackBar(message: String) {
        DefaultSnackBar.Builder()
            .view(binding.root)
            .message(message)
            .build()
    }

    private fun showActionSnackBar(message: String, label: String, onClickListener: DefaultSnackBar.OnClickListener) {
        DefaultSnackBar.Builder()
            .view(binding.root)
            .message(message)
            .actionButton(label, onClickListener)
            .build()
    }
}
