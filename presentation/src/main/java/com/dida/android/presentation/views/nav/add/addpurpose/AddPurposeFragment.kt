package com.dida.android.presentation.views.nav.add.addpurpose

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.android.databinding.FragmentAddPurposeBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.views.nav.add.AddNftBottomSheet
import com.dida.android.presentation.views.nav.add.addnftprice.AddNftPriceBottomSheet
import com.dida.android.presentation.views.password.InputNumberDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


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
            launch {
                viewModel.navigationEvent.collect {
                    when(it) {
                        is AddPurposeNavigationAction.NavigateToNotSaled -> notSaled()
                        is AddPurposeNavigationAction.NavigateToSaled -> isSaled()
                        is AddPurposeNavigationAction.NavigateToMyPage -> navigate(AddPurposeFragmentDirections.actionAddPurposeFragmentToMyPageFragment())
                    }
                }
            }

            launch {
                viewModel.checkPasswordState.collect{
                    if(!it){
                        toastMessage("비밀번호가 틀렸습니다.")
                        InputNumberDialog(6,"비밀번호 설정","본인 확인 시 사용됩니다."){success, password ->
                            if(success){
                                viewModel.checkPassword(password)
                            }else{
                                navController.popBackStack()
                            }
                        }.show(childFragmentManager,"AddFragment")
                    }
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
    private fun getPath(uri: String): String {
        val cursor: Cursor? = requireContext().contentResolver.query(Uri.parse(uri), null, null, null, null )
        cursor?.moveToNext()
        val path: String? = cursor?.getString(cursor.getColumnIndex("_data"))
        cursor?.close()
        return path?:""
    }

    private fun notSaled() {
        AddNftBottomSheet {
            InputNumberDialog(6,"비밀번호 입력","6자리를 입력해주세요."){success, password ->
                if(success){
                    viewModel.checkPassword(password)
                }else{

                }
            }.show(childFragmentManager,"AddNftBottomSheet")
        }.show(childFragmentManager,"AddPurposeFragment")
    }

    private fun isSaled() {
        val dialog = AddNftPriceBottomSheet {
        }
        dialog.show(childFragmentManager, "AddPurposeFragment")
    }
}