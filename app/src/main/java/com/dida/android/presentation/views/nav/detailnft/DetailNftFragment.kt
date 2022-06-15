package com.dida.android.presentation.views.nav.detailnft

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dida.android.R
import com.dida.android.databinding.FragmentDetailNftBinding
import com.dida.android.domain.model.nav.detailnft.Comments
import com.dida.android.domain.model.nav.detailnft.Community
import com.dida.android.presentation.adapter.detailnft.CommunityAdapter
import com.dida.android.presentation.adapter.home.SoldOutAdapter
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.viewmodel.nav.detailnft.DetailNftViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailNftFragment : BaseFragment<FragmentDetailNftBinding, DetailNftViewModel>(com.dida.android.R.layout.fragment_detail_nft) {

    private val TAG = "DetailNftFragment"

    override val layoutResourceId: Int
        get() = com.dida.android.R.layout.fragment_detail_nft

    override val viewModel : DetailNftViewModel by viewModels()
    lateinit var navController: NavController
    lateinit var communityAdapter: CommunityAdapter


    override fun initStartView() {
        communityAdapter = CommunityAdapter()
        navController = Navigation.findNavController(requireView())

        binding.communityRecycler.run {
            adapter = communityAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        val item1 = Comments("test", "NFT 가 너무 이쁘네요~~~! 미아러ㅣㅏ어미라ㅓ")
        val item2 = Comments("test", "NFT 가 너무 이쁘네요~~~!")
        val commentsList = ArrayList<Comments>()
        commentsList.add(item1)
        commentsList.add(item2)
        val item3 = Community("test", "test", false, "test", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, ",
            "test", "NFT name here", "test", 1.65, commentsList
        )
        val communityList = ArrayList<Community>()
        communityList.add(item3)

        communityAdapter.addItem(item3)
        communityAdapter.notifyDataSetChanged()
    }

    override fun initAfterBinding() {
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }

        // move detail
        communityAdapter.nextItemClickListener(object : CommunityAdapter.OnItemClickEventListener {
            override fun onItemClick(a_view: View?, a_position: Int) {
            }
        })
    }
}