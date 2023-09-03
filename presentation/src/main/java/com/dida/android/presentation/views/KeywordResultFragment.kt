package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.ai.databinding.FragmentKeywordBinding
import com.dida.ai.databinding.FragmentKeywordResultBinding
import com.dida.ai.keyword.main.KeywordViewModel
import com.dida.ai.keyword.result.KeywordResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KeywordResultFragment :
    BaseFragment<FragmentKeywordResultBinding, KeywordResultViewModel>(com.dida.ai.R.layout.fragment_keyword_result) {

    private val TAG = "KeywordResultFragment"

    override val layoutResourceId: Int
        get() = com.dida.ai.R.layout.fragment_keyword_result // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: KeywordResultViewModel by viewModels()
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

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_close_white)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setContent {
            }
        }
    }
}
