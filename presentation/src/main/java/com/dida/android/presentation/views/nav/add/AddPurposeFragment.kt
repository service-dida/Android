package com.dida.android.presentation.views.nav.add

import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.android.databinding.FragmentAddPurposeBinding
import com.dida.android.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPurposeFragment :
    BaseFragment<FragmentAddPurposeBinding, AddPurposeViewModel>(R.layout.fragment_add_purpose) {

    private val TAG = "AddPurposeFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_add_purpose

    override val viewModel: AddPurposeViewModel by viewModels()

    val args: AddPurposeFragmentArgs by navArgs()

    override fun initStartView() {
        binding.vm = viewModel
        viewModel.initNFTInfo(args.imgURL,args.title,args.description)
        initToolbar()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}