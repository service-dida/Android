package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.common.widget.DefaultSnackBar
import com.dida.compose.theme.Surface2
import com.dida.compose.utils.LineDivider
import com.dida.compose.utils.VerticalDivider
import com.dida.compose.utils.reachEnd
import com.dida.domain.main.model.Follow
import com.dida.hot_seller.HotSellerMessageAction
import com.dida.hot_seller.HotSellerViewModel
import com.dida.hot_seller.component.HotSellerItem
import com.dida.hot_seller.databinding.FragmentHotSellerBinding
import com.dida.user_followed.UserFollowedMessageAction
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HotSellerFragment : BaseFragment<FragmentHotSellerBinding, HotSellerViewModel>(com.dida.hot_seller.R.layout.fragment_hot_seller) {

    private val TAG = "HotSellerFragment"

    override val layoutResourceId: Int
        get() = com.dida.hot_seller.R.layout.fragment_hot_seller // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : HotSellerViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.messageEvent.collectLatest {
                when(it) {
                    is HotSellerMessageAction.UserFollowMessage -> showMessageSnackBar(String.format(getString(R.string.user_follow_message), it.nickname))
                    is HotSellerMessageAction.UserUnFollowMessage -> showMessageSnackBar(getString(R.string.user_unfollow_message))
                }
            }
        }
    }

    override fun initAfterBinding() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val hotSellers by viewModel.hotSellerState.collectAsStateWithLifecycle()
                val listState = rememberLazyListState()
                val nextPage by remember {
                    derivedStateOf { listState.reachEnd() }
                }

                LaunchedEffect(key1 = nextPage) {
                    if (nextPage) viewModel.onNextPage()
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item { VerticalDivider(dp = 19) }
                    items(hotSellers.size) {
                        HotSellerItem(
                            item = hotSellers.content[it],
                            onFollowButtonClicked = { viewModel.onFollowButtonClicked(hotSellers.content[it]) },
                            onUserClicked = { navigate(HotSellerFragmentDirections.actionHotSellerFragmentToUserProfileFragment(hotSellers.content[it].memberInfo.memberId)) }
                        )
                    }
                    item { VerticalDivider(dp = 19) }
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

    private fun showMessageSnackBar(message: String) {
        DefaultSnackBar.Builder()
            .view(binding.root)
            .message(message)
            .build()
    }
}
