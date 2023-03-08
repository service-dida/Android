package com.dida.buy.nft

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dida.domain.model.main.DetailNft
import kotlinx.coroutines.flow.StateFlow


@BindingAdapter("sellerText")
fun TextView.bindSellerName(detailNFT: StateFlow<DetailNft>) {
    this.text = "${detailNFT.value.nickname} 판매자"
}

@BindingAdapter("buyerText")
fun TextView.bindBuyerName(detailNFT: StateFlow<DetailNft>) {
    this.text = "${detailNFT.value.viewerNickname} 구매자"
}

@BindingAdapter("priceText")
fun TextView.bindPriceName(detailNFT: StateFlow<DetailNft>) {
    this.text = "${detailNFT.value.price} dida"
}
