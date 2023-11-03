package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.compose.theme.BrandLemon
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.MainBlack
import com.dida.compose.theme.Surface2
import com.dida.compose.theme.Surface6
import com.dida.compose.theme.TextGray
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp
import com.dida.compose.utils.DidaImage
import com.dida.compose.utils.HorizontalDivider
import com.dida.compose.utils.LineDivider
import com.dida.compose.utils.NoRippleInteractionSource
import com.dida.compose.utils.VerticalDivider
import com.dida.compose.utils.clickableSingle
import com.dida.create_community.CreateCommunityNavigationAction
import com.dida.create_community.CreateCommunityViewModel
import com.dida.create_community.databinding.FragmentCreateCommunityBinding
import com.dida.domain.main.model.CreateCommunityTabs
import com.dida.domain.main.model.OwnNft
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

    override fun initAfterBinding() {}

    private fun initAdapter() {}

    @OptIn(ExperimentalPagerApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val tabs = listOf(CreateCommunityTabs.LIKENFT, CreateCommunityTabs.OWNNFT)
                val pagerState = rememberPagerState(pageCount = tabs.size)
                val coroutineScope = rememberCoroutineScope()

                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp, horizontal = 16.dp),
                        text = getString(R.string.create_community_select_nft_title),
                        style = DidaTypography.h1,
                        fontSize = dpToSp(dp = 24.dp),
                        color = White
                    )
                    Tabs(tabs = tabs, pagerState = pagerState, coroutineScope = coroutineScope)
                    LineDivider(color = Surface2, height = 1)
                    TabContents(tabs = tabs, pagerState = pagerState)
                }
            }
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun Tabs(
        tabs: List<CreateCommunityTabs>,
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
        tabs: List<CreateCommunityTabs>,
        pagerState: PagerState
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState
        ) { index ->
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                when (tabs[index]) {
                    CreateCommunityTabs.LIKENFT -> LikeNftScreen()
                    CreateCommunityTabs.OWNNFT -> OwnNftScreen()
                }
            }
        }
    }

    @Composable
    fun LikeNftScreen() {
        val items by viewModel.likeNftState.collectAsStateWithLifecycle()
        val listState = rememberLazyListState()
        val nextPage by remember {
            derivedStateOf { listState.firstVisibleItemIndex == (items.content.size - 10) }
        }

        LaunchedEffect(key1 = nextPage) {
            if (nextPage) viewModel.onNextPageFromLikeNft()
        }

        if (items.content.isEmpty()) {
            EmptyLikeNft {
                viewModel.onLikeButtonClicked()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { VerticalDivider(dp = 19) }
                items(
                    count = items.content.size
                ) {
                    NftItem(
                        item = items.content[it],
                        onClicked = { viewModel.onNftSelectClicked(items.content[it].nftId) }
                    )
                }
                item { VerticalDivider(dp = 19) }
            }
        }
    }

    @Composable
    fun OwnNftScreen() {
        val items by viewModel.ownNftState.collectAsStateWithLifecycle()
        val listState = rememberLazyListState()
        val nextPage by remember {
            derivedStateOf { listState.firstVisibleItemIndex == (items.content.size - 10) }
        }

        LaunchedEffect(key1 = nextPage) {
            if (nextPage) viewModel.onNextPageFromOwnNft()
        }

        if (items.content.isEmpty()) {
            EmptyOwnNft {
                viewModel.onCreateButtonClicked()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { VerticalDivider(dp = 19) }
                items(
                    count = items.content.size
                ) {
                    NftItem(
                        item = items.content[it],
                        onClicked = { viewModel.onNftSelectClicked(items.content[it].nftId) }
                    )
                }
                item { VerticalDivider(dp = 19) }
            }
        }
    }

    @Composable
    fun NftItem(
        item: OwnNft,
        onClicked: () -> Unit
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = MainBlack
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DidaImage(
                    model = item.nftImgUrl,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(8.dp)),
                )
                HorizontalDivider(dp = 12)
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = item.nftName,
                        style = DidaTypography.h1,
                        fontSize = dpToSp(dp = 18.dp),
                        color = White
                    )
                    VerticalDivider(dp = 4)
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        DidaImage(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(RoundedCornerShape(100.dp)),
                            model = item.memberInfo.profileImgUrl
                        )
                        HorizontalDivider(dp = 8)
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = item.memberInfo.memberName,
                            style = DidaTypography.body1,
                            fontSize = dpToSp(dp = 14.dp),
                            color = TextGray
                        )
                    }

                }
                HorizontalDivider(dp = 12)
                Surface(
                    modifier = Modifier
                        .background(color = Surface2, shape = RoundedCornerShape(size = 8.dp))
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .clickableSingle { onClicked() },
                    color = Surface2
                ) {
                    Text(
                        text = getString(R.string.create_community_my_nft_select),
                        style = DidaTypography.body1,
                        fontSize = dpToSp(dp = 14.dp),
                        color = White
                    )
                }
            }
        }
    }

    @Composable
    fun EmptyLikeNft(
        onClicked: () -> Unit
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = getString(com.dida.create_community.R.string.create_community_like_empty_text),
                style = DidaTypography.h1,
                fontSize = dpToSp(dp = 20.dp),
                color = TextGray
            )
            VerticalDivider(dp = 24)
            Surface(
                modifier = Modifier
                    .background(color = BrandLemon, shape = RoundedCornerShape(size = 10.dp))
                    .padding(16.dp)
                    .clickableSingle { onClicked() },
                color = BrandLemon
            ) {
                Text(
                    text = getString(com.dida.create_community.R.string.create_community_empty_like_button_text),
                    style = DidaTypography.h1,
                    fontSize = dpToSp(dp = 16.dp),
                    color = MainBlack
                )
            }
        }
    }

    @Composable
    fun EmptyOwnNft(
        onClicked: () -> Unit
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = getString(com.dida.create_community.R.string.create_community_create_empty_text),
                style = DidaTypography.h1,
                fontSize = dpToSp(dp = 20.dp),
                color = TextGray
            )
            VerticalDivider(dp = 24)
            Surface(
                modifier = Modifier
                    .background(color = BrandLemon, shape = RoundedCornerShape(size = 10.dp))
                    .padding(16.dp)
                    .clickableSingle { onClicked() },
                color = BrandLemon
            ) {
                Text(
                    text = getString(com.dida.create_community.R.string.create_community_empty_create_button_text),
                    style = DidaTypography.h1,
                    fontSize = dpToSp(dp = 16.dp),
                    color = MainBlack
                )
            }
        }
    }
}
