package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dida.ai.databinding.FragmentKeywordInputBinding
import com.dida.ai.keyword.input.KeywordInputViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KeywordInputFragment :
    BaseFragment<FragmentKeywordInputBinding, KeywordInputViewModel>(com.dida.ai.R.layout.fragment_keyword_input) {

    private val TAG = "KeywordInputFragment"

    override val layoutResourceId: Int
        get() = com.dida.ai.R.layout.fragment_keyword_input // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: KeywordInputViewModel by viewModels()
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

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener {
                navController.popBackStack()
            }
        }
    }
}
