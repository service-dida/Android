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
import com.dida.block.BlockViewModel
import com.dida.block.databinding.FragmentBlockBinding
import com.dida.common.dialog.CentralDialogFragment
import com.dida.compose.theme.DIDA_THEME
import com.dida.compose.theme.DidaTypography
import com.dida.settings.SettingsViewModel
import com.dida.settings.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BlockFragment : BaseFragment<FragmentBlockBinding, BlockViewModel>(com.dida.block.R.layout.fragment_block) {

    private val TAG = "SettingsFragment"

    override val layoutResourceId: Int
        get() = com.dida.block.R.layout.fragment_block

    override val viewModel: BlockViewModel by viewModels()
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

            }
        }
    }

    private fun initToolbar(){
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    @Composable
    fun BlockScreen() {

    }
}

@Preview
@Composable
fun BlockPreview() {
    DIDA_THEME {
        BlockFragment().BlockScreen()
    }
}