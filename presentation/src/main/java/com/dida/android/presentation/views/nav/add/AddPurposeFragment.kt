package com.dida.android.presentation.views.nav.add

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.android.databinding.FragmentAddPurposeBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.views.nav.add.addnftprice.AddNftPriceBottomSheet
import com.dida.android.presentation.views.password.PasswordDialog
import com.dida.android.util.UriToFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class AddPurposeFragment : BaseFragment<FragmentAddPurposeBinding, AddPurposeViewModel>(R.layout.fragment_add_purpose) {

    private val TAG = "AddPurposeFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_add_purpose

    override val viewModel: AddPurposeViewModel by viewModels()

    private val args: AddPurposeFragmentArgs by navArgs()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        viewModel.initNFTInfo(args.imgURL,args.title,args.description)
        initToolbar()
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenStarted {
            viewModel.successCreateNft.collect {
                if(it) { navigate(AddPurposeFragmentDirections.actionAddPurposeFragmentToMyPageFragment()) }
            }
        }
    }

    override fun initAfterBinding() {
        binding.type1Button.setOnClickListener {
            viewModel.changePurposeType(1)
            val dialog = AddNftBottomSheet{
                val passwordDialog = PasswordDialog(true) { password ->
                    //TODO : 비밀번호 맞는지 체크하기
                    val currentImageUri = Uri.parse(viewModel.nftImageLiveData.value.toString())
                    try {
                        currentImageUri?.let {
                            viewModel.uploadAsset(UriToFile(currentImageUri,requireContext()))
                        }
                    }catch (e : Exception){

                    }
                }
                passwordDialog.show(requireActivity().supportFragmentManager, passwordDialog.tag)
            }
            dialog.show(childFragmentManager, "AddPurposeFragment")

        }

        binding.type2Button.setOnClickListener {
            viewModel.changePurposeType(2)
            val dialog = AddNftPriceBottomSheet(){

            }
            dialog.show(childFragmentManager, "AddPurposeFragment")
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}