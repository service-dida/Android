package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.settings.SettingsNavigationAction
import com.dida.settings.SettingsViewModel
import com.dida.settings.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SettingsFragment :
    BaseFragment<FragmentSettingsBinding, SettingsViewModel>(com.dida.settings.R.layout.fragment_settings) {

    private val TAG = "SettingsFragment"

    override val layoutResourceId: Int
        get() = com.dida.settings.R.layout.fragment_settings

    override val viewModel: SettingsViewModel by viewModels()
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
                    is SettingsNavigationAction.NavigateToProfileEdit -> navigate(SettingsFragmentDirections.actionSettingFragmentToUpdateProfileFragment())
                    is SettingsNavigationAction.NavigateToTempPassword -> navigate(SettingsFragmentDirections.actionSettingFragmentToTempPasswordFragment())
                    is SettingsNavigationAction.NavigateToPasswordEdit -> navigate(SettingsFragmentDirections.actionSettingFragmentToChangePasswordFragment())
                    is SettingsNavigationAction.NavigateToAccountInformation -> {}
                    is SettingsNavigationAction.NavigateToNotification -> {}
                    is SettingsNavigationAction.NavigateToHideList -> navigate(SettingsFragmentDirections.actionSettingFragmentToHideListFragment())
                    is SettingsNavigationAction.NavigateToLogout -> {
                        toastMessage("로그아웃 하였습니다.")
                        navigate(SettingsFragmentDirections.actionMainFragment())
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar(){
        binding.toolbar.apply {
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
