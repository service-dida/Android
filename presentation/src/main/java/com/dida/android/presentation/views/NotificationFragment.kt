package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.compose.theme.MainBlack
import com.dida.compose.utils.reachEnd
import com.dida.domain.main.model.Follow
import com.dida.notification.NotificationNavigationAction
import com.dida.notification.NotificationViewModel
import com.dida.notification.component.NotificationItem
import com.dida.notification.databinding.FragmentNotificationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding, NotificationViewModel>(com.dida.notification.R.layout.fragment_notification) {

    private val TAG = "NotificationFragment"

    override val layoutResourceId: Int
        get() = com.dida.notification.R.layout.fragment_notification

    override val viewModel: NotificationViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationAction.collectLatest {
                when (it) {
                    is NotificationNavigationAction.NavigateToPost -> navigate(NotificationFragmentDirections.actionPostFragment(it.postId))
                    is NotificationNavigationAction.NavigateToNft -> navigate(NotificationFragmentDirections.actionNftFragment(it.nftId))
                    is NotificationNavigationAction.NavigateToUserFollowed -> navigate(NotificationFragmentDirections.actionUserFollowedFragment(0, Follow.FOLLOWER))
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
                val alarms by viewModel.notifications.collectAsStateWithLifecycle()
                val listState = rememberLazyListState()
                val nextPage by remember {
                    derivedStateOf { listState.reachEnd() }
                }

                LaunchedEffect(key1 = nextPage) {
                    if (nextPage) viewModel.nextPage()
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MainBlack),
                    state = listState,
                ) {
                    items(alarms.content) {
                        NotificationItem(
                            alarm = it,
                            onAlarmClicked = { viewModel.onNotificationClicked(it) }
                        )
                    }
                }
            }
        }
    }

    private fun initToolbar(){
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
