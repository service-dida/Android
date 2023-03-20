package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import com.dida.common.util.repeatOnStarted
import com.dida.create_community.CreateCommunityViewModel
import com.dida.create_community.adapter.CreateCommunityNftAdapter
import com.dida.create_community.databinding.FragmentCreateCommunityNftBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateCommunityNftFragment(
    val createNftState: Int
) : BaseFragment<FragmentCreateCommunityNftBinding, CreateCommunityViewModel>(com.dida.create_community.R.layout.fragment_create_community_nft) {

    private val TAG = "CreateCommunityNftFragment"

    override val layoutResourceId: Int
        get() = com.dida.create_community.R.layout.fragment_create_community_nft

    override val viewModel : CreateCommunityViewModel by viewModels({ requireParentFragment() })
    private val cardsAdapter by lazy { CreateCommunityNftAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.repeatOnStarted {
            launch {
                viewModel.cardPostLikeState.collectLatest {
                    if (createNftState == 0) cardsAdapter.submitList(it)
                }
            }

            launch {
                viewModel.cardPostMyState.collectLatest {
                    if (createNftState == 1) cardsAdapter.submitList(it)
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initAdapter() {
        binding.recyclerNft.adapter = cardsAdapter
    }
}
