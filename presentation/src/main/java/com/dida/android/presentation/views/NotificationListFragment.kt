package com.dida.android.presentation.views

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dida.common.adapter.UserCardAdapter
import com.dida.notification.list.NotificationListViewModel
import com.dida.notification.list.R
import com.dida.notification.list.adapter.NotificationAdapter
import com.dida.notification.list.databinding.FragmentNotificationListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotificationListFragment : BaseFragment<FragmentNotificationListBinding, NotificationListViewModel>(R.layout.fragment_notification_list) {

    private val TAG = "SwapHistoryFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_notification_list

    override val viewModel : NotificationListViewModel by viewModels()
    private val notificationAdapter: NotificationAdapter by lazy { NotificationAdapter(viewModel) }

    private val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.notificationState.collectLatest {
                notificationAdapter.submitData(it)
            }
        }
    }

    override fun initAfterBinding() {

    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun initAdapter() {
        binding.rvNotification.apply {
            adapter = notificationAdapter
            layoutManager = LinearLayoutManager(context)
        }

        notificationAdapter.addLoadStateListener {

        }
    }
}
