package com.dida.mypage

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
import com.dida.domain.model.main.UserNft
import com.dida.domain.model.main.UserProfile

@BindingAdapter("userProfile")
fun ImageView.bindUserProfile(uiState: UiState<UserProfile>) {
    Glide.with(context)
        .load(uiState.successOrNull()?.profileUrl)
        .transform(CenterCrop(), RoundedCorners(1000))
        .into(this)
}

@BindingAdapter("userNickname")
fun TextView.bindUserNickname(uiState: UiState<UserProfile>) {
    this.text = uiState.successOrNull()?.nickname
}

@BindingAdapter("userDescription")
fun TextView.bindUserDescription(uiState: UiState<UserProfile>) {
    this.text = uiState.successOrNull()?.description
}

@BindingAdapter("userNftCount")
fun TextView.bindUserNftCount(uiState: UiState<UserProfile>) {
    this.text = uiState.successOrNull()?.cardCnt.toString()
}

@BindingAdapter("userFollwingCount")
fun TextView.bindUserFollwingCount(uiState: UiState<UserProfile>) {
    this.text = uiState.successOrNull()?.followingCnt.toString()
}

@BindingAdapter("userFollwerCount")
fun TextView.bindUserFollwerCount(uiState: UiState<UserProfile>) {
    this.text = uiState.successOrNull()?.followerCnt.toString()
}

@BindingAdapter("userNftItem")
fun RecyclerView.bindRecentNftItem(uiState: UiState<List<UserNft>>) {
    val boundAdapter = this.adapter
    if (boundAdapter is RecentNftAdapter) {
        boundAdapter.submitList(uiState.successOrNull())
    }
}

@BindingAdapter("userToolBar")
fun androidx.appcompat.widget.Toolbar.bindUserToolBar(uiState: UiState<UserProfile>) {
    visibility = if (uiState is UiState.Success) View.VISIBLE else View.INVISIBLE
}
