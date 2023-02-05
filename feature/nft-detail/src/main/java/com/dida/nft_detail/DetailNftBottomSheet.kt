package com.dida.nft_detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

enum class DetailNftMenuType{
    SELL,
    CANCEL,
    REMOVE
}
class DetailNftBottomSheet(
    val callback: (clickGallery: DetailNftMenuType) -> Unit
) : BottomSheetDialogFragment(){
    private lateinit var dlg : BottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 이 코드를 실행하지 않으면 XML에서 round 처리를 했어도 적용되지 않는다.
        dlg = ( super.onCreateDialog(savedInstanceState).apply {
            // window?.setDimAmount(0.2f) // Set dim amount here
            setOnShowListener {
                val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)

                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.isDraggable = true
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        } ) as BottomSheetDialog
        return dlg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_bottom_detail_nft, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sell = requireView().findViewById<ConstraintLayout>(R.id.sell_btn)
        val cancel = requireView().findViewById<ConstraintLayout>(R.id.cancel_btn)
        val remove = requireView().findViewById<ConstraintLayout>(R.id.remove_btn)

        sell.setOnClickListener {
            callback.invoke(DetailNftMenuType.SELL)
            dismiss()
        }

        cancel.setOnClickListener {
            callback.invoke(DetailNftMenuType.CANCEL)
            dismiss()
        }

        remove.setOnClickListener {
            callback.invoke(DetailNftMenuType.REMOVE)
            dismiss()
        }
    }
}
