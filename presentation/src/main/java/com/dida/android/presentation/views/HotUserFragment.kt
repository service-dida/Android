package com.dida.android.presentation.views

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.common.util.repeatOnResumed
import com.dida.common.util.repeatOnStarted
import com.dida.hot_user.HotUserNavigationAction
import com.dida.hot_user.HotUserViewModel
import com.dida.hot_user.adapter.HotUserPagingAdapter
import com.dida.hot_user.databinding.FragmentHotUserBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HotUserFragment : BaseFragment<FragmentHotUserBinding, HotUserViewModel>(com.dida.hot_user.R.layout.fragment_hot_user) {

    private val TAG = "HotUserFragment"

    override val layoutResourceId: Int
        get() = com.dida.hot_user.R.layout.fragment_hot_user // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel : HotUserViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val hotUserPagingAdapter by lazy { HotUserPagingAdapter(viewModel) }
    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.hotUserState.collectLatest {
                hotUserPagingAdapter.submitData(it)
            }
        }

        viewLifecycleOwner.repeatOnResumed {
            viewModel.navigationEvent.collectLatest {
                when(it) {
                    is HotUserNavigationAction.NavigateToUserProfile -> navigate(HotUserFragmentDirections.actionHotUserFragmentToUserProfileFragment(userId = it.userId))
                    is HotUserNavigationAction.NavigateToFollow -> hotUserPagingAdapter.refresh()
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar(){
        binding.toolbar.apply {
            this.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun initAdapter() {
        binding.hotUserRecycler.adapter = hotUserPagingAdapter
    }
}
