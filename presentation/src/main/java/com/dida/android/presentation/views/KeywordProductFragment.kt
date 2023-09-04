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
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.ai.databinding.FragmentKeywordProductBinding
import com.dida.ai.keyword.KeywordNavigationAction
import com.dida.ai.keyword.KeywordType
import com.dida.ai.keyword.KeywordViewModel
import com.dida.ai.keyword.KeywordViewModel.Companion.BLANK
import com.dida.ai.keyword.product.KeywordProductViewModel
import com.dida.android.presentation.views.ui.CustomLinearProgressBar
import com.dida.android.presentation.views.ui.KeywordMore
import com.dida.android.presentation.views.ui.KeywordTitle
import com.dida.android.presentation.views.ui.Keywords
import com.dida.android.presentation.views.ui.NextButton
import com.dida.android.presentation.views.ui.SelectKeywordTitle
import com.dida.android.presentation.views.ui.SelectKeywords
import com.dida.android.presentation.views.ui.WriteKeyword
import com.dida.common.dialog.CentralDialogFragment
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
                    is KeywordNavigationAction.NavigateToSkip -> sharedViewModel.insertKeyword(KeywordType.Product, BLANK)
                    is KeywordNavigationAction.NavigateToNext -> {}
                }
                navigate(KeywordProductFragmentDirections.actionKeywordProductFragmentToKeywordPlaceFragment())
            }
        }
    }

    override fun initAfterBinding() {}

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener {
                showDrawBackDialog()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setContent {
                val keywords = viewModel.keywordsState.collectAsStateWithLifecycle()
                val selectKeyword = viewModel.selectKeywordState.collectAsStateWithLifecycle()
                val hasNext = viewModel.nextState.collectAsStateWithLifecycle()

                val selectedKeywords = sharedViewModel.keywords.collectAsStateWithLifecycle()
                val selectedCount by remember { derivedStateOf { selectedKeywords.value.count { it != "" } > 0 } }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MainBlack)
                ) {
                    CustomLinearProgressBar(progress = 0.25f)
                    KeywordTitle(type = KeywordType.Product)
                    KeywordMore(onButtonClicked = {})
                    Keywords(
                        keywords = keywords.value,
                        selectKeyword = selectKeyword.value,
                        onKeywordClicked = {
                            viewModel.onKeywordClicked(it)
                            sharedViewModel.insertKeyword(KeywordType.Product, it)
                        }
                    )
                    WriteKeyword(onButtonClicked = {})
                    Spacer(modifier = Modifier.weight(1f))
                    SelectKeywordTitle(isSelected = selectedCount)
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

    private fun setOnBackPressedEvent() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showDrawBackDialog()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    private fun showDrawBackDialog() {
        CentralDialogFragment.Builder()
            .title(getString(com.dida.common.R.string.draw_back_dialog_title))
            .message(getString(com.dida.common.R.string.draw_back_dialog_description))
            .positiveButton(getString(com.dida.common.R.string.draw_back_dialog_positive), object : CentralDialogFragment.OnClickListener {
                override fun onClick() {
                    sharedViewModel.deleteKeyword(KeywordType.Product)
                    navController.popBackStack()
                }
            })
            .negativeButton(getString(com.dida.common.R.string.draw_back_dialog_negative))
            .build()
            .show(childFragmentManager, "show_draw_dialog")
    }
}
