package com.dida.android.presentation.views.detailnft

import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dida.android.util.UiState
import com.dida.android.util.successOrNull
import com.dida.domain.model.nav.detailnft.DetailNFT

@BindingAdapter("NftImgUrl")
fun ImageView.bindImgUrl(uiState: UiState<DetailNFT>) {
    Glide.with(context)
        .load(uiState.successOrNull()?.imgUrl)
        .into(this)
}

@BindingAdapter("NftTitle")
fun TextView.bindTitle(uiState: UiState<DetailNFT>) {
    this.text = uiState.successOrNull()?.title
}

@BindingAdapter("NftDescription")
fun TextView.bindDescription(uiState: UiState<DetailNFT>) {
    this.text = uiState.successOrNull()?.description
}

@BindingAdapter("NftNickName")
fun TextView.bindNickName(uiState: UiState<DetailNFT>) {
    this.text = uiState.successOrNull()?.nickname
}

@BindingAdapter("NftProfileUrl")
fun ImageView.bindProfileUrl(uiState: UiState<DetailNFT>) {
    Glide.with(context)
        .load(uiState.successOrNull()?.profileUrl)
        .transform(CenterCrop(), RoundedCorners(1000))
        .into(this)
}

@BindingAdapter("NftContract")
fun TextView.bindContract(uiState: UiState<DetailNFT>) {
    this.text = uiState.successOrNull()?.let {
        if (it.contracts.isNullOrEmpty()) {
            "empty"
        } else {
            it.contracts
        }
    }
}

@BindingAdapter("NftTokenId")
fun TextView.bindTokenId(uiState: UiState<DetailNFT>) {
    this.text = uiState.successOrNull()?.id
}

@BindingAdapter("NftPrice")
fun TextView.bindPrice(uiState: UiState<DetailNFT>) {
    if(uiState.successOrNull()?.type == "NEED LOGIN"){
        this.text = "로그인이 필요합니다."
    }else if(uiState.successOrNull()?.type =="NOT MINE"){
        this.isGone
    }
    else{
        this.text = uiState.successOrNull()?.price
    }
}

@BindingAdapter("NftPriceLayout")
fun ConstraintLayout.bindVisible(uiState: UiState<DetailNFT>){
    if(uiState.successOrNull()?.type == "NOT SALE" || uiState.successOrNull()?.type == "MINE"){
        this.visibility = View.GONE
    }
}

@BindingAdapter("NftDetailConfirmBtn")
fun TextView.bindConfrimBtn(uiState: UiState<DetailNFT>){
    if(uiState.successOrNull()?.type == "NOT MINE"){
        this.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        this.gravity = Gravity.CENTER_HORIZONTAL
        this.text ="판매요청 하기 "
    }
}
