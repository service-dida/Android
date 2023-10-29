package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.compose.theme.BrandLemon
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.MainBlack
import com.dida.compose.theme.Surface2
import com.dida.compose.theme.TextGray
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp
import com.dida.compose.utils.DidaImage
import com.dida.compose.utils.HorizontalDivider
import com.dida.compose.utils.VerticalDivider
import com.dida.compose.utils.clickableSingle
import com.dida.domain.main.model.SoldOut
import com.dida.soldout.Period
import com.dida.soldout.SoldOutViewModel
import com.dida.soldout.databinding.FragmentSoldOutBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SoldOutFragment : BaseFragment<FragmentSoldOutBinding, SoldOutViewModel>(com.dida.soldout.R.layout.fragment_sold_out) {

    private val TAG = "SoldOutFragment"

    override val layoutResourceId: Int
        get() = com.dida.soldout.R.layout.fragment_sold_out

    @Inject
    lateinit var assistedFactory: SoldOutViewModel.AssistedFactory
    override val viewModel: SoldOutViewModel by viewModels {
        SoldOutViewModel.provideFactory(
            assistedFactory,
            period = args.period
        )
    }
    private val navController: NavController by lazy { findNavController() }
    private val args: SoldOutFragmentArgs by navArgs()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        initToolbar()
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val periods by viewModel.periodState.collectAsStateWithLifecycle()
                val selectedPeriod by viewModel.selectedPeriod.collectAsStateWithLifecycle()
                val items by viewModel.soldOutState.collectAsStateWithLifecycle()
                val listState = rememberLazyListState()
                val nextPage = remember {
                    derivedStateOf { listState.firstVisibleItemIndex == (items.content.size - 10) }
                }

                LaunchedEffect(key1 = nextPage.value) {
                    if (nextPage.value) viewModel.onNextPageFromSoldOut()
                }

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item { HorizontalDivider(dp = 6) }
                        items(
                            count = periods.size
                        ) {
                            PeriodItem(
                                item = periods[it],
                                isClicked = (periods[it].period == selectedPeriod),
                                onPeriodClicked = { viewModel.onSelectPeriod(periods[it].period) }
                            )
                        }
                        item { HorizontalDivider(dp = 6) }
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            count = items.content.size
                        ) {
                            SoldOutItem(
                                item = items.content[it],
                                onItemClicked = { navigate(SoldOutFragmentDirections.actionSoldOutFragmentToDetailNftFragment(items.content[it].nftInfo.nftId)) }
                            )
                        }
                        item { VerticalDivider(dp = 19) }
                    }
                }
            }
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    @Composable
    fun PeriodItem(
        item: Period,
        isClicked: Boolean = false,
        onPeriodClicked: () -> Unit
    ) {
        Text(
            modifier = Modifier
                .background(
                    color = if (isClicked) BrandLemon else Surface2,
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clickableSingle { onPeriodClicked() },
            text = item.text,
            style = DidaTypography.h1,
            fontSize = dpToSp(dp = 15.dp),
            color = if (isClicked) MainBlack else White
        )
    }

    @Composable
    fun SoldOutItem(
        item: SoldOut,
        onItemClicked: () -> Unit
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickableSingle { onItemClicked() },
            color = MainBlack
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DidaImage(
                    model = item.nftInfo.nftImgUrl,
                    modifier = Modifier
                        .size(76.dp)
                        .clip(RoundedCornerShape(8.dp)),
                )
                HorizontalDivider(dp = 12)
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = item.nftInfo.nftName,
                        style = DidaTypography.h1,
                        fontSize = dpToSp(dp = 18.dp),
                        color = White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    VerticalDivider(dp = 4)
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        DidaImage(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(RoundedCornerShape(100.dp)),
                            model = item.memberInfo.profileImgUrl
                        )
                        HorizontalDivider(dp = 8)
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = item.memberInfo.memberName,
                            style = DidaTypography.body1,
                            fontSize = dpToSp(dp = 14.dp),
                            color = TextGray
                        )
                    }

                }
                HorizontalDivider(dp = 12)
                Row {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = com.dida.common.R.drawable.ic_soldout),
                        contentDescription = "돈 아이콘"
                    )
                    HorizontalDivider(dp = 4)
                    Text(
                        text = item.nftInfo.price,
                        style = DidaTypography.body1,
                        fontSize = dpToSp(dp = 15.dp),
                        color = White
                    )
                }
            }
        }
    }
}
