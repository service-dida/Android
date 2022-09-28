package com.dida.android.presentation.views.nav.add

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.android.databinding.FragmentAddPurposeBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.views.nav.add.addnftprice.AddNftPriceBottomSheet
import com.dida.android.presentation.views.password.PasswordDialog
import dagger.hilt.android.AndroidEntryPoint


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
    }

    override fun initAfterBinding() {
        binding.type1Button.setOnClickListener {
            viewModel.changePurposeType(1)
            val dialog = AddNftBottomSheet(){
                val passwordDialog = PasswordDialog(true) { password ->
                    //TODO : 비밀번호 맞는지 체크하기
                    val currentImageUri = Uri.parse(viewModel.nftImageLiveData.value.toString())
                    try {
                        currentImageUri?.let {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                val imagePath: String = getPath(currentImageUri)!!
                                viewModel.uploadAsset(imagePath)
                            }else{
                                //TODO :버전낮은거 처리하기
                            }
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

    @SuppressLint("Range")
    private fun getPath(uri: Uri): String {
        val cursor: Cursor? = requireContext().contentResolver.query(uri, null, null, null, null )
        cursor?.moveToNext()
        val path: String? = cursor?.getString(cursor.getColumnIndex("_data"))
        cursor?.close()
        return path?:""
    }
}