package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil.compose.AsyncImage
import com.dida.block.BlockViewModel
import com.dida.block.databinding.FragmentBlockBinding
import com.dida.compose.theme.BrandLemon
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.Surface1
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp
import com.dida.compose.utils.Divider12
import com.dida.compose.utils.clickableSingle
import com.dida.domain.main.model.HideMember
import dagger.hilt.android.AndroidEntryPoint


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

    override fun initDataBinding() {}

    override fun initAfterBinding() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setContent {
                val userList = viewModel.userListState.collectAsStateWithLifecycle()
                BlockScreen(userList.value)
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
    fun BlockScreen(
        userList: List<HideMember>
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(userList.size) {
                BlockUserItem(
                    user = userList[it]
                ) {
                    viewModel.onBlockCancel(it)
                }
            }
        }
    }

    @Composable
    fun BlockUserItem(
        user: HideMember,
        onBlockCancelClicked: (user: HideMember) -> Unit
    ) {
        Surface(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .clickableSingle { onBlockCancelClicked(user) },
            shape = RoundedCornerShape(24.dp),
            color = Surface1
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .background(Surface1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(62.dp),
                    shape = CircleShape,
                    color = Surface1
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = user.profileUrl,
                        contentDescription = "유저 이미지",
                        contentScale = ContentScale.Crop,
                        error = painterResource(id = com.dida.common.R.mipmap.img_dida_logo_foreground),
                        placeholder = painterResource(id = com.dida.common.R.mipmap.img_dida_logo_foreground)
                    )
                }
                Divider12()
                Text(
                    modifier = Modifier.weight(1f),
                    text = user.nickname,
                    style = DidaTypography.h3,
                    fontSize = dpToSp(dp = 16.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = White
                )
                Divider12()
                Surface(
                    modifier = Modifier
                        .clickableSingle { onBlockCancelClicked(user) },
                    border = BorderStroke(1.dp, BrandLemon),
                    shape = RoundedCornerShape(100.dp),
                    color = Surface1
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        text = "차단 해제",
                        style = DidaTypography.caption,
                        fontSize = dpToSp(dp = 12.dp),
                        color = White
                    )
                }
            }
        }

    }
}
