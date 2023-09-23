package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import com.dida.android.R
import com.dida.common.widget.DefaultSnackBar
import com.dida.compose.theme.BrandLemon
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.MainBlack
import com.dida.compose.theme.Surface6
import com.dida.compose.theme.TextGray
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp
import com.dida.compose.utils.Divider12
import com.dida.compose.utils.Divider8
import com.dida.compose.utils.NoRippleInteractionSource
import com.dida.compose.utils.clickableSingle
import com.dida.domain.main.model.CommonFollow
import com.dida.domain.main.model.Follow
import com.dida.user_followed.UserFollowedMessageAction
import com.dida.user_followed.UserFollowedViewModel
import com.dida.user_followed.databinding.FragmentUserfollowedBinding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFollowedFragment :
    BaseFragment<FragmentUserfollowedBinding, UserFollowedViewModel>(com.dida.user_followed.R.layout.fragment_userfollowed) {

    private val TAG = "UserFollowedFragment"

    override val layoutResourceId: Int
        get() = com.dida.user_followed.R.layout.fragment_userfollowed // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: UserFollowedViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }
    private val args: UserFollowedFragmentArgs by navArgs()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.messageEvent.collectLatest {
                when(it) {
                    is UserFollowedMessageAction.UserFollowMessage -> showMessageSnackBar(String.format(getString(R.string.user_follow_message), it.nickname))
                    is UserFollowedMessageAction.UserUnFollowMessage -> showMessageSnackBar(getString(R.string.user_unfollow_message))
                }
            }
        }
    }

    override fun initAfterBinding() {}

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val tabs = listOf(Follow.FOLLOWER, Follow.FOLLOWING)
                val pagerState = rememberPagerState(pageCount = tabs.size)
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(key1 = Unit) {
                    when (args.type) {
                        Follow.FOLLOWER -> coroutineScope.launch { pagerState.animateScrollToPage(0) }
                        Follow.FOLLOWING -> coroutineScope.launch { pagerState.animateScrollToPage(1) }
                    }
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Tabs(tabs = tabs, pagerState = pagerState, coroutineScope = coroutineScope)
                    TabContents(tabs = tabs, pagerState = pagerState)
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun Tabs(
        tabs: List<Follow>,
        pagerState: PagerState,
        coroutineScope: CoroutineScope
    ) {
        TabRow(
            backgroundColor = MainBlack,
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    height = 3.dp,
                    color = BrandLemon
                )
            }
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = tab.str) },
                    selectedContentColor = BrandLemon,
                    unselectedContentColor = Surface6,
                    interactionSource = NoRippleInteractionSource()
                )
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun TabContents(
        tabs: List<Follow>,
        pagerState: PagerState
    ) {
        val following = viewModel.followingState.collectAsStateWithLifecycle()
        val follower = viewModel.followerState.collectAsStateWithLifecycle()

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState
        ) { index ->
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                when (tabs[index]) {
                    Follow.FOLLOWER -> FollowerScreen(items = follower.value.content)
                    Follow.FOLLOWING -> FollowingScreen(items = following.value.content)
                }
            }
        }
    }

    @Composable
    fun FollowerScreen(
        items: List<CommonFollow>
    ) {
        val listState = rememberLazyListState()
        val nextPage = remember {
            derivedStateOf { listState.firstVisibleItemIndex == (items.size - 10) }
        }

        LaunchedEffect(key1 = nextPage.value) {
            if (nextPage.value) viewModel.onNextPageFromFollower()
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            state = listState
        ) {
            items(items.size) {
                FollowerItem(
                    item = items[it],
                    onUserClicked = { navigate(UserFollowedFragmentDirections.actionUserFollowedFragmentToUserProfileFragment(items[it].memberId)) },
                    onFollowButtonClicked = { viewModel.onFollowButtonClicked(items[it]) }
                )
            }
        }
    }

    @Composable
    fun FollowingScreen(
        items: List<CommonFollow>
    ) {
        val listState = rememberLazyListState()
        val nextPage = remember {
            derivedStateOf { listState.firstVisibleItemIndex == (items.size - 10) }
        }

        LaunchedEffect(key1 = nextPage.value) {
            if (nextPage.value) viewModel.onNextPageFromFollowing()
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items.size) {
                FollowingItem(
                    item = items[it],
                    onUserClicked = { navigate(UserFollowedFragmentDirections.actionUserFollowedFragmentToUserProfileFragment(items[it].memberId)) },
                    onFollowButtonClicked = { viewModel.onFollowButtonClicked(items[it]) }
                )
            }
        }
    }
    
    @Composable
    fun FollowingItem(
        item: CommonFollow,
        onUserClicked: () -> Unit,
        onFollowButtonClicked: () -> Unit
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableSingle { onUserClicked() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier.size(62.dp),
                    model = item.profileImgUrl,
                    contentDescription = "유저 이미지"
                )
                Divider12()
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        style = DidaTypography.h4,
                        color = White,
                        fontSize = dpToSp(dp = 20.dp),
                        text = item.nickname,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Divider8()
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        style = DidaTypography.body1,
                        color = TextGray,
                        fontSize = dpToSp(dp = 14.dp),
                        text = "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Divider12()
                    CancelFollowButton(onFollowButtonClicked = onFollowButtonClicked)
                }
            }
        }
    }

    @Composable
    fun FollowerItem(
        item: CommonFollow,
        onUserClicked: () -> Unit,
        onFollowButtonClicked: () -> Unit
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableSingle { onUserClicked() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier.size(62.dp),
                    model = item.profileImgUrl,
                    contentDescription = "유저 이미지"
                )
                Divider12()
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        style = DidaTypography.h4,
                        color = White,
                        fontSize = dpToSp(dp = 20.dp),
                        text = item.nickname,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Divider8()
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        style = DidaTypography.body1,
                        color = TextGray,
                        fontSize = dpToSp(dp = 14.dp),
                        text = "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Divider12()
                    FollowButton(onFollowButtonClicked = onFollowButtonClicked)
                }
            }
        }
    }

    @Composable
    fun FollowButton(
        onFollowButtonClicked: () -> Unit
    ) {
        Surface(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clickableSingle { onFollowButtonClicked() }
                .border(
                    width = 1.dp,
                    color = BrandLemon,
                    shape = RoundedCornerShape(size = 100.dp)
                )
        ) {
            Text(
                modifier = Modifier,
                style = DidaTypography.body1,
                color = White,
                fontSize = dpToSp(dp = 12.dp),
                text = "팔로우",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    @Composable
    fun CancelFollowButton(
        onFollowButtonClicked: () -> Unit
    ) {
        Surface(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clickableSingle { onFollowButtonClicked() },
            shape = RoundedCornerShape(100.dp),
            color = BrandLemon
        ) {
            Text(
                modifier = Modifier,
                style = DidaTypography.body1,
                color = MainBlack,
                fontSize = dpToSp(dp = 12.dp),
                text = "팔로잉",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    private fun showMessageSnackBar(message: String) {
        DefaultSnackBar.Builder()
            .view(binding.root)
            .message(message)
            .build()
    }
}
