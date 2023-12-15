package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.Surface1
import com.dida.compose.theme.Surface4
import com.dida.compose.theme.Surface8
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp
import com.dida.compose.utils.LineDivider
import com.dida.compose.utils.reachEnd
import com.dida.domain.Contents
import com.dida.domain.main.model.OwnershipHistory
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
                Column {
                    currentOwnerArea(ownerShipHistoryList)

                    //소유권 내역이 하나라면 이전소유내역 비 노출
                    if (ownerShipHistoryList.content.size > 1) {
                        Spacer(Modifier.size(32.dp))
                        LineDivider(Surface1, 8)

                        beforeOwnerArea(ownerShipHistoryList)
                    }
                }
            }
        }
    }

    @Composable
    private fun currentOwnerArea(ownerShipHistoryList: Contents<OwnershipHistory>) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
            Text(
                text = "현재 소유자",
                modifier = Modifier
                    .padding(top = 23.dp),
                style = DidaTypography.h3,
                fontSize = dpToSp(dp = 18.dp),
                color = White
            )

            if (ownerShipHistoryList.content.isNotEmpty()) {
                OwnershipHistoryItem(
                    modifier = Modifier.padding(top = 12.dp),
                    nameColor = Color.White,
                    item = ownerShipHistoryList.content[0]
                )
            }
        }
    }

    @Composable
    private fun beforeOwnerArea(ownerShipHistoryList: Contents<OwnershipHistory>) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
            Text(
                text = "이전 소유 내역",
                modifier = Modifier
                    .padding(top = 24.dp),
                style = DidaTypography.h3,
                fontSize = dpToSp(dp = 18.dp),
                color = White
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp)
            ) {
                items(ownerShipHistoryList.size) { index ->
                    if (index != 0) {
                        OwnershipHistoryItem(
                            modifier = Modifier,
                            nameColor = Surface8,
                            item = ownerShipHistoryList.content[index],
                        )
                        Spacer(Modifier.size(12.dp))
                        LineDivider(Surface4, 1)
                    }
                }
            }
        }
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
