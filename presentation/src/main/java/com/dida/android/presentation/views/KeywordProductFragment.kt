package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.ai.databinding.FragmentKeywordProductBinding
import com.dida.ai.keyword.KeywordNavigationAction
import com.dida.ai.keyword.KeywordViewModel
import com.dida.ai.keyword.product.KeywordProductViewModel
import com.dida.android.presentation.views.ui.CustomLinearProgressBar
import com.dida.android.presentation.views.ui.KeywordMore
import com.dida.android.presentation.views.ui.KeywordProductTitle
import com.dida.android.presentation.views.ui.Keywords
import com.dida.android.presentation.views.ui.NextButton
import com.dida.android.presentation.views.ui.SelectKeywordTitle
import com.dida.android.presentation.views.ui.SelectKeywords
import com.dida.android.presentation.views.ui.WriteKeyword
import com.dida.compose.theme.MainBlack
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
                    is KeywordNavigationAction.NavigateToSkip -> Unit
                    is KeywordNavigationAction.NavigateToNext -> shardViewModel.insertKeyword(it.keyword)
                }
                navigate(KeywordProductFragmentDirections.actionKeywordProductFragmentToKeywordPlaceFragment())
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
                val keywords = viewModel.keywordsState.collectAsState()
                val selectKeyword = viewModel.selectKeywordState.collectAsState()
                val selectedKeywords = shardViewModel.keywords.collectAsState()
                val hasNext = viewModel.nextState.collectAsState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MainBlack)
                ) {
                    CustomLinearProgressBar(progress = 0.5f)
                    KeywordProductTitle()
                    KeywordMore(onButtonClicked = {})
                    Keywords(
                        keywords = keywords.value,
                        selectKeyword = selectKeyword.value,
                        onKeywordClicked = { viewModel.onKeywordClicked(it) }
                    )
                    WriteKeyword(onButtonClicked = {})
                    Spacer(modifier = Modifier.weight(1f))
                    SelectKeywordTitle(isSelected = false)
                    SelectKeywords(keywords = selectedKeywords.value)
                    NextButton(
                        hasNext = hasNext.value,
                        onButtonClicked = { viewModel.onNextClicked() }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
