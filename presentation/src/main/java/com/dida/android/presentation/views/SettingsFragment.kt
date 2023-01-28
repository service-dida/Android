package com.dida.android.presentation.views

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.settings.SettingsNavigationAction
import com.dida.settings.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SettingsFragment :
    BaseFragment<FragmentSettingsBinding, com.dida.settings.SettingsViewModel>(com.dida.settings.R.layout.fragment_settings) {

    private val TAG = "SettingsFragment"

    override val layoutResourceId: Int
        get() = com.dida.settings.R.layout.fragment_settings

    override val viewModel: com.dida.settings.SettingsViewModel by viewModels()
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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when(it) {
                    is SettingsNavigationAction.NavigateToProfileEdit -> {}
                    is SettingsNavigationAction.NavigateToPrePassword -> {}
                    is SettingsNavigationAction.NavigateToPasswordEdit -> {}
                    is SettingsNavigationAction.NavigateToAccountInformation -> {}
                    is SettingsNavigationAction.NavigateToNotification -> {}
                    is SettingsNavigationAction.NavigateToInVisible -> {}
                    is SettingsNavigationAction.NavigateToLogout -> {}
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar(){
        binding.toolbar.apply {
            this.title = resources.getString(R.string.detail_community_title)
            this.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
