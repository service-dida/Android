package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.notification.list.NotificationListViewModel
import com.dida.notification.list.R
import com.dida.notification.list.databinding.FragmentNotificationListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationListFragment : BaseFragment<FragmentNotificationListBinding, NotificationListViewModel>(R.layout.fragment_notification_list) {

    private val TAG = "SwapHistoryFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_notification_list

    override val viewModel : NotificationListViewModel by viewModels()

    private val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
            this.title = "알림"
        }
    }
}
