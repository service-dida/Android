package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.ai.databinding.FragmentKeywordResultBinding
import com.dida.ai.keyword.KeywordViewModel
import com.dida.ai.keyword.result.KeywordResultButton
import com.dida.ai.keyword.result.KeywordResultImages
import com.dida.ai.keyword.result.KeywordResultMessage
import com.dida.ai.keyword.result.KeywordResultNavigationAction
import com.dida.ai.keyword.result.KeywordResultTitle
import com.dida.ai.keyword.result.KeywordResultViewModel
import com.dida.ai.keyword.result.RestartKeyword
import com.dida.compose.utils.WeightDivider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KeywordResultFragment :
    BaseFragment<FragmentKeywordResultBinding, KeywordResultViewModel>(com.dida.ai.R.layout.fragment_keyword_result) {

    private val TAG = "KeywordResultFragment"

    override val layoutResourceId: Int
        get() = com.dida.ai.R.layout.fragment_keyword_result // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: KeywordResultViewModel by viewModels()
    private val sharedViewModel: KeywordViewModel by activityViewModels()
    private val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        createAiPicture()
        observeNavigation()
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {}

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_close_white)
            this.setNavigationOnClickListener { navigate(KeywordResultFragmentDirections.actionKeywordResultFragmentToAddFragment()) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val aiPictures by viewModel.aiPictures.collectAsStateWithLifecycle()
                val selectedPicture by viewModel.selectedPicture.collectAsStateWithLifecycle()

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    KeywordResultTitle()
                    KeywordResultMessage()
                    if (aiPictures.isNotEmpty()) {
                        KeywordResultImages(
                            images = aiPictures,
                            selectedImage = selectedPicture,
                            onClicked = viewModel::onSelectImage
                        )
                    }
                    WeightDivider(weight = 1f)
                    RestartKeyword(onClicked = viewModel::onRestartKeyword)
                    KeywordResultButton(
                        isSelected = selectedPicture.isNotBlank(),
                        onDownloadClicked = viewModel::onDownload,
                        onCreateNftClicked = viewModel::onCreateNft
                    )
                }

            }
        }
    }

    private fun observeNavigation() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationAction.collectLatest {
                when (it) {
                    is KeywordResultNavigationAction.NavigateToRestartKeyword -> {}
                    is KeywordResultNavigationAction.NavigateToDownloadAiPicture -> {}
                    is KeywordResultNavigationAction.NavigateToCreateNftDialog -> {}
                }
            }
        }
    }

    private fun createAiPicture() {
        val sentence = sharedViewModel.getKeywords()
        viewModel.createAiPicture(sentence)
    }
}
