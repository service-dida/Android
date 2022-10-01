package com.dida.android.presentation.views.nav.mypage

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dida.android.presentation.adapter.home.*
import com.dida.android.presentation.base.UiState
import com.dida.android.presentation.base.successOrNull
import com.dida.android.presentation.views.nav.home.bindRecentNftItem
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.model.nav.mypage.UserProfile

@BindingAdapter("userProfile")
fun ImageView.bindUserProfile(uiState: UiState<UserProfile>) {
    Glide.with(context)
        .load(uiState.successOrNull()?.profileUrl)
        .transform(CenterCrop(), RoundedCorners(100))
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
    this.text = uiState.successOrNull()?.followerCnt.toString()
}

@BindingAdapter("userFollwerCount")
fun TextView.bindUserFollwerCount(uiState: UiState<UserProfile>) {
    this.text = uiState.successOrNull()?.followingCnt.toString()
}

@BindingAdapter("userNftItem")
fun RecyclerView.bindRecentNftItem(uiState: UiState<List<UserNft>>) {
    val boundAdapter = this.adapter
    if (boundAdapter is RecentNftAdapter) {
        boundAdapter.submitList(uiState.successOrNull())
    }
}