package com.dida.android.presentation.views.nav.add

import android.R.attr.data
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.CalendarContract.Attendees.query
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dida.android.R
import com.dida.android.databinding.FragmentAddPurposeBinding
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.util.AppLog
import com.dida.android.util.BitmapImageToCache
import dagger.hilt.android.AndroidEntryPoint
import java.net.URL


@AndroidEntryPoint
class AddPurposeFragment : BaseFragment<FragmentAddPurposeBinding, AddPurposeViewModel>(R.layout.fragment_add_purpose) {

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
            val dialog = AddNftBottomSheet(){
                val currentImageUri = Uri.parse(viewModel.nftImageLiveData.value.toString())

                try {
                    currentImageUri?.let {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                            val imagePath: String = getPath(currentImageUri)!!
                            val imageFileName = imagePath.split("/").last()

                            viewModel.uploadAsset(imageFileName, imagePath)

                        }
                    }
                }catch (e : Exception){

                }


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
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
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