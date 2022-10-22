package com.dida.android.presentation.views.password

import android.content.DialogInterface
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.android.R
import com.dida.android.databinding.DialogInputNumberBinding
import com.dida.android.presentation.base.BaseBottomSheetDialogFragment
import com.dida.android.util.AppLog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputNumberDialog(
    private val size : Int,
    private val mainTitleStr : String,
    private val subTitleStr : String,
    private val result: (Boolean, String) -> Unit) : BaseBottomSheetDialogFragment<DialogInputNumberBinding, InputNumberViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.dialog_input_number

    override val viewModel: InputNumberViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
            this.mainTitle = mainTitleStr
            this.subTitle = subTitleStr
        }
        exception = viewModel.errorEvent
        viewModel.setStackSize(size)
        makePasswordDial()
        dialogFullScreen()
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenStarted {
            viewModel.stackSizeState.collect{
                checkImageType(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.completeEvent.collect {
                result.invoke(true,it)
                dismiss()
            }
        }
    }

    override fun initAfterBinding() {}

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        result.invoke(false,"")
    }
    private fun dialogFullScreen() {
        if (dialog != null) {
            val bottomSheet: View =
                dialog!!.findViewById(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        val view = view
        view!!.post {
            val parent = view!!.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
            bottomSheetBehavior!!.peekHeight = view!!.measuredHeight
            parent.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun makePasswordDial(){
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,1f)

        for(i in 0 until size){
            val imageView = ImageView(context)
            imageView.setImageResource(R.drawable.ic_password_default)
            imageView.layoutParams= layoutParams
            binding.passwordDialLayout.addView(imageView)
        }
    }

    private fun checkImageType(stackSize : Int){
        for(i in 0 until size){
            val imageView = binding.passwordDialLayout[i] as ImageView
            if(i < stackSize){
                imageView.setImageResource(R.drawable.ic_password)
            }else{
                imageView.setImageResource(R.drawable.ic_password_default)
            }
        }
    }
}