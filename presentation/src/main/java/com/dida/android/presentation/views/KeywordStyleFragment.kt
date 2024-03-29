package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.ai.databinding.FragmentKeywordStyleBinding
import com.dida.ai.keyword.KeywordNavigationAction
import com.dida.ai.keyword.KeywordType
import com.dida.ai.keyword.KeywordViewModel
import com.dida.ai.keyword.component.CustomLinearProgressBar
import com.dida.ai.keyword.component.KeywordMore
import com.dida.ai.keyword.component.KeywordTitle
import com.dida.ai.keyword.component.NextButton
import com.dida.ai.keyword.component.SelectKeywordTitle
import com.dida.ai.keyword.component.SelectKeywords
import com.dida.ai.keyword.component.StyleKeywords
import com.dida.ai.keyword.style.KeywordStyleViewModel
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
        initToolbar()
        setOnBackPressedEvent()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationAction.collectLatest {
                when (it) {
                    is KeywordNavigationAction.NavigateToSkip -> sharedViewModel.insertKeyword(KeywordType.Style)
                    is KeywordNavigationAction.NavigateToNext -> {}
                }
                navigate(KeywordStyleFragmentDirections.actionKeywordStyleFragmentToKeywordColorFragment())
            }
        }
    }

    override fun initAfterBinding() {}

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener {
                onPopBackStack()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val keywords by viewModel.keywordsState.collectAsStateWithLifecycle()
                val hasNext by viewModel.nextState.collectAsStateWithLifecycle()

                val selectedKeywords = sharedViewModel.keywords.collectAsStateWithLifecycle()
                val selectedCount by remember { derivedStateOf { selectedKeywords.value.count { it.word != "" } > 0 } }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MainBlack)
                ) {
                    CustomLinearProgressBar(progress = 0.75f)
                    KeywordTitle(type = KeywordType.Style)
                    KeywordMore(onButtonClicked = {})
                    StyleKeywords(
                        keywords = keywords,
                        onKeywordClicked = {
                            viewModel.onKeywordClicked(it)
                            sharedViewModel.insertKeyword(KeywordType.Style, it)
                        }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    SelectKeywordTitle(isSelected = selectedCount)
                    SelectKeywords(keywords = selectedKeywords.value)
                    NextButton(
                        hasNext = hasNext,
                        onButtonClicked = { viewModel.onNextClicked() }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    private fun setOnBackPressedEvent() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onPopBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    private fun onPopBackStack() {
        sharedViewModel.deleteKeyword(KeywordType.Style)
        navController.popBackStack()
    }
}
