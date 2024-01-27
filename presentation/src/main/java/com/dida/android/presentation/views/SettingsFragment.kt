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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.common.dialog.CentralDialogFragment
import com.dida.compose.common.DidaBoldText
import com.dida.compose.common.DidaMediumText
import com.dida.compose.theme.LineSurface
import com.dida.compose.theme.MainBlack
import com.dida.compose.theme.NoticeRed
import com.dida.compose.theme.White
import com.dida.domain.main.model.RequestEmailType
import com.dida.settings.SettingsType
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
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                SettingScreen(
                    onClicked = {
                        when (it) {
                            SettingsType.EDIT_PROFILE -> navigate(SettingsFragmentDirections.actionSettingFragmentToUpdateProfileFragment())
                            SettingsType.EDIT_PASSWORD ->  navigate(SettingsFragmentDirections.actionSettingFragmentToEmailFragment(RequestEmailType.RESET_PASSWORD))
                            SettingsType.ACCOUNT -> Unit
                            SettingsType.NOTIFICATION -> Unit
                            SettingsType.INVISIBLE_CARD -> navigate(SettingsFragmentDirections.actionSettingFragmentToHideListFragment())
                            SettingsType.BLOCK_USER -> navigate(SettingsFragmentDirections.actionSettingFragmentToBlockFragment())
                            SettingsType.PRIVACY -> onWebView(getString(com.dida.common.R.string.privacy_url))
                            SettingsType.SERVICE -> onWebView(getString(com.dida.common.R.string.service_url))
                        }
                    },
                    onLogOutClicked = { logOutDialog() },
                    onDeleteUserClicked = { deleteUserDialog() }
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
            .title(getString(R.string.logout_dialog_message))
            .positiveButton(getString(R.string.logout_dialog_positive), object : CentralDialogFragment.OnClickListener {
                override fun onClick() {
                    viewModel.logOut()
                }
            })
            .negativeButton(getString(R.string.logout_dialog_negative))
            .build()
            .show(childFragmentManager, "log_out_dialog")
    }

    private fun deleteUserDialog() {
        CentralDialogFragment.Builder()
            .title(getString(R.string.delete_user_dialog_title))
            .message(getString(R.string.delete_user_dialog_message))
            .positiveButton(getString(R.string.delete_user_dialog_positive), object : CentralDialogFragment.OnClickListener {
                override fun onClick() {
                    viewModel.deleteUser()
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
        onClicked: (type: SettingsType) -> Unit,
        onLogOutClicked: () -> Unit,
        onDeleteUserClicked: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainBlack)
        ) {
            val settings by viewModel.settings.collectAsStateWithLifecycle()

            settings.forEach {
                SettingItem(
                    iconRes = it.iconRes,
                    message = stringResource(id = it.message),
                    onClicked = { onClicked(it.type) }
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            DidaMediumText(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = com.dida.common.R.string.app_version_string),
                fontSize = 14,
                color = LineSurface
            )
            Spacer(modifier = Modifier.weight(1f))
            Surface(
                modifier = Modifier.clickable { onLogOutClicked() },
                color = MainBlack
            ) {
                DidaMediumText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(id = com.dida.common.R.string.logout_text),
                    fontSize = 16,
                    textAlign = TextAlign.Start,
                    color = NoticeRed
                )
            }
            Surface(
                modifier = Modifier.clickable { onDeleteUserClicked() },
                color = MainBlack
            ) {
                DidaMediumText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(id = com.dida.common.R.string.delete_user_text),
                    fontSize = 16,
                    textAlign = TextAlign.Start,
                    color = NoticeRed
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
                DidaBoldText(
                    text = message,
                    color = White,
                    textAlign = TextAlign.Center,
                    fontSize = 18
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 16.dp)
                    .background(LineSurface)
            )
        }
    }
}
