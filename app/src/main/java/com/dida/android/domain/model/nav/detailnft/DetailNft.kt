package com.dida.android.domain.model.nav.detailnft

import com.google.gson.annotations.SerializedName

class DetailNft(
    @SerializedName("nftImg") var nftImg: String,
    @SerializedName("nftName") var nftName: String,
    @SerializedName("nftDetail") var nftDetail: String,
    @SerializedName("userImg") var userImg: String,
    @SerializedName("userName") var userName: String,
    @SerializedName("nftLink") var nftLink: String,
    @SerializedName("tokenId") var tokenId: String,
    @SerializedName("community") var community: List<Community>
)