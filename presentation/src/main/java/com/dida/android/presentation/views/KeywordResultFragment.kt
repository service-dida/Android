package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.ai.databinding.FragmentKeywordResultBinding
import com.dida.ai.keyword.KeywordViewModel
import com.dida.ai.keyword.result.KeywordResultViewModel
import com.dida.compose.theme.dpToSp
import dagger.hilt.android.AndroidEntryPoint

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
                Column {
                    Text(
                        text = "키워드 재선택하기",
                        fontSize = dpToSp(dp = 16.dp),
                        color = Color.White,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { navigate(KeywordResultFragmentDirections.actionKeywordResultFragmentToKeywordProductFragment()) }
                    )
                    Text(
                        text = "NFT 만들기",
                        fontSize = dpToSp(dp = 16.dp),
                        color = Color.White,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { navigate(KeywordResultFragmentDirections.actionKeywordResultFragmentToCreateNftFragment()) }
                    )
                }

            }
        }
    }

    private fun createAiPicture() {
        val sentence = sharedViewModel.getKeywords()
        viewModel.createAiPicture(sentence)
    }
}
