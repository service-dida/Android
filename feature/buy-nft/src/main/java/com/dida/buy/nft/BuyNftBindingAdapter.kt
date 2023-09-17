package com.dida.buy.nft

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dida.domain.ui.model.DetailNft


@BindingAdapter("sellerText")
fun TextView.bindSellerName(detailNFT: DetailNft) {
    this.text = "${detailNFT.nickname} 판매자"
}

@BindingAdapter("buyerText")
fun TextView.bindBuyerName(detailNFT: DetailNft) {
    this.text = "${detailNFT.viewerNickname} 구매자"
}

@BindingAdapter("priceText")
fun TextView.bindPriceName(detailNFT: DetailNft) {
    this.text = "${detailNFT.price} dida"
}

// TODO : 수수료 반영하기
@BindingAdapter("feeText")
fun TextView.bindFeeText(detailNFT: DetailNft) {
    val fee = 0
    this.text = "$fee dida"
}

// TODO: 수수료 반영하기
@BindingAdapter("totalPrice")
fun TextView.bindTotalPrice(detailNFT: DetailNft) {
    val view = this
    val price = detailNFT.price.toFloat() + 0
    view.text = "$price dida"
}
