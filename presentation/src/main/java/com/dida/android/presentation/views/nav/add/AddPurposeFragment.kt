package com.dida.android.presentation.views.nav.add

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.android.databinding.FragmentAddPurposeBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.views.password.PasswordReconfirmDialog
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
        binding.type1Button.setOnClickListener {
            viewModel.changePurposeType(1)
            val dialog = AddNftBottomSheet(::type1)
            dialog.show(childFragmentManager, "AddPurposeFragment")
        }
        binding.type2Button.setOnClickListener {
            viewModel.changePurposeType(2)
            val dialog = AddNftPriceBottomSheet(::type2)
            dialog.show(childFragmentManager, "AddPurposeFragment")
        }
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    //소장용
    private fun type1(int : Int){

    }
    //판매용
    private fun type2(string : String){

    }
}