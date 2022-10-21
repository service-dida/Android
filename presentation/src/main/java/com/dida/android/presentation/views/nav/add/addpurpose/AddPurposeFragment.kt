package com.dida.android.presentation.views.nav.add.addpurpose

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.android.databinding.FragmentAddPurposeBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.views.nav.add.AddNftBottomSheet
import com.dida.android.presentation.views.nav.add.addnftprice.AddNftPriceBottomSheet
import com.dida.android.presentation.views.password.PasswordDialog
import com.dida.android.util.AppLog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


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
        viewModel.initNFTInfo(args.imgURL,args.title,args.description)
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collect {
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
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    @SuppressLint("Range")
    private fun getPath(uri: Uri): String {
        val cursor: Cursor? = requireContext().contentResolver.query(uri, null, null, null, null )
        cursor?.moveToNext()
        val path: String? = cursor?.getString(cursor.getColumnIndex("_data"))
        cursor?.close()
        return path?:""
    }

    private fun notSaled() {
        val dialog = AddNftBottomSheet {
            val passwordDialog = PasswordDialog(true) { password ->
                //TODO : 비밀번호 맞는지 체크하기
                val currentImageUri = Uri.parse(viewModel.nftImageState.value)
                if(password != "") {
                    try {
                        currentImageUri?.let {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                val imagePath: String = getPath(currentImageUri)!!
                                viewModel.uploadAsset(imagePath)
                            } else {
                                //TODO :버전낮은거 처리하기
                            }
                        }
                    } catch (e : Exception){
                        AppLog.e("error_response", e.toString())
                    }
                }
            }
            passwordDialog.show(requireActivity().supportFragmentManager, passwordDialog.tag)
        }
        dialog.show(childFragmentManager, "AddPurposeFragment")
    }

    private fun isSaled() {
        val dialog = AddNftPriceBottomSheet {
        }
        dialog.show(childFragmentManager, "AddPurposeFragment")
    }
}