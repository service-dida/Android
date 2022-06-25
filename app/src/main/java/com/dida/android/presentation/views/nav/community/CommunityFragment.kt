package com.dida.android.presentation.views.nav.community

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dida.android.R
import com.dida.android.databinding.FragmentCommunityBinding
import com.dida.android.domain.model.nav.community.ActiveNFTHolderModel
import com.dida.android.domain.model.nav.community.ReservationNFTHolderModel
import com.dida.android.domain.model.nav.detailnft.Comments
import com.dida.android.domain.model.nav.detailnft.Community
import com.dida.android.domain.model.nav.mypage.WalletNFTHistoryHolderModel
import com.dida.android.presentation.adapter.community.ActiveNFTRecyclerViewAdapter
import com.dida.android.presentation.adapter.community.ReservationNFTRecyclerViewAdapter
import com.dida.android.presentation.adapter.detailnft.CommunityAdapter
import com.dida.android.presentation.adapter.mypage.WalletNFTHistoryRecyclerViewAdapter
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.viewmodel.nav.community.CommunityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding, CommunityViewModel>(R.layout.fragment_community) {

    private val TAG = "CommunityFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_community

    override val viewModel : CommunityViewModel by viewModels()


    override fun initStartView() {
        binding.vm = viewModel
        initToolbar()
        initRecyclerView()
    }

    override fun initDataBinding() {

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

        binding.reservationRecyclerView.apply {
            adapter = ReservationNFTRecyclerViewAdapter(Reservationlist)
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }

        val ActiveNFTList = mutableListOf(
            ActiveNFTHolderModel("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2"),
            ActiveNFTHolderModel("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2"),
            ActiveNFTHolderModel("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2")
        )

        binding.activeCommunityRecyclerView.apply {
            adapter = ActiveNFTRecyclerViewAdapter(ActiveNFTList)
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }

        val commentList = mutableListOf(
            Comments("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT 가 너무 이쁘네요~~~! 미아러ㅣㅏ어미라ㅓ"),
            Comments("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT 가 너무 이쁘네요~~~! 미아러ㅣㅏ어미라ㅓ")
        )

        binding.communityRecyclerView.apply {
            adapter = CommunityAdapter()
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }
        val adapter = binding.communityRecyclerView.adapter as CommunityAdapter
        adapter.addItem(Community("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2",
            "test", false, "test", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, ",
            "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT name here",
            "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", 1.65, commentList
        ))
        adapter.addItem(Community("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2",
            "test", false, "test", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, ",
            "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT name here",
            "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", 1.65, commentList
        ))
        adapter.notifyDataSetChanged()

    }
}