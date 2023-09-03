package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.ai.databinding.FragmentKeywordProductBinding
import com.dida.ai.keyword.KeywordNavigationAction
import com.dida.ai.keyword.KeywordViewModel
import com.dida.ai.keyword.product.KeywordProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KeywordProductFragment :
    BaseFragment<FragmentKeywordProductBinding, KeywordProductViewModel>(com.dida.ai.R.layout.fragment_keyword_product) {

    private val TAG = "KeywordFragment"

    override val layoutResourceId: Int
        get() = com.dida.ai.R.layout.fragment_keyword_product // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: KeywordProductViewModel by viewModels()
    private val shardViewModel: KeywordViewModel by activityViewModels()

    private val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationAction.collectLatest {
                when (it) {
                    is KeywordNavigationAction.NavigateToSkip -> {}
                    is KeywordNavigationAction.NavigateToNext -> { shardViewModel.insertKeyword(it.keyword) }
                }
            }
        }
    }

    override fun initAfterBinding() {}

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
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
