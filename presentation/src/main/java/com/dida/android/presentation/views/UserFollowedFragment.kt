package com.dida.android.presentation.views

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.dida.android.R
import com.dida.android.util.toLoginFailure
import com.dida.android.util.toLoginSuccess
import com.dida.compose.theme.DIDA_THEME
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.KakaoYellow
import com.dida.compose.theme.MainBlack
import com.dida.compose.theme.White
import com.dida.login.LoginMainViewModel
import com.dida.login.LoginNavigationAction
import com.dida.login.databinding.FragmentLoginmainBinding
import com.dida.user_followed.UserFollowedViewModel
import com.dida.user_followed.databinding.FragmentUserfollowedBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFollowedFragment :
    BaseFragment<FragmentUserfollowedBinding, UserFollowedViewModel>(com.dida.user_followed.R.layout.fragment_userfollowed) {

    private val TAG = "UserFollowedFragment"

    override val layoutResourceId: Int
        get() = com.dida.user_followed.R.layout.fragment_userfollowed // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: UserFollowedViewModel by viewModels()


    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setContent {

            }
        }
    }
}