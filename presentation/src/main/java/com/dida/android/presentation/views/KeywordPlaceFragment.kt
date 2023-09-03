package com.dida.android.presentation.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dida.ai.databinding.FragmentKeywordPlaceBinding
import com.dida.ai.keyword.place.KeywordPlaceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KeywordPlaceFragment :
    BaseFragment<FragmentKeywordPlaceBinding, KeywordPlaceViewModel>(com.dida.ai.R.layout.fragment_keyword_place) {

    private val TAG = "KeywordPlaceFragment"

    override val layoutResourceId: Int
        get() = com.dida.ai.R.layout.fragment_keyword_place // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: KeywordPlaceViewModel by viewModels()
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
