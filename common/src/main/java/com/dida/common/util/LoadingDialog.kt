package com.dida.common.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.dida.common.databinding.FragmentLoadingBinding

class LoadingDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "LoadingDialogFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return FragmentLoadingBinding.inflate(inflater, container, false).root
    }

    override fun onStart() {
        super.onStart()
        isCancelable = false
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setStyle(STYLE_NO_FRAME, android.R.style.Theme)
    }

}