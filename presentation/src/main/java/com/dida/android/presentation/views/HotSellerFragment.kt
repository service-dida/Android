package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.compose.theme.Surface2
import com.dida.compose.utils.LineDivider
import com.dida.compose.utils.VerticalDivider
import com.dida.domain.main.model.Follow
import com.dida.hot_seller.HotSellerViewModel
import com.dida.hot_seller.component.HotSellerItem
import com.dida.hot_seller.databinding.FragmentHotSellerBinding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HotSellerFragment : BaseFragment<FragmentHotSellerBinding, HotSellerViewModel>(com.dida.hot_seller.R.layout.fragment_hot_seller) {

    private val TAG = "HotSellerFragment"

    override val layoutResourceId: Int
        get() = com.dida.hot_seller.R.layout.fragment_hot_seller // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : HotSellerViewModel by viewModels()
    private val navController by lazy { findNavController() }

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
                val hotSellers by viewModel.hotSellerState.collectAsStateWithLifecycle()
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item { VerticalDivider(dp = 19) }
                    items(hotSellers.size) {
                        HotSellerItem(
                            item = hotSellers.content[it],
                            onFollowButtonClicked = {}
                        )
                    }
                    item { VerticalDivider(dp = 19) }
                }
            }
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.title = resources.getString(R.string.hot_seller)
            this.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
