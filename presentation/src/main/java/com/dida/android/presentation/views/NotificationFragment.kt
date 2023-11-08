package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.domain.main.model.Follow
import com.dida.notification.NotificationNavigationAction
import com.dida.notification.NotificationViewModel
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
            setContent {}
        }
    }

    private fun initToolbar(){
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
