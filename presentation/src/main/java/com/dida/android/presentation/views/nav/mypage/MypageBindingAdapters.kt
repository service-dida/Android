package com.dida.android.presentation.views.nav.mypage

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dida.android.presentation.adapter.home.*
import com.dida.android.presentation.base.UiState
import com.dida.android.presentation.base.successOrNull
import com.dida.domain.model.nav.mypage.UserCardsResponseModel

@BindingAdapter("userProfile")
fun ImageView.bindUserProfile(uiState: UiState<MypageUiModel>) {
    Glide.with(context)
        .load(uiState.successOrNull()?.profileUrl)
        .into(this)
}

@BindingAdapter("userNickname")
fun TextView.bindUserNickname(uiState: UiState<MypageUiModel>) {
    this.text = uiState.successOrNull()?.nickname
}

@BindingAdapter("userDescription")
fun TextView.bindUserDescription(uiState: UiState<MypageUiModel>) {
    this.text = uiState.successOrNull()?.description
}

@BindingAdapter("userNftCount")
fun TextView.bindUserNftCount(uiState: UiState<MypageUiModel>) {
    this.text = uiState.successOrNull()?.cardCnt.toString()
}

@BindingAdapter("userFollwingCount")
fun TextView.bindUserFollwingCount(uiState: UiState<MypageUiModel>) {
    this.text = uiState.successOrNull()?.followerCnt.toString()
}

@BindingAdapter("userFollwerCount")
fun TextView.bindUserFollwerCount(uiState: UiState<MypageUiModel>) {
    this.text = uiState.successOrNull()?.followingCnt.toString()
}

@BindingAdapter("userNftItem")
fun RecyclerView.bindRecentNftItem(userNftList: List<UserCardsResponseModel>) {
    val boundAdapter = this.adapter
    if (boundAdapter is RecentNftAdapter) {
        boundAdapter.submitList(userNftList)
    }
}