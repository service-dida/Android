package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.ai.databinding.FragmentKeywordStyleBinding
import com.dida.ai.keyword.KeywordNavigationAction
import com.dida.ai.keyword.KeywordViewModel
import com.dida.ai.keyword.style.KeywordStyleViewModel
import com.dida.android.presentation.views.ui.CustomLinearProgressBar
import com.dida.android.presentation.views.ui.KeywordMore
import com.dida.android.presentation.views.ui.KeywordStyleTitle
import com.dida.android.presentation.views.ui.NextButton
import com.dida.android.presentation.views.ui.SelectKeywordTitle
import com.dida.android.presentation.views.ui.SelectKeywords
import com.dida.android.presentation.views.ui.StyleKeywords
import com.dida.android.presentation.views.ui.WriteKeyword
import com.dida.compose.theme.MainBlack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KeywordStyleFragment :
    BaseFragment<FragmentKeywordStyleBinding, KeywordStyleViewModel>(com.dida.ai.R.layout.fragment_keyword_style) {

    private val TAG = "KeywordStyleFragment"

    override val layoutResourceId: Int
        get() = com.dida.ai.R.layout.fragment_keyword_style // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: KeywordStyleViewModel by viewModels()
    private val sharedViewModel: KeywordViewModel by activityViewModels()
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
//                when (it) {
//                    is KeywordNavigationAction.NavigateToSkip -> sharedViewModel.insertKeyword("")
//                    is KeywordNavigationAction.NavigateToNext -> sharedViewModel.insertKeyword(viewModel.selectKeywordState.value)
//                }
//                navigate(KeywordStyleFragmentDirections.actionKeywordStyleFragmentToKeywordColorFragment())
            }
        }
    }

    override fun initAfterBinding() {}

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener {
//                sharedViewModel.deleteKeyword(2)
                navController.popBackStack()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setContent {
                binding.composeView.apply {
                    setContent {
                        val keywords = viewModel.keywordsState.collectAsStateWithLifecycle()
                        val selectKeyword = viewModel.selectKeywordState.collectAsStateWithLifecycle()
                        val selectedKeywords = sharedViewModel.keywords.collectAsStateWithLifecycle()
                        val hasNext = viewModel.nextState.collectAsStateWithLifecycle()

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MainBlack)
                        ) {
                            CustomLinearProgressBar(progress = 0.75f)
                            KeywordStyleTitle()
                            KeywordMore(onButtonClicked = {})
                            StyleKeywords(
                                keywords = keywords.value,
                                selectKeyword = selectKeyword.value,
                                onKeywordClicked = { viewModel.onKeywordClicked(it) }
                            )
                            WriteKeyword(onButtonClicked = {})
                            Spacer(modifier = Modifier.weight(1f))
                            SelectKeywordTitle(isSelected = selectedKeywords.value.isNotEmpty())
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
    }
}
