package com.dida.android.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.dida.android.util.LoadingDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseBottomSheetDialogFragment<T: ViewDataBinding, R : BaseViewModel>: BottomSheetDialogFragment() {

    private var _binding: T? = null
    val binding get()= requireNotNull(_binding)

    abstract val layoutResourceId: Int

    abstract val viewModel: R

    abstract fun initStartView()

    abstract fun initDataBinding()

    abstract fun initAfterBinding()

    private var isSetBackButtonValid = false

    lateinit var mLoadingDialog: LoadingDialog

    protected var exception: SharedFlow<Throwable>? = null
    protected var toast: Toast? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.dida.android.R.style.Theme_DIDA_BottomDialogAboveKeyboard)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            exception?.collect { exception ->
                showToastMessage(exception)
            }
        }
        settingBottomSheetDialog()
        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun settingBottomSheetDialog(){
        // 팝업 생성 시 전체화면으로 띄우기
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        if(bottomSheet != null) {
            val behavior = BottomSheetBehavior.from<View>(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED

            //바텀시트 다이얼로그 드래그 막기
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if(newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            })
        }
    }

    // Toast Message 관련 함수
    fun showToastMessage(e: Throwable?) {
        toast?.cancel()
        toast = Toast.makeText(activity, e?.message, Toast.LENGTH_SHORT)?.apply { show() }
    }

    // Toast Message 관련 함수
    fun toastMessage(message: String) {
        toast?.cancel()
        toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)?.apply { show() }
    }
}