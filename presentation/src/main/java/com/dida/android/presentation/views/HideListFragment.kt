package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dida.settings.R
import com.dida.settings.databinding.FragmentHideListBinding
import com.dida.settings.hidelist.HideListAdapter
import com.dida.settings.hidelist.HideListNavigationAction
import com.dida.settings.hidelist.HideListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HideListFragment : BaseFragment<FragmentHideListBinding, HideListViewModel>(R.layout.fragment_hide_list) {

    private val TAG = "HideListFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_hide_list

    override val viewModel: HideListViewModel by viewModels()

    private val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initMyPage()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is HideListNavigationAction.NavigateToDetailNft -> navigate(
                        HideListFragmentDirections.actionHideListFragmentToDetailNftFragment(it.cardId)
                    )
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHideList()
    }

    private fun initMyPage() {
        initToolbar()
        initAdapter()
    }

    private fun initAdapter() {
        binding.hideListRecycler.apply {
            adapter = HideListAdapter(viewModel)
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
