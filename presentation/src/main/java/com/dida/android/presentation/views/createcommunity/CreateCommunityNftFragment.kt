package com.dida.android.presentation.views.createcommunity

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.android.databinding.FragmentCreateCommunityBinding
import com.dida.android.databinding.FragmentCreateCommunityNftBinding
import com.dida.android.databinding.FragmentDetailCommunityBinding
import com.dida.android.presentation.adapter.community.CreateCommunityNftPagerAdapter
import com.dida.android.presentation.adapter.detailnft.CommunityAdapter
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.views.nav.community.CommunityViewModel
import com.dida.domain.model.nav.community.CreateCommunityNft
import com.dida.domain.model.nav.detailnft.Comments
import com.dida.domain.model.nav.detailnft.Community
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCommunityNftFragment(
    val createNftState: Int
) : BaseFragment<FragmentCreateCommunityNftBinding, CreateCommunityNftViewModel>(R.layout.fragment_create_community_nft) {

    private val TAG = "CreateCommunityNftFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_create_community_nft

    override val viewModel : CreateCommunityNftViewModel by viewModels()

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
        
    }
}