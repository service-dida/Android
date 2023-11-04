package com.dida.user_profile

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.main.model.MemberProfile

@BindingAdapter("otherUserProfile")
fun ImageView.bindOtherUserProfile(memberProfile: MemberProfile) {
    if (memberProfile.memberDetailInfo.memberInfo.profileImgUrl?.isEmpty()?.not() == true) {
        Glide.with(context)
            .load(memberProfile.memberDetailInfo.memberInfo.profileImgUrl)
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

@BindingAdapter("otherUserNickname")
fun TextView.bindOtherUserNickname(memberProfile: MemberProfile) {
    this.text = memberProfile.memberDetailInfo.memberInfo.memberName
}

@BindingAdapter("otherUserDescription")
fun TextView.bindOtherUserDescription(memberProfile: MemberProfile) {
    this.text = memberProfile.memberDetailInfo.description
}

@BindingAdapter("otherUserNftCount")
fun TextView.bindOtherUserNftCount(memberProfile: MemberProfile) {
    this.text = memberProfile.memberDetailInfo.nftCnt.toString()
}

@BindingAdapter("otherUserFollwingCount")
fun TextView.bindOtherUserFollwingCount(memberProfile: MemberProfile) {
    this.text = memberProfile.memberDetailInfo.followingCnt.toString()
}

@BindingAdapter("otherUserFollwerCount")
fun TextView.bindOtherUserFollwerCount(memberProfile: MemberProfile) {
    this.text = memberProfile.memberDetailInfo.followerCnt.toString()
}

@BindingAdapter("otherUserFollowBtn")
fun TextView.bindOtherUserFollowBtn(uiState: UiState<MemberProfile>) {
    val view = this
    var isFollow = false
    uiState.successOrNull()?.let {
        isFollow = it.followed
    }
    if (isFollow) {
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
