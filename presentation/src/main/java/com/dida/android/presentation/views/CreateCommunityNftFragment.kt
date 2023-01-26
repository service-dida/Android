package com.dida.android.presentation.views

import androidx.fragment.app.viewModels
import com.dida.create_community.adapter.CreateCommunityNftAdapter
import com.dida.create_community.databinding.FragmentCreateCommunityNftBinding
import com.dida.domain.model.nav.community.CreateCommunityNft
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCommunityNftFragment(
    val createNftState: Int
) : BaseFragment<FragmentCreateCommunityNftBinding, com.dida.create_community.CreateCommunityViewModel>(com.dida.create_community.R.layout.fragment_create_community_nft) {

    private val TAG = "CreateCommunityNftFragment"

    override val layoutResourceId: Int
        get() = com.dida.create_community.R.layout.fragment_create_community_nft

    override val viewModel : com.dida.create_community.CreateCommunityViewModel by viewModels({ requireParentFragment() })

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initAdapter()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

    private fun initAdapter() {
        val testLikeList = arrayListOf(
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","좋아요 한 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","좋아요 한 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","좋아요 한 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","좋아요 한 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","좋아요 한 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","좋아요 한 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","좋아요 한 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","좋아요 한 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","좋아요 한 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","좋아요 한 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
        )

        val testMyList = arrayListOf(
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","나의 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","나의 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","나의 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","나의 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","나의 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","나의 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","나의 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","나의 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","나의 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
            CreateCommunityNft(1, "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","나의 nft name here","https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2","user name here"),
        )

        val testAdapter = CreateCommunityNftAdapter(viewModel)
        binding.recyclerNft.adapter = testAdapter

        when(createNftState) {
            0 -> testAdapter.submitList(testLikeList)
            1 -> testAdapter.submitList(testMyList)
        }
    }
}
