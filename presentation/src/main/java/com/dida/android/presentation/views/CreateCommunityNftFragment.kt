package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.create_community.adapter.CreateCommunityNftAdapter
import com.dida.create_community.databinding.FragmentCreateCommunityNftBinding
import com.dida.domain.model.nav.community.CreateCommunityNft
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateCommunityNftFragment(
    val createNftState: Int
) : BaseFragment<FragmentCreateCommunityNftBinding, com.dida.create_community.CreateCommunityViewModel>(com.dida.create_community.R.layout.fragment_create_community_nft) {

    private val TAG = "CreateCommunityNftFragment"

    override val layoutResourceId: Int
        get() = com.dida.create_community.R.layout.fragment_create_community_nft

    override val viewModel : com.dida.create_community.CreateCommunityViewModel by viewModels({ requireParentFragment() })
    private val cardsAdapter by lazy { CreateCommunityNftAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initAdapter()
        viewModel.getCardsPostMy()
        viewModel.getCardsPostLike()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.cardPostLikeState.collectLatest {
                    if(createNftState == 0) cardsAdapter.submitList(it)
                }
            }

            launch {
                viewModel.cardPostMyState.collectLatest {
                    if(createNftState == 1) cardsAdapter.submitList(it)
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
