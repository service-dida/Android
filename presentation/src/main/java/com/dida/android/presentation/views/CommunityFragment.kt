package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.common.adapter.CommunityAdapter
import com.dida.community.CommunityViewModel
import com.dida.community.adapter.ActiveNFTRecyclerViewAdapter
import com.dida.community.adapter.ReservationNFTRecyclerViewAdapter
import com.dida.community.databinding.FragmentCommunityBinding
import com.dida.domain.model.nav.community.ActiveNFTHolderModel
import com.dida.domain.model.nav.community.ReservationNFTHolderModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding, CommunityViewModel>(com.dida.community.R.layout.fragment_community) {

    private val TAG = "CommunityFragment"

    override val layoutResourceId: Int
        get() = com.dida.community.R.layout.fragment_community

    override val viewModel : CommunityViewModel by viewModels()
    private val activeNFTRecyclerViewAdapter = ActiveNFTRecyclerViewAdapter()
    private val reservationNFTRecyclerViewAdapter = ReservationNFTRecyclerViewAdapter()
    private val communityAdapter by lazy { CommunityAdapter(viewModel) }


    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initRecyclerView()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.navigationEvent.collectLatest {
                    when(it) {
                        is com.dida.community.CommunityNavigationAction.NavigateToDetail -> navigate(CommunityFragmentDirections.actionCommunityFragmentToCommunityDetailFragment(it.communityId))
                        is com.dida.community.CommunityNavigationAction.NavigateToCommunityWrite -> navigate(CommunityFragmentDirections.actionCommunityFragmentToCreateCommunityFragment())
                    }
                }
            }

            launch {
                viewModel.postsState.collectLatest {
                    communityAdapter.submitList(it)
                }
            }

        }
    }

    override fun initAfterBinding() {
    }

    private fun initRecyclerView(){
        val Reservationlist = mutableListOf(
            ReservationNFTHolderModel("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here","NFT name here","23:00"),
            ReservationNFTHolderModel("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here","NFT name here","23:00"),
            ReservationNFTHolderModel("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here","NFT name here","23:00")
        )

        reservationNFTRecyclerViewAdapter.submitList(Reservationlist)
        binding.reservationRecyclerView.adapter = reservationNFTRecyclerViewAdapter

        val ActiveNFTList = mutableListOf(
            ActiveNFTHolderModel("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2"),
            ActiveNFTHolderModel("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2"),
            ActiveNFTHolderModel("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2")
        )

        activeNFTRecyclerViewAdapter.submitList(ActiveNFTList)
        binding.activeCommunityRecyclerView.adapter = activeNFTRecyclerViewAdapter
        binding.communityRecyclerView.adapter = communityAdapter
    }
}
