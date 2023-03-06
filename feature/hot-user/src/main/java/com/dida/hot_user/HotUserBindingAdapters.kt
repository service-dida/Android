package com.dida.hot_user

import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter

@BindingAdapter("cardCount")
fun TextView.bindCardCount(count: Long) {
    val view = this
    view.text = "$count 작품"
}

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("hotUserFollowBtn")
fun TextView.bindHotUserFollowBtn(isFollow: Boolean) {
    val view = this
    if(isFollow) {
        view.text = context.getString(com.dida.common.R.string.user_following)
        view.setTextColor(context.getColor(com.dida.common.R.color.white))
        view.setBackgroundResource(com.dida.common.R.drawable.custom_surface6_radius100)
    } else {
        view.text = context.getString(com.dida.common.R.string.user_follow)
        view.setTextColor(context.getColor(com.dida.common.R.color.brand_lemon))
        view.setBackgroundResource(com.dida.common.R.drawable.custom_brandlemon_empty_radius100)
    }
}
