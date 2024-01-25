package com.dida.common.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Scheme : Parcelable {
    @Parcelize
    object Home : Scheme()

    @Parcelize
    object Defi : Scheme()

    @Parcelize
    object Add : Scheme()

    @Parcelize
    object Community : Scheme()

    @Parcelize
    object My : Scheme()

    @Parcelize
    data class DetailNft(
        val nftId: Long
    ) : Scheme()

    @Parcelize
    data class DetailCommunity(
        val communityId: Int
    ) : Scheme()
}
