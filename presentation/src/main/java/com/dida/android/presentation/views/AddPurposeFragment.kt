package com.dida.android.presentation.views

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.add.R
import com.dida.add.bottom.AddKeepNftBottomSheet
import com.dida.add.databinding.FragmentAddPurposeBinding
import com.dida.add.purpose.AddPurposeNavigationAction
import com.dida.add.purpose.AddPurposeViewModel
import com.dida.nft.sale.AddSaleNftBottomSheet
import com.dida.password.PasswordDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddPurposeFragment :
    BaseFragment<FragmentAddPurposeBinding, AddPurposeViewModel>(R.layout.fragment_add_purpose) {

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
        viewModel.initNFTInfo(getPath(args.imgURL), args.title, args.description)
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is AddPurposeNavigationAction.NavigateToNotSaled -> notSaled()
                    is AddPurposeNavigationAction.NavigateToSaled -> isSaled()
                    is AddPurposeNavigationAction.NavigateToMyPage -> navigate(
                        AddPurposeFragmentDirections.actionAddPurposeFragmentToMyPageFragment()
                    )
                }
            }
        }
    }

    override fun initAfterBinding() {}

    private fun initToolbar() {
        binding.toolbar.apply {
            this.setNavigationIcon(com.dida.common.R.drawable.ic_arrow_left)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun notSaled() {
        AddKeepNftBottomSheet {
            PasswordDialog(6, "비밀번호 입력", "6자리를 입력해주세요.") { success, password ->
                if (success) {
                    viewModel.mintNFT(password, AddPurposeViewModel.AddNftType.NOT_SALE, 0.0)
                } else {
                    if (password == "reset") {
                        navigate(AddPurposeFragmentDirections.actionAddPurposeFragmentToSettingFragment())
                    }
                }
            }.show(childFragmentManager, "AddNftBottomSheet")
        }.show(childFragmentManager, "AddPurposeFragment")
    }

    private fun isSaled() {
        val dialog = AddSaleNftBottomSheet {
            PasswordDialog(6, "비밀번호 입력", "6자리를 입력해주세요.") { success, password ->
                if (success) {
                    viewModel.mintNFT(
                        password,
                        AddPurposeViewModel.AddNftType.SALE,
                        it.toDouble()
                    )
                }else {
                    if (password == "reset") {
                        navigate(AddPurposeFragmentDirections.actionAddPurposeFragmentToSettingFragment())
                    }
                }
            }.show(childFragmentManager, "AddNftBottomSheet")
        }
        dialog.show(childFragmentManager, "AddPurposeFragment")
    }

    @SuppressLint("Range")
    private fun getPath(uri: String): String {
        val cursor: Cursor? =
            requireContext().contentResolver.query(Uri.parse(uri), null, null, null, null)
        cursor?.moveToNext()
        val path: String? = cursor?.getString(cursor.getColumnIndex("_data"))
        cursor?.close()
        return path ?: ""
    }
}
