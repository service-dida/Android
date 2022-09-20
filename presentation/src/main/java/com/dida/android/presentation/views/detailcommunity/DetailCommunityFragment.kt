package com.dida.android.presentation.views.detailcommunity

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.android.databinding.FragmentCommunityBinding
import com.dida.android.databinding.FragmentDetailCommunityBinding
import com.dida.android.presentation.adapter.community.ActiveNFTRecyclerViewAdapter
import com.dida.android.presentation.adapter.community.ReservationNFTRecyclerViewAdapter
import com.dida.android.presentation.adapter.detailnft.CommunityAdapter
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.views.nav.community.CommunityViewModel
import com.dida.domain.model.nav.community.ActiveNFTHolderModel
import com.dida.domain.model.nav.community.ReservationNFTHolderModel
import com.dida.domain.model.nav.detailnft.Comments
import com.dida.domain.model.nav.detailnft.Community
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DetailCommunityFragment : BaseFragment<FragmentDetailCommunityBinding, DetailCommunityViewModel>(R.layout.fragment_detail_community) {

    private val TAG = "DetailCommunityFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_detail_community

    override val viewModel : DetailCommunityViewModel by viewModels()
    private val communityViewModel : CommunityViewModel by viewModels()
    private val navController by lazy { findNavController() }

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
    }

    override fun initAfterBinding() {

    }

    private fun initToolbar(){
        binding.toolbar.apply {
            this.title = resources.getString(R.string.detail_community_title)
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener {
                navController.popBackStack()
            }
        }
    }

    private fun initAdapter() {
        val commentList = mutableListOf(
            Comments("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT 가 너무 이쁘네요~~~! 미아러ㅣㅏ어미라ㅓ"),
            Comments("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT 가 너무 이쁘네요~~~! 미아러ㅣㅏ어미라ㅓ"),
            Comments("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT 가 너무 이쁘네요~~~! 미아러ㅣㅏ어미라ㅓ"),
            Comments("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT 가 너무 이쁘네요~~~! 미아러ㅣㅏ어미라ㅓ"),
            Comments("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT 가 너무 이쁘네요~~~! 미아러ㅣㅏ어미라ㅓ")
        )

        val test = CommunityAdapter(communityViewModel)
        val testList = listOf(
            Community("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2",
            "test", false, "test", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, ",
            "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT name here",
            "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", 1.65, commentList
            )
        )

        binding.detailCommunityMain.adapter = test
        test.submitList(testList)
    }
}