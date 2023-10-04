package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.ai.databinding.FragmentKeywordColorBinding
import com.dida.ai.keyword.KeywordNavigationAction
import com.dida.ai.keyword.KeywordType
import com.dida.ai.keyword.KeywordViewModel
import com.dida.ai.keyword.color.KeywordColorViewModel
import com.dida.android.presentation.views.ui.ColorKeywords
import com.dida.android.presentation.views.ui.CustomLinearProgressBar
import com.dida.android.presentation.views.ui.DrawButton
import com.dida.android.presentation.views.ui.KeywordMore
import com.dida.android.presentation.views.ui.KeywordTitle
import com.dida.android.presentation.views.ui.SelectKeywordTitle
import com.dida.android.presentation.views.ui.SelectKeywords
import com.dida.android.presentation.views.ui.WriteKeyword
import com.dida.common.dialog.CentralDialogFragment
import com.dida.compose.theme.MainBlack
import com.dida.compose.utils.VerticalDivider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KeywordColorFragment :
    BaseFragment<FragmentKeywordColorBinding, KeywordColorViewModel>(com.dida.ai.R.layout.fragment_keyword_color) {

    private val TAG = "KeywordColorFragment"

    override val layoutResourceId: Int
        get() = com.dida.ai.R.layout.fragment_keyword_color // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: KeywordColorViewModel by viewModels()
    private val sharedViewModel: KeywordViewModel by activityViewModels()
    private val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        setOnBackPressedEvent()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationAction.collectLatest {
                when (it) {
                    is KeywordNavigationAction.NavigateToSkip -> sharedViewModel.insertKeyword(KeywordType.Color)
                    is KeywordNavigationAction.NavigateToNext -> showDrawDialog()
                }
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
                val hasNext by viewModel.nextState.collectAsStateWithLifecycle()

                val selectedKeywords by sharedViewModel.keywords.collectAsStateWithLifecycle()
                val selectedCount by remember { derivedStateOf { selectedKeywords.count { it.word != "" } > 0 } }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MainBlack)
                ) {
                    CustomLinearProgressBar(progress = 1f)
                    KeywordTitle(type = KeywordType.Color)
                    KeywordMore(onButtonClicked = {})
                    ColorKeywords(
                        onKeywordClicked = {
                            sharedViewModel.insertKeyword(KeywordType.Color, it)
                            viewModel.onKeywordClicked(it)
                        }
                    )
                    WriteKeyword(onButtonClicked = {})
                    Spacer(modifier = Modifier.weight(1f))
                    SelectKeywordTitle(isSelected = selectedCount)
                    SelectKeywords(keywords = selectedKeywords)
                    DrawButton(
                        hasNext = hasNext,
                        onButtonClicked = { viewModel.onNextClicked() }
                    )
                    VerticalDivider(dp = 16)
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
        sharedViewModel.deleteKeyword(KeywordType.Color)
        navController.popBackStack()
    }

    // TODO : 시작하기 클릭시 로직 추가하기
    private fun showDrawDialog() {
        CentralDialogFragment.Builder()
            .title(getString(com.dida.common.R.string.draw_dialog_title))
            .message(getString(com.dida.common.R.string.draw_dialog_description))
            .positiveButton(getString(com.dida.common.R.string.draw_dialog_positive), object : CentralDialogFragment.OnClickListener {
                override fun onClick() {
                    navigate(KeywordColorFragmentDirections.actionKeywordColorFragmentToKeywordResultFragment())
                }
            })
            .negativeButton(getString(com.dida.common.R.string.draw_dialog_negative))
            .build()
            .show(childFragmentManager, "show_draw_dialog")
    }
}
