package com.dida.domain.model.nav.home

import com.google.gson.annotations.SerializedName

class HotSeller(
    @SerializedName("sellerBackground") var sellerBackground: String,
    @SerializedName("sellerProfile") var sellerProfile: String,
    @SerializedName("sellerName") var sellerName: String
)