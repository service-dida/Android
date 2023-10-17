package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.add.R
import com.dida.add.databinding.FragmentAddBinding
import com.dida.add.main.AddViewModel
import com.dida.ai.keyword.KeywordViewModel
import com.dida.compose.theme.BrandLemon
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.Surface2
import com.dida.compose.theme.dpToSp
import com.dida.compose.utils.DidaImage
import com.dida.compose.utils.HorizontalDivider
import com.dida.compose.utils.TriangleEdgeShape
import com.dida.compose.utils.VerticalDivider
import com.dida.compose.utils.clickableSingle
import com.dida.password.PasswordDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddFragment : BaseFragment<FragmentAddBinding, AddViewModel>(R.layout.fragment_add) {

    private val TAG = "AddFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_add

    override val viewModel: AddViewModel by viewModels()
    private val sharedViewModel: KeywordViewModel by activityViewModels()
    private val navController: NavController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getWalletExists()
    }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        sharedViewModel.initKeywords()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.walletExistsState.collectLatest { existed ->
                    if (existed) {
                        PasswordDialog(6, "비밀번호 입력", "6자리를 입력해주세요.") { success, msg ->
                            if (!success) {
                                if (msg == "reset") navigate(AddFragmentDirections.actionAddFragmentToSettingFragment())
                                else navController.checkPopBackStack()
                            }
                        }.show(childFragmentManager, "AddFragment")
                    } else {
                        showToastMessage("지갑을 생성해야 합니다!")
                        navigate(AddFragmentDirections.actionAddFragmentToEmailFragment(RequestEmailType.MAKE_WALLET))
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AddScreen()
            }
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_close_white)
            this.setNavigationOnClickListener { navController.checkPopBackStack() }
        }
    }

    @Composable
    fun AddScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            VerticalDivider(dp = 24)
            Text(
                style = DidaTypography.h1,
                fontSize = dpToSp(dp = 22.dp),
                color = Color.White,
                text = "어떤 이미지로\nNFT를 생성하시겠어요?",
            )
            VerticalDivider(dp = 50)
            RecommendBubble()
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clickableSingle { navigate(AddFragmentDirections.actionAddFragmentToKeywordProductFragment()) },
                color = Surface2,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DidaImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        model = "https://blog.kakaocdn.net/dn/bCCCDH/btrY8ihFxJf/TGCh6Y5vYs5jkKC6RypuFK/img.jpg"
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 21.dp),
                        textAlign = TextAlign.Center,
                        style = DidaTypography.subtitle1,
                        fontSize = dpToSp(dp = 18.dp),
                        color = Color.White,
                        text = "AI 키워드 그리기",
                    )
                }
            }
            VerticalDivider(dp = 10)
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .background(color = Surface2)
                        .padding(vertical = 42.dp)
                        .clickableSingle { navigate(AddFragmentDirections.actionAddFragmentToCreateNftFragment()) },
                    textAlign = TextAlign.Center,
                    style = DidaTypography.subtitle1,
                    fontSize = dpToSp(dp = 18.dp),
                    color = Color.White,
                    text = "내 갤러리 그림\n그리기",
                )
                HorizontalDivider(dp = 14)
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .background(color = Surface2)
                        .padding(vertical = 42.dp),
                    textAlign = TextAlign.Center,
                    style = DidaTypography.subtitle1,
                    fontSize = dpToSp(dp = 18.dp),
                    color = Color.White,
                    text = "직접 입력해서\n그리기",
                )
            }
        }
    }

    @Composable
    fun RecommendBubble() {
        Surface(
            modifier = Modifier.offset(y = (-20).dp)
        ) {
            Row(
                modifier = Modifier.height(IntrinsicSize.Max)
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            color = BrandLemon,
                            shape = RoundedCornerShape(4.dp, 4.dp, 0.dp, 4.dp)
                        )
                        .wrapContentWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row {
                        Image(
                            painter = painterResource(id = com.dida.common.R.drawable.ic_dida_dialog_foreground),
                            contentDescription =
                        )
                        Text("DIDA 추천!")
                    }
                }
            }
            Column(
                modifier = Modifier
                    .background(
                        color = BrandLemon,
                        shape = TriangleEdgeShape(10)
                    )
                    .width(8.dp)
                    .fillMaxHeight()
            ) {}
        }
    }

    private fun NavController.checkPopBackStack() {
        val navigation = this
        if (navigation.backQueue.size <= 2) navigate(AddFragmentDirections.actionMainFragment())
        else navigation.popBackStack()
    }
}
