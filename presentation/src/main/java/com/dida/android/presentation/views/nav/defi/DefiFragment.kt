package com.dida.android.presentation.views.nav.defi

import androidx.fragment.app.viewModels
import com.dida.android.R
import com.dida.android.databinding.FragmentDefiBinding
import com.dida.android.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DefiFragment : BaseFragment<FragmentDefiBinding, DefiViewModel>(R.layout.fragment_defi) {

    private val TAG = "DefiFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_defi

    override val viewModel : DefiViewModel by viewModels()


    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}