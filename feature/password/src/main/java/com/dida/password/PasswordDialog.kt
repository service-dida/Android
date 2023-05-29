package com.dida.password

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.get
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dida.common.base.BaseBottomSheetDialogFragment
import com.dida.common.dialog.CentralDialogFragment
import com.dida.common.dialog.VerticalDialogFragment
import com.dida.password.databinding.DialogPasswordBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PasswordDialog(
    private val size: Int,
    private val mainTitleStr: String,
    private val subTitleStr: String,
    private val settingYn : Boolean = false,
    private val showFindPwdBtn : Boolean = true,
    private val result: (Boolean, String) -> Unit
) : BaseBottomSheetDialogFragment<DialogPasswordBinding, PasswordViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.dialog_password

    override val viewModel: PasswordViewModel by viewModels()

    private val imageViewList: MutableList<ImageView> = mutableListOf()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
            this.mainTitle = mainTitleStr
            this.subTitle = subTitleStr
            this.findPasswordBtn.visibility =
                if(showFindPwdBtn){
                    View.VISIBLE
                }else {
                    View.GONE
                }
        }
        exception = viewModel.errorEvent
        viewModel.initPwdInfo(size, settingYn)
        makePasswordDial()
        dialogFullScreen()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initDataBinding() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.stackSizeState.collect {
                    checkImageType(it)
                    if(it!=0){
                        buttonVibrator()
                    }
                }
            }

            launch {
                viewModel.completeEvent.collectLatest {
                    result.invoke(true, it)
                    dismiss()
                }
            }

            launch {
                viewModel.failEvent.collectLatest {
                    failAction(it)
                }
            }

            launch {
                viewModel.dismissEvent.collectLatest {
                    VerticalDialogFragment.Builder()
                        .title(getString(R.string.wrong_password_mainTitle))
                        .message(getString(R.string.wrong_password_subTitle))
                        .positiveButton(getString(R.string.wrong_password_positive), object : VerticalDialogFragment.OnClickListener {
                            override fun onClick() {
                                result.invoke(false,"reset")
                            }
                        })
                        .negativeButton(getString(R.string.wrong_password_negative), object : VerticalDialogFragment.OnClickListener {
                            override fun onClick() {
                                result.invoke(false,"")
                            }
                        })
                        .build()
                        .show(childFragmentManager, "log_out_dialog")
                }
            }
        }
    }

    override fun initAfterBinding() {
        binding.findPasswordBtn.setOnClickListener {
            result.invoke(false,"reset")
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        result.invoke(false, "")
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

    private fun makePasswordDial() {
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT, 1f
        )

        imageViewList.clear()
        binding.passwordDialLayout.removeAllViews()

        for (i in 0 until size) {
            val imageView = ImageView(context)
            imageView.setImageResource(R.drawable.ic_password_default)
            imageView.layoutParams = layoutParams
            binding.passwordDialLayout.addView(imageView)
            imageViewList.add(imageView)
        }
    }

    private fun checkImageType(stackSize: Int) {
        for (i in 0 until size) {
            val imageView = binding.passwordDialLayout[i] as ImageView
            if (i < stackSize) {
                imageView.setImageResource(R.drawable.ic_password)
            } else {
                imageView.setImageResource(R.drawable.ic_password_default)
            }
        }
    }

    private fun failAction(fail : Boolean){
        if (fail) {
            imageViewList.forEach {
                it.setImageResource(R.drawable.ic_password_fail)
            }
            val animation =
                AnimationUtils.loadAnimation(context, R.anim.left_right_shake)
            binding.passwordDialLayout.startAnimation(animation)
            binding.mainTitleTv.text = "비밀번호가 일치하지 않아요\n" + "다시 눌러주세요"
            binding.subTitleTv.visibility = View.GONE
            binding.worngCountTv.visibility = View.VISIBLE

        } else {
            makePasswordDial()
            binding.passwordDialLayout.clearAnimation()
            binding.mainTitleTv.text = mainTitleStr
            binding.subTitleTv.visibility = View.VISIBLE
            binding.worngCountTv.visibility = View.GONE
        }
    }

    private fun buttonVibrator(){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = activity?.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                val vibrator = vibratorManager.defaultVibrator
                vibrator.vibrate(VibrationEffect.createOneShot(50, 50));
            } else {
                val vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(1000);
            }
    }
}
