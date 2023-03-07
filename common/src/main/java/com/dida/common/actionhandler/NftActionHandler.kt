package com.dida.common.actionhandler

interface NftActionHandler {
    fun onNftItemClicked(nftId: Long)
    fun onLikeBtnClicked(nftId: Long)
}
