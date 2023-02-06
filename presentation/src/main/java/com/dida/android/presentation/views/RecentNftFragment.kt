package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.dida.android.util.toLoginSuccess
import com.dida.nickname.NicknameNavigationAction
import com.dida.nickname.NicknameViewModel
import com.dida.nickname.databinding.FragmentNicknameBinding
import com.dida.recent_nft.RecentNftViewModel
import com.dida.recent_nft.databinding.FragmentRecentNftBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecentNftFragment : BaseFragment<FragmentRecentNftBinding, RecentNftViewModel>(com.dida.recent_nft.R.layout.fragment_recent_nft) {

    private val TAG = "RecentNftFragment"

    override val layoutResourceId: Int
        get() = com.dida.recent_nft.R.layout.fragment_recent_nft // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : RecentNftViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.errorEvent.collectLatest {
                    showToastMessage(it)
                }
            }
        }
    }

    override fun initAfterBinding() {
    }
}
