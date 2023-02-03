package com.dida.common.actionhandler

interface NftActionHandler {
    fun onNftItemClicked(nftId: Int)
    fun onLikeBtnClicked(nftId: Int)
}
