package com.dida.android.util

import android.content.Intent
import androidx.fragment.app.Fragment
import com.dida.android.presentation.activities.NavHostActivity


fun Fragment.toLoginSuccess() {
    var intent = Intent(requireActivity(), NavHostActivity::class.java)
    requireActivity().let { activity ->
        activity.setResult(9001, intent)
        activity.finish()
    }
}
