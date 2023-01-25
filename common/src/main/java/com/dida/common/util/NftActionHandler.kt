package com.dida.common.util

interface NftActionHandler {
    fun onNftItemClicked(nftId: Int)
    fun onLikeBtnClicked(nftId: Int)
}
