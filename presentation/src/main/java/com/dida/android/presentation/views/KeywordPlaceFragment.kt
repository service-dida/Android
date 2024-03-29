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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.ai.databinding.FragmentKeywordPlaceBinding
import com.dida.ai.keyword.KeywordNavigationAction
import com.dida.ai.keyword.KeywordType
import com.dida.ai.keyword.KeywordViewModel
import com.dida.ai.keyword.component.CustomLinearProgressBar
import com.dida.ai.keyword.component.DefaultKeywords
import com.dida.ai.keyword.component.KeywordMore
import com.dida.ai.keyword.component.KeywordTitle
import com.dida.ai.keyword.component.NextButton
import com.dida.ai.keyword.component.SelectKeywordTitle
import com.dida.ai.keyword.component.SelectKeywords
import com.dida.ai.keyword.place.KeywordPlaceViewModel
import com.dida.compose.theme.MainBlack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KeywordPlaceFragment :
    BaseFragment<FragmentKeywordPlaceBinding, KeywordPlaceViewModel>(com.dida.ai.R.layout.fragment_keyword_place) {

    private val TAG = "KeywordPlaceFragment"

    override val layoutResourceId: Int
        get() = com.dida.ai.R.layout.fragment_keyword_place // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: KeywordPlaceViewModel by viewModels()
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
                    is KeywordNavigationAction.NavigateToSkip -> sharedViewModel.insertKeyword(KeywordType.Place)
                    is KeywordNavigationAction.NavigateToNext -> {}
                }
                navigate(KeywordPlaceFragmentDirections.actionKeywordPlaceFragmentToKeywordStyleFragment())
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

                val selectedKeywords by sharedViewModel.keywords.collectAsStateWithLifecycle()
                val selectedCount by remember { derivedStateOf { selectedKeywords.count { it.word != "" } > 0 } }
                var selectedIndex by rememberSaveable { mutableStateOf(-1) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MainBlack)
                ) {
                    CustomLinearProgressBar(progress = 0.5f)
                    KeywordTitle(type = KeywordType.Place)
                    KeywordMore{
                        viewModel.getKeywordPlaces()
                        sharedViewModel.deleteKeyword(KeywordType.Place)
                        selectedIndex = -1
                    }
                    DefaultKeywords(
                        keywords = keywords,
                        onKeywordClicked = { keyword, position ->
                            viewModel.onKeywordClicked(keyword)
                            sharedViewModel.insertKeyword(KeywordType.Place, keyword)
                            selectedIndex = position
                        },
                        selectedIndex = selectedIndex
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    SelectKeywordTitle(isSelected = selectedCount)
                    SelectKeywords(keywords = selectedKeywords)
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
        sharedViewModel.deleteKeyword(KeywordType.Place)
        navController.popBackStack()
    }
}
