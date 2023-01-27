package com.dida.android.presentation.views

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.add.bottom.AddKeepNftBottomSheet
import com.dida.add.bottom.AddSaleNftBottomSheet
import com.dida.add.databinding.FragmentAddPurposeBinding
import com.dida.add.purpose.AddPurposeNavigationAction
import com.dida.add.purpose.AddPurposeViewModel
import com.dida.add.R
import com.dida.android.presentation.views.password.PasswordDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AddPurposeFragment : BaseFragment<FragmentAddPurposeBinding, AddPurposeViewModel>(R.layout.fragment_add_purpose) {

    private val TAG = "AddPurposeFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_add_purpose

    override val viewModel: AddPurposeViewModel by viewModels()

    private val args: AddPurposeFragmentArgs by navArgs()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        viewModel.initNFTInfo(getPath(args.imgURL),args.title,args.description)
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when(it) {
                    is AddPurposeNavigationAction.NavigateToNotSaled -> notSaled()
                    is AddPurposeNavigationAction.NavigateToSaled -> isSaled()
                    is AddPurposeNavigationAction.NavigateToMyPage -> navigate(AddPurposeFragmentDirections.actionAddPurposeFragmentToMyPageFragment())
                }
            }
        }
    }
    override fun initAfterBinding() {}

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.android.R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun notSaled() {
        AddKeepNftBottomSheet {
            PasswordDialog(6,"비밀번호 입력","6자리를 입력해주세요."){ success, password ->
                if(success){
                    viewModel.mintNFT(password)
                }else{

                }
            }.show(childFragmentManager,"AddNftBottomSheet")
        }.show(childFragmentManager,"AddPurposeFragment")
    }

    private fun isSaled() {
        val dialog = AddSaleNftBottomSheet {
        }
        dialog.show(childFragmentManager, "AddPurposeFragment")
    }

    @SuppressLint("Range")
    private fun getPath(uri: String): String {
        val cursor: Cursor? = requireContext().contentResolver.query(Uri.parse(uri), null, null, null, null )
        cursor?.moveToNext()
        val path: String? = cursor?.getString(cursor.getColumnIndex("_data"))
        cursor?.close()
        return path?:""
    }
}
