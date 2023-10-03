package com.dida.user_profile

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.main.model.MemberProfile

@BindingAdapter("otherUserProfile")
fun ImageView.bindOtherUserProfile(uiState: UiState<MemberProfile>) {
    uiState.successOrNull()?.let {
        if (it.memberDetailInfo.memberInfo.profileImgUrl?.isEmpty()?.not() == true) {
            Glide.with(context)
                .load(it.memberDetailInfo.memberInfo.profileImgUrl)
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
fun TextView.bindOtherUserNickname(uiState: UiState<MemberProfile>) {
    this.text = uiState.successOrNull()?.memberDetailInfo?.memberInfo?.memberName
}

@BindingAdapter("otherUserDescription")
fun TextView.bindOtherUserDescription(uiState: UiState<MemberProfile>) {
    this.text = uiState.successOrNull()?.memberDetailInfo?.description
}

@BindingAdapter("otherUserNftCount")
fun TextView.bindOtherUserNftCount(uiState: UiState<MemberProfile>) {
    this.text = uiState.successOrNull()?.memberDetailInfo?.nftCnt.toString()
}

@BindingAdapter("otherUserFollwingCount")
fun TextView.bindOtherUserFollwingCount(uiState: UiState<MemberProfile>) {
    this.text = uiState.successOrNull()?.memberDetailInfo?.followingCnt.toString()
}

@BindingAdapter("otherUserFollwerCount")
fun TextView.bindOtherUserFollwerCount(uiState: UiState<MemberProfile>) {
    this.text = uiState.successOrNull()?.memberDetailInfo?.followerCnt.toString()
}

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("otherUserFollowBtn")
fun TextView.bindOtherUserFollowBtn(uiState: UiState<MemberProfile>) {
    val view = this
    var isFollow = false
    uiState.successOrNull()?.let {
        isFollow = it.followed
    }
    if(isFollow) {
        view.text = context.getString(com.dida.common.R.string.user_following)
        view.setTextColor(context.getColor(com.dida.common.R.color.black))
        view.setBackgroundResource(com.dida.common.R.drawable.custom_brandlemon_radius100)
    } else {
        view.text = context.getString(com.dida.common.R.string.user_follow)
        view.setTextColor(context.getColor(com.dida.common.R.color.brand_lemon))
        view.setBackgroundResource(com.dida.common.R.drawable.custom_brandlemon_empty_radius100)
    }
}

@BindingAdapter("isMyProfile")
fun TextView.bindIsMyProfile(my: Boolean) {
    val view = this
    view.visibility = if (my) View.GONE else View.VISIBLE
}
