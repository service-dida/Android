package com.dida.android.presentation.views.nav.sns

import androidx.fragment.app.viewModels
import com.dida.android.R
import com.dida.android.databinding.FragmentNicknameBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.viewmodel.nav.sns.SNSViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SNSFragment : BaseFragment<FragmentNicknameBinding, SNSViewModel>(R.layout.fragment_sns) {

    private val TAG = "SNSFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_sns

    override val viewModel : SNSViewModel by viewModels()


    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}