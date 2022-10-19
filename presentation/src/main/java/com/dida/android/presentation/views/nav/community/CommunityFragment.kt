package com.dida.android.presentation.views.nav.community

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.android.R
import com.dida.android.databinding.FragmentCommunityBinding
import com.dida.android.presentation.adapter.community.ActiveNFTRecyclerViewAdapter
import com.dida.android.presentation.adapter.community.ReservationNFTRecyclerViewAdapter
import com.dida.android.presentation.adapter.detailnft.CommunityAdapter
import com.dida.android.presentation.base.BaseFragment
import com.dida.domain.model.nav.community.ActiveNFTHolderModel
import com.dida.domain.model.nav.community.ReservationNFTHolderModel
import com.dida.domain.model.nav.detailnft.Comments
import com.dida.domain.model.nav.detailnft.Community
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding, CommunityViewModel>(R.layout.fragment_community) {

    private val TAG = "CommunityFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_community

    override val viewModel : CommunityViewModel by viewModels()
    private val activeNFTRecyclerViewAdapter = ActiveNFTRecyclerViewAdapter()
    private val reservationNFTRecyclerViewAdapter = ReservationNFTRecyclerViewAdapter()


    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initRecyclerView()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collect {
                when(it) {
                    is CommunityNavigationAction.NavigateToDetail -> { navigate(CommunityFragmentDirections.actionCommunityFragmentToCommunityDetailFragment()) }
                    is CommunityNavigationAction.NavigateToCommunityWrite -> {}
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar(){
        binding.toolbar.inflateMenu(R.menu.menu_community_toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_search ->{
                    //TODO : 검색 이벤트 작성
                }
            }
            true
        }
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

        val commentList = mutableListOf(
            Comments("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT 가 너무 이쁘네요~~~! 미아러ㅣㅏ어미라ㅓ"),
            Comments("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT 가 너무 이쁘네요~~~! 미아러ㅣㅏ어미라ㅓ")
        )

        val test = CommunityAdapter(viewModel)
        val testList = ArrayList<Community>()
        for(i in 0..3) {
            testList.add(Community("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2",
                "test", false, "test", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, ",
                "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT name here",
                "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", 1.65, commentList
            ))
        }
        
        test.submitList(testList)
        binding.communityRecyclerView.adapter = test
    }
}