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
import com.dida.common.util.successOrNull
import com.dida.domain.model.main.DetailNft
import com.dida.nft_detail.bottom.DetailOwnerType
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("NftImgUrl")
fun ImageView.bindImgUrl(uiState: UiState<DetailNft>) {
    uiState.successOrNull()?.let {
        if (it.imgUrl.isEmpty().not()) {
            Glide.with(context)
                .load(uiState.successOrNull()?.imgUrl)
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
fun TextView.bindTitle(uiState: UiState<DetailNft>) {
    this.text = uiState.successOrNull()?.title
}

@BindingAdapter("NftDescription")
fun TextView.bindDescription(uiState: UiState<DetailNft>) {
    this.text = uiState.successOrNull()?.description
}

@BindingAdapter("NftNickName")
fun TextView.bindNickName(uiState: UiState<DetailNft>) {
    this.text = uiState.successOrNull()?.nickname
}

@BindingAdapter("NftProfileUrl")
fun ImageView.bindProfileUrl(uiState: UiState<DetailNft>) {
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

@BindingAdapter("NftContract")
fun TextView.bindContract(uiState: UiState<DetailNft>) {
    this.text = uiState.successOrNull()?.let {
        if (it.contracts.isNullOrEmpty()) {
            "empty"
        } else {
            with(it.contracts!!) {
                if (this.length > 6) this.substring(0 until 3) + "..." + this.substring(this.length - 4 until this.length - 1)
                else this
            }
        }
    }
}

@BindingAdapter("NftTokenId")
fun TextView.bindTokenId(uiState: UiState<DetailNft>) {
    this.text = uiState.successOrNull()?.id
}

@BindingAdapter("NftPrice")
fun TextView.bindPrice(uiState: UiState<DetailNft>) {
    this.text = uiState.successOrNull()?.price
}

@SuppressLint("SetTextI18n")
@BindingAdapter("NftDidaPrice")
fun TextView.bindNftDidaPrice(uiState: UiState<DetailNft>) {
    val price = uiState.successOrNull()?.price
    if (price == "NOT SALE") this.text = price
    else this.text = "$price dida"
}

@BindingAdapter("NftPriceLayout")
fun ConstraintLayout.bindVisible(type: DetailOwnerType) {
    if (type == DetailOwnerType.NOTMINE_AND_NOTSALE ||type == DetailOwnerType.NOTLOGIN_AND_NOTSALE) {
        this.visibility = View.GONE
    }
}

@BindingAdapter("LoginCheck")
fun FloatingActionButton.bindVisible(uiState: UiState<DetailNft>) {
    if(uiState != UiState.Loading){
        if(uiState.successOrNull()?.type == "NEED LOGIN") {
            this.visibility = View.GONE
        } else {
            this.visibility = View.VISIBLE
        }
    }
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

@BindingAdapter("NftDetailToolbar")
fun Toolbar.bindToolbar(uiState: UiState<DetailNft>) {
    this.menu.clear()
    if (uiState.successOrNull()?.type != "NEED LOGIN") {
        if (uiState.successOrNull()?.liked == true) {
            this.inflateMenu(R.menu.menu_detailnft_like_toolbar)
        } else {
            this.inflateMenu(R.menu.menu_detailnft_unlike_toolbar)
        }
    }
}
