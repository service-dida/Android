package com.dida.android.presentation.views

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.dida.android.R
import com.dida.android.util.toLoginFailure
import com.dida.android.util.toLoginSuccess
import com.dida.compose.theme.BrandLemon
import com.dida.compose.theme.DIDA_THEME
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.KakaoYellow
import com.dida.compose.theme.MainBlack
import com.dida.compose.theme.TextGray
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp
import com.dida.compose.utils.Divider12
import com.dida.compose.utils.Divider8
import com.dida.compose.utils.clickableSingle
import com.dida.login.LoginMainViewModel
import com.dida.login.LoginNavigationAction
import com.dida.login.databinding.FragmentLoginmainBinding
import com.dida.user_followed.Follow
import com.dida.user_followed.UserFollowedViewModel
import com.dida.user_followed.databinding.FragmentUserfollowedBinding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFollowedFragment :
    BaseFragment<FragmentUserfollowedBinding, UserFollowedViewModel>(com.dida.user_followed.R.layout.fragment_userfollowed) {

    private val TAG = "UserFollowedFragment"

    override val layoutResourceId: Int
        get() = com.dida.user_followed.R.layout.fragment_userfollowed // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: UserFollowedViewModel by viewModels()


    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setContent {

            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun UserFollowedScreen(slug: String){
        val tabs = listOf(Follow.FOLLOWER, Follow.FOLLOWING)

        val pagerState = rememberPagerState(
            pageCount = tabs.size,
            initialOffscreenLimit = 2,
            infiniteLoop = true,
            initialPage = 0,
        )
        val tabIndex = pagerState.currentPage
        val coroutineScope = rememberCoroutineScope()

        TabRow(selectedTabIndex = tabIndex,
            modifier = Modifier.padding(top = 20.dp)) {
            tabs.forEachIndexed { index, tab ->
                Tab(selected = tabIndex == index, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }, text = {
                    Text(text = tab.str)
                })
            }
        }

        HorizontalPager(
            state = pagerState
        ) { index ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (tabs[index]) {
                    Follow.FOLLOWING -> {}
                    Follow.FOLLOWER -> {}
                }
            }
        }
    }

    @Composable
    fun FollowedColumn(
        state: Follow
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            
        }
    }
    
    @Composable
    fun FollowUserItem(
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
                    model = "",
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
                        text = "",
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
}