package com.dida.android.presentation.views.nav.detailnft

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dida.android.R
import com.dida.android.databinding.FragmentDetailNftBinding
import com.dida.android.presentation.adapter.detailnft.CommunityAdapter
import com.dida.android.presentation.base.BaseFragment
import com.dida.domain.model.nav.detailnft.Comments
import com.dida.domain.model.nav.detailnft.Community
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailNftFragment : BaseFragment<FragmentDetailNftBinding, DetailNftViewModel>(R.layout.fragment_detail_nft) {

    private val TAG = "DetailNftFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_detail_nft

    override val viewModel : DetailNftViewModel by viewModels()
    lateinit var navController: NavController
    lateinit var communityAdapter: CommunityAdapter


    override fun initStartView() {
        binding.vm = viewModel
        initToolbar()

        communityAdapter = CommunityAdapter()
        navController = Navigation.findNavController(requireView())

        binding.communityRecycler.adapter = communityAdapter
    }

    override fun initDataBinding() {
        val item1 = Comments("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT 가 너무 이쁘네요~~~! 미아러ㅣㅏ어미라ㅓ")
        val item2 = Comments("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT 가 너무 이쁘네요~~~!")
        val item = Comments("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "짧은글 테스트 짧은글 테스트 짧은글 테스트 짧은글 테스트 짧은글 테스트 짧은글 테스트 짧은글 테스트 짧은글 테스트")
        val commentsList = ArrayList<Comments>()
        commentsList.add(item)
        commentsList.add(item1)
        commentsList.add(item2)
        val item3 = Community("https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2",
            "test", false, "test", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, ",
            "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "NFT name here",
            "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", 1.65, commentsList
        )
        val communityList = ArrayList<Community>()
        communityList.add(item3)

        communityAdapter.submitList(communityList)
    }

    override fun initAfterBinding() {
//        binding.backBtn.setOnClickListener {
//            navController.popBackStack()
//        }
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            // 우측 메뉴
            this.inflateMenu(R.menu.menu_detailnft_toolbar)
            this.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_heart -> {
                    }
                    R.id.action_more -> {
                    }
                }
                true
            }

            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener {
                navController.popBackStack()
            }
        }
    }
}