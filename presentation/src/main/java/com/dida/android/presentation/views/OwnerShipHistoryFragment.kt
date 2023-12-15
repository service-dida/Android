package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.Surface1
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp
import com.dida.compose.utils.LineDivider
import com.dida.compose.utils.VerticalDivider
import com.dida.compose.utils.reachEnd
import com.dida.ownership_history.OwnerShipHistoryViewModel
import com.dida.ownership_history.R
import com.dida.ownership_history.component.OwnershipHistoryItem
import com.dida.ownership_history.databinding.FragmentOwnershipHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OwnerShipHistoryFragment : BaseFragment<FragmentOwnershipHistoryBinding, OwnerShipHistoryViewModel>(R.layout.fragment_ownership_history) {

    private val TAG = "OwnerShipHistoryFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_ownership_history

    override val viewModel: OwnerShipHistoryViewModel by viewModels()
    private val navController by lazy { findNavController() }

    private val args: OwnerShipHistoryFragmentArgs by navArgs()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        initToolbar()
        viewModel.setNftId(args.nftId)
        viewModel.getOwnershipHistory()
    }

    override fun initDataBinding() {}
    override fun initAfterBinding() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val ownerShipHistoryList by viewModel.ownershipHistoryState.collectAsStateWithLifecycle()
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
                        .padding(horizontal = 16.dp)
                ) {
                    item { OwnerTitle(isCurrent = true) }
                    ownerShipHistoryList.content.firstOrNull()?.let {
                        item {
                            OwnershipHistoryItem(
                                currentOwner = true,
                                item = it
                            )
                        }
                    }
                    if (ownerShipHistoryList.content.size > 1) {
                        item { VerticalDivider(dp = 32) }
                        item { LineDivider(Surface1, 8) }
                        item { OwnerTitle(isCurrent = false) }
                        items(ownerShipHistoryList.size) { index ->
                            if (index != 0) {
                                OwnershipHistoryItem(
                                    currentOwner = false,
                                    item = ownerShipHistoryList.content[index],
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun OwnerTitle(isCurrent: Boolean) {
        val titleLabel = if (isCurrent) R.string.ownership_current_owner_title else R.string.ownership_before_owner_title
        Text(
            text = stringResource(id = titleLabel),
            modifier = Modifier.padding(top = 23.dp),
            style = DidaTypography.h3,
            fontSize = dpToSp(dp = 18.dp),
            color = White
        )
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setTitleTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    com.dida.common.R.color.white
                )
            )
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
