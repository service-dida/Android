package com.dida.android.presentation.views.nav.mypage

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.android.databinding.FragmentHomeBinding
import com.dida.android.databinding.FragmentNicknameBinding
import com.dida.android.databinding.FragmentWalletBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.viewmodel.nav.home.HomeViewModel
import com.dida.android.presentation.viewmodel.nav.home.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding, WalletViewModel>(R.layout.fragment_wallet) {

    private val TAG = "WalletFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_wallet

    override val viewModel : WalletViewModel by viewModels()


    override fun initStartView() {
        initToolbar()
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

    private fun initToolbar(){
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_close_24)
        binding.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
    }
}