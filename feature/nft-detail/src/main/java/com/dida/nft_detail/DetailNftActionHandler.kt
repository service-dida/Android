package com.dida.nft_detail

import com.dida.common.util.UiState
import com.dida.domain.model.nav.detailnft.DetailNFT

interface DetailNftActionHandler {
    fun onCommunityMoreClicked()
    fun onBuyNftClicked()
    fun onUserProfileClicked()
    fun onNextButtonClicked()
}
