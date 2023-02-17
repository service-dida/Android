package com.dida.common.ui

import androidx.fragment.app.viewModels
import com.dida.common.R
import com.dida.common.base.BaseBottomSheetDialogFragment
import com.dida.common.databinding.DialogBottomImageBinding

class ImageBottomSheet(
    val callback: (clickGallery: Boolean) -> Unit
) : BaseBottomSheetDialogFragment<DialogBottomImageBinding,ImageBottomSheetViewModel>(){
    override val layoutResourceId: Int
        get() = R.layout.dialog_bottom_image

    override val viewModel: ImageBottomSheetViewModel by viewModels()
    override fun initStartView() {
        binding.apply {
            this.vm
            this.lifecycleOwner = viewLifecycleOwner
        }
    }
    override fun initDataBinding() {}
    override fun initAfterBinding() {
        binding.galleryBtn.setOnClickListener {
            callback.invoke(true)
            dismissAllowingStateLoss()
        }

        binding.captureBtn.setOnClickListener {
            callback.invoke(false)
            dismissAllowingStateLoss()
        }
    }
}
