package com.dida.buy.nft

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dida.domain.main.model.Nft

@BindingAdapter("sellerText")
fun TextView.bindSellerName(name: String) {
    this.text = "$name 판매자"
}

@BindingAdapter("buyerText")
fun TextView.bindBuyerName(name: String) {
    this.text = "$name 구매자"
}

@BindingAdapter("priceText")
fun TextView.bindPriceName(price: String) {
    this.text = "$price dida"
}

// TODO : 수수료 반영하기
@BindingAdapter("feeText")
fun TextView.bindFeeText(nft: Nft) {
    val fee = 0
    this.text = "$fee dida"
}

// TODO: 수수료 반영하기
@BindingAdapter("totalPrice")
fun TextView.bindTotalPrice(detailNFT: Nft) {
    val view = this
    val price = detailNFT.nftInfo.price.toFloat() + 0
    view.text = "$price dida"
}
