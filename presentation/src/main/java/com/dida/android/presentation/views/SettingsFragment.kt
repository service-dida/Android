package com.dida.android.presentation.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.common.dialog.CentralDialogFragment
import com.dida.compose.theme.DIDA_THEME
import com.dida.compose.theme.DidaTypography
import com.dida.settings.SETTINGS
import com.dida.settings.SettingsViewModel
import com.dida.settings.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>(com.dida.settings.R.layout.fragment_settings) {

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
        setOnBackPressedEvent()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigateToMainEvent.collectLatest {
                navigate(SettingsFragmentDirections.actionMainFragment())
            }
        }
    }

    override fun initAfterBinding() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setContent {
                SettingScreen(
                    onClicked = {
                        when (it) {
                            SETTINGS.EDIT_PROFILE -> navigate(SettingsFragmentDirections.actionSettingFragmentToUpdateProfileFragment())
                            SETTINGS.EDIT_PASSWORD ->  navigate(SettingsFragmentDirections.actionSettingFragmentToEmailFragment(RequestEmailType.RESET_PASSWORD))
                            SETTINGS.ACCOUNT -> Unit
                            SETTINGS.NOTIFICATION -> Unit
                            SETTINGS.INVISIBLE_CARD -> navigate(SettingsFragmentDirections.actionSettingFragmentToHideListFragment())
                            SETTINGS.BLOCK_USER -> navigate(SettingsFragmentDirections.actionSettingFragmentToBlockFragment())
                            SETTINGS.PRIVACY -> onWebView(getString(com.dida.common.R.string.privacy_url))
                            SETTINGS.SERVICE -> onWebView(getString(com.dida.common.R.string.service_url))
                        }
                    },
                    onLogOutClicked = { logOutDialog() }
                )
            }
        }
    }

    private fun initToolbar(){
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun logOutDialog() {
        CentralDialogFragment.Builder()
            .message(getString(R.string.logout_dialog_message))
            .positiveButton(getString(R.string.logout_dialog_positive), object : CentralDialogFragment.OnClickListener {
                override fun onClick() {
                    viewModel.logOut()
                }
            })
            .negativeButton(getString(R.string.logout_dialog_negative))
            .build()
            .show(childFragmentManager, "log_out_dialog")
    }

    private fun onWebView(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun setOnBackPressedEvent(){
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigate(SettingsFragmentDirections.actionSettingFragmentToMypageFragment())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    @Composable
    fun SettingScreen(
        onClicked: (type: SETTINGS) -> Unit,
        onLogOutClicked: () -> Unit
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
        ) {
            val settings by viewModel.settings.collectAsState()

            settings.forEach {
                SettingType(type = it, onClicked = onClicked)
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = com.dida.common.R.string.app_version_string),
                style = DidaTypography.body1,
                fontSize = 14.sp,
                color = Color(0x80DADADA)
            )
            Spacer(modifier = Modifier.weight(1f))
            Surface(
                modifier = Modifier.clickable { onLogOutClicked() },
                color = Color(0xFF121212)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(id = com.dida.common.R.string.logout_text),
                    style = DidaTypography.body1,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    color = Color(0xFFE8625B)
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }
    }

    @Composable
    private fun SettingType(
        type: SETTINGS,
        onClicked: (type: SETTINGS) -> Unit
    ) {
        when (type) {
            SETTINGS.EDIT_PROFILE ->
                SettingItem(
                    iconRes = com.dida.common.R.drawable.ic_profile_edit,
                    message = stringResource(id = com.dida.settings.R.string.profile_edit_title)
                ) {
                    onClicked(SETTINGS.EDIT_PROFILE)
                }
            SETTINGS.EDIT_PASSWORD ->
                SettingItem(
                    iconRes = com.dida.common.R.drawable.ic_password_edit,
                    message = stringResource(id = com.dida.settings.R.string.password_edit_title)
                ) {
                    onClicked(SETTINGS.EDIT_PASSWORD)
                }
            SETTINGS.ACCOUNT ->
                SettingItem(
                    iconRes = com.dida.common.R.drawable.ic_account_information,
                    message = stringResource(id = com.dida.settings.R.string.account_information_title)
                ) {
                    onClicked(SETTINGS.ACCOUNT)
                }
            SETTINGS.NOTIFICATION ->
                SettingItem(
                    iconRes = com.dida.common.R.drawable.ic_notification,
                    message = stringResource(id = com.dida.settings.R.string.notification_title)
                ) {
                    onClicked(SETTINGS.NOTIFICATION)
                }
            SETTINGS.INVISIBLE_CARD ->
                SettingItem(
                    iconRes = com.dida.common.R.drawable.ic_invisible,
                    message = stringResource(id = com.dida.settings.R.string.invisible_title)
                ) {
                    onClicked(SETTINGS.INVISIBLE_CARD)
                }
            SETTINGS.BLOCK_USER ->
                SettingItem(
                    iconRes = com.dida.common.R.drawable.ic_block,
                    message = stringResource(id = com.dida.settings.R.string.block_title)
                ) {
                    onClicked(SETTINGS.BLOCK_USER)
                }
            SETTINGS.PRIVACY ->
                SettingItem(
                    iconRes = com.dida.common.R.drawable.ic_privacy,
                    message = stringResource(id = com.dida.settings.R.string.privacy)
                ) {
                    onClicked(SETTINGS.PRIVACY)
                }
            SETTINGS.SERVICE ->
                SettingItem(
                    iconRes = com.dida.common.R.drawable.ic_service,
                    message = stringResource(id = com.dida.settings.R.string.service)
                ) {
                    onClicked(SETTINGS.SERVICE)
                }
        }
    }

    @Composable
    private fun SettingItem(
        iconRes: Int,
        message: String,
        onClicked: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClicked() }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(painter = painterResource(id = iconRes), contentDescription = "설정 아이템 아이콘")
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = message,
                    style = DidaTypography.button,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 16.dp)
                    .background(Color(0x80DADADA))
            )
        }
    }
}

@Preview
@Composable
fun SettingPreview() {
    DIDA_THEME {
        SettingsFragment().SettingScreen(
            onClicked = {},
            onLogOutClicked = {}
        )
    }
}