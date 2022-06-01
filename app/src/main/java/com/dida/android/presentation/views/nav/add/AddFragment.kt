package com.dida.android.presentation.views.nav.add

import androidx.fragment.app.viewModels
import com.dida.android.R
import com.dida.android.databinding.FragmentNicknameBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.viewmodel.nav.add.AddViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : BaseFragment<FragmentNicknameBinding, AddViewModel>(R.layout.fragment_add) {

    private val TAG = "AddFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_add

    override val viewModel : AddViewModel by viewModels()


    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}