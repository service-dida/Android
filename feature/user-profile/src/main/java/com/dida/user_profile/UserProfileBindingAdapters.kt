package com.dida.user_profile

import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.model.main.OtherUserProfie

@BindingAdapter("otherUserProfile")
fun ImageView.bindOtherUserProfile(uiState: UiState<OtherUserProfie>) {
    uiState.successOrNull()?.let {
        if (it.profileUrl.isEmpty().not()) {
            Glide.with(context)
                .load(it.profileUrl)
                .placeholder(com.dida.common.R.mipmap.img_dida_logo_foreground)
                .error(com.dida.common.R.mipmap.img_dida_logo_foreground)
                .transform(CenterCrop(), RoundedCorners(1000))
                .into(this)
        } else {
            Glide.with(context)
                .load(com.dida.common.R.mipmap.img_dida_logo_foreground)
                .transform(CenterCrop(), RoundedCorners(1000))
                .into(this)
        }
    }
}

@BindingAdapter("otherUserNickname")
fun TextView.bindOtherUserNickname(uiState: UiState<OtherUserProfie>) {
    this.text = uiState.successOrNull()?.nickname
}

@BindingAdapter("otherUserDescription")
fun TextView.bindOtherUserDescription(uiState: UiState<OtherUserProfie>) {
    this.text = uiState.successOrNull()?.description
}

@BindingAdapter("otherUserNftCount")
fun TextView.bindOtherUserNftCount(uiState: UiState<OtherUserProfie>) {
    this.text = uiState.successOrNull()?.cardCnt.toString()
}

@BindingAdapter("otherUserFollwingCount")
fun TextView.bindOtherUserFollwingCount(uiState: UiState<OtherUserProfie>) {
    this.text = uiState.successOrNull()?.followingCnt.toString()
}

@BindingAdapter("otherUserFollwerCount")
fun TextView.bindOtherUserFollwerCount(uiState: UiState<OtherUserProfie>) {
    this.text = uiState.successOrNull()?.followerCnt.toString()
}

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("otherUserFollowBtn")
fun TextView.bindOtherUserFollowBtn(uiState: UiState<OtherUserProfie>) {
    val view = this
    var isFollow = false
    uiState.successOrNull()?.let {
        isFollow = it.followed
    }
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
