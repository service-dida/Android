package com.dida.android.presentation.views.nav.home

import androidx.fragment.app.viewModels
import com.dida.android.R
import com.dida.android.databinding.FragmentNicknameBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.viewmodel.nav.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentNicknameBinding, HomeViewModel>(R.layout.fragment_home) {

    private val TAG = "HomeFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    override val viewModel : HomeViewModel by viewModels()


    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}