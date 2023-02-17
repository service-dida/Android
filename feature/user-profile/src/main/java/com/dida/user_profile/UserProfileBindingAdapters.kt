package com.dida.user_profile

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dida.common.adapter.RecentNftAdapter
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.model.nav.mypage.OtherUserProfie
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.model.nav.mypage.UserProfile

@BindingAdapter("otherUserProfile")
fun ImageView.bindOtherUserProfile(uiState: UiState<OtherUserProfie>) {
    Glide.with(context)
        .load(uiState.successOrNull()?.profileUrl)
        .transform(CenterCrop(), RoundedCorners(1000))
        .into(this)
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
    this.text = uiState.successOrNull()?.followerCnt.toString()
}

@BindingAdapter("otherUserFollwerCount")
fun TextView.bindOtherUserFollwerCount(uiState: UiState<OtherUserProfie>) {
    this.text = uiState.successOrNull()?.followingCnt.toString()
}

@BindingAdapter("otherUserCardItem")
fun RecyclerView.bindOtherUserCardItem(uiState: UiState<List<UserNft>>) {
    val boundAdapter = this.adapter
    if (boundAdapter is RecentNftAdapter) {
        boundAdapter.submitList(uiState.successOrNull())
    }
}
