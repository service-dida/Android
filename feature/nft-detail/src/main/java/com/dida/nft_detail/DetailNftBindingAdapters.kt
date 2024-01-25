package com.dida.nft_detail

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dida.common.util.UiState
import com.dida.common.util.addPriceDot
import com.dida.common.util.successOrNull
import com.dida.domain.main.model.Nft
import com.dida.nft_detail.bottom.DetailOwnerType
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("NftImgUrl")
fun ImageView.bindImgUrl(uiState: UiState<Nft>) {
    uiState.successOrNull()?.let {
        if (it.nftInfo.nftImgUrl.isEmpty().not()) {
            Glide.with(context)
                .load(uiState.successOrNull()?.nftInfo?.nftImgUrl)
                .placeholder(com.dida.common.R.mipmap.img_dida_logo_foreground)
                .error(com.dida.common.R.mipmap.img_dida_logo_foreground)
                .centerCrop()
                .into(this)
        } else {
            Glide.with(context)
                .load(com.dida.common.R.mipmap.img_dida_logo_foreground)
                .into(this)
        }
    }
}

@BindingAdapter("NftTitle")
fun TextView.bindTitle(uiState: UiState<Nft>) {
    this.text = uiState.successOrNull()?.nftInfo?.nftName
}

@BindingAdapter("NftDescription")
fun TextView.bindDescription(uiState: UiState<Nft>) {
    this.text = uiState.successOrNull()?.description
}

@BindingAdapter("userNickname")
fun TextView.bindNickName(uiState: UiState<Nft>) {
    this.text = uiState.successOrNull()?.memberInfo?.memberName
}

@BindingAdapter("userProfile")
fun ImageView.bindProfileUrl(uiState: UiState<Nft>) {
    uiState.successOrNull()?.let {
        if (it.memberInfo.profileImgUrl?.isEmpty()?.not() == true) {
            Glide.with(context)
                .load(it.memberInfo.profileImgUrl)
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

@BindingAdapter("NftContract")
fun TextView.bindContract(uiState: UiState<Nft>) {
    this.text = uiState.successOrNull()?.let {
        if (it.contractAddress.isNullOrEmpty()) {
            "empty"
        } else {
            with(it.contractAddress) {
                if (this.length > 6) this.substring(0 until 3) + "..." + this.substring(this.length - 4 until this.length - 1)
                else this
            }
        }
    }
}

@BindingAdapter("NftTokenId")
fun TextView.bindTokenId(uiState: UiState<Nft>) {
    this.text = uiState.successOrNull()?.tokenId
}

@BindingAdapter("NftPrice")
fun TextView.bindPrice(uiState: UiState<Nft>) {
    val price = uiState.successOrNull()?.nftInfo?.price
    if (price.isNullOrEmpty() || price == "NOT SALE" || price == "NO MARKETED") {
        this.text = "NOT SALE"
    } else {
        val roundedValue = (price.toDouble() * 100).toLong() / 100.0

        val formattedValue = if (roundedValue % 1 == 0.0) {
            String.format("%.0f", roundedValue)
        } else {
            String.format("%.2f", roundedValue)
        }
        this.text = "${addPriceDot(formattedValue)}"
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("NftDidaPrice")
fun TextView.bindNftDidaPrice(uiState: UiState<Nft>) {
    val price = uiState.successOrNull()?.nftInfo?.price
    if (price == "NOT SALE") this.text = price
    else this.text = "$price dida"
}

@BindingAdapter("NftPriceLayout")
fun ConstraintLayout.bindVisible(type: DetailOwnerType) {
    if (type == DetailOwnerType.NOTMINE_AND_NOTSALE || type == DetailOwnerType.NOTLOGIN_AND_NOTSALE) {
        this.visibility = View.GONE
    }
}

@BindingAdapter("writeLoginCheck")
fun FloatingActionButton.bindLoginCheck(login: Boolean) {
    this.visibility = if(login) View.VISIBLE else View.GONE
}

@BindingAdapter("NftDetailConfirmBtn")
fun TextView.bindConfrimBtn(type: DetailOwnerType) {
    when(type) {
        DetailOwnerType.MINE_AND_NOTSALE -> {
            this.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            this.gravity = Gravity.CENTER_HORIZONTAL
            this.text = "판매하기"
            this.setTextColor(ContextCompat.getColor(this.context,com.dida.common.R.color.black))
            this.background = ContextCompat.getDrawable(this.context,com.dida.common.R.drawable.custom_brandlemon_radius10)
        }
        DetailOwnerType.MINE_AND_SALE-> {
            this.text = "판매중"
            this.setTextColor(ContextCompat.getColor(this.context,com.dida.common.R.color.surface6))
            this.background = ContextCompat.getDrawable(this.context,com.dida.common.R.drawable.custom_surface2_radius10)
        }
        else -> {

        }
    }
}

@BindingAdapter(value = ["nftDetailToolbar", "loginState"], requireAll = true)
fun Toolbar.bindToolbar(nft: UiState<Nft>, login: Boolean) {
    this.menu.clear()
    if (login) {
        nft.successOrNull()?.let {
            if (it.liked) this.inflateMenu(R.menu.menu_detailnft_like_toolbar)
            else this.inflateMenu(R.menu.menu_detailnft_unlike_toolbar)
        }
    }
}
