package com.dida.mypage

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dida.common.util.UiState
import com.dida.domain.main.model.CommonProfile

@BindingAdapter("userProfile")
fun ImageView.bindUserProfile(commonProfile: CommonProfile) {
    if(commonProfile.memberInfo.profileImgUrl?.isEmpty()?.not() == true) {
        Glide.with(context)
            .load(commonProfile.memberInfo.profileImgUrl)
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

@BindingAdapter("userNickname")
fun TextView.bindUserNickname(commonProfile: CommonProfile) {
    this.text = commonProfile.memberInfo.memberName
}

@BindingAdapter("userDescription")
fun TextView.bindUserDescription(commonProfile: CommonProfile) {
    this.text = commonProfile.description
}

@BindingAdapter("userNftCount")
fun TextView.bindUserNftCount(commonProfile: CommonProfile) {
    this.text = commonProfile.nftCnt.toString()
}

@BindingAdapter("userFollwingCount")
fun TextView.bindUserFollwingCount(commonProfile: CommonProfile) {
    this.text = commonProfile.followingCnt.toString()
}

@BindingAdapter("userFollwerCount")
fun TextView.bindUserFollwerCount(commonProfile: CommonProfile) {
    this.text = commonProfile.followerCnt.toString()
}

@BindingAdapter("userToolBar")
fun Toolbar.bindUserToolBar(uiState: UiState<CommonProfile>) {
    visibility = if (uiState is UiState.Success) View.VISIBLE else View.INVISIBLE
}
