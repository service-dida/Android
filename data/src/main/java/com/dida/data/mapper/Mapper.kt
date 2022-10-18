package com.dida.data.mapper

import com.dida.data.model.createwallet.PostCheckPasswordResponse
import com.dida.data.model.createwallet.SendEmailResponse
import com.dida.data.model.detail.GetDetailNFTResponse
import com.dida.data.model.klaytn.AssetResponse
import com.dida.data.model.main.*
import com.dida.data.model.mypage.UserProfileResponse
import com.dida.domain.model.klaytn.Asset
import com.dida.domain.model.nav.createwallet.RandomNumber
import com.dida.domain.model.nav.detailnft.DetailNFT
import com.dida.domain.model.nav.home.*
import com.dida.domain.model.nav.home.Collection
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.model.nav.mypage.UserProfile
import com.google.gson.annotations.SerializedName

fun GetMainResponse.toDomain() : Home {
    return Home(
        getHotItems = getHotItems.toDomain(),
        getHotSellers = getHotSellers.toDomain(),
        getRecentCards = getRecentCards.toDomain(),
        getHotUsers = getHotUsers.toDomain()
    )
}

fun List<GetSoldOutResponse>.toDomain(): List<SoldOut> {
    return map { SoldOut(
        nftId = it.nftId,
        nftImg = it.imgUrl,
        nftName = it.name,
        userImg = it.profileUrl,
        userName = it.userName,
        price = it.price
    ) }
}

fun AssetResponse.toDomain() : Asset {
    return Asset(
        contentType = contentType,
        filename = filename,
        uri = uri
    )
}

fun SendEmailResponse.toDomain(): RandomNumber { return RandomNumber( random = random ) }

fun PostCheckPasswordResponse.toDomain(): Boolean { return flag }

@JvmName("toDomainHotItemResponse")
fun List<HotItemResponse>.toDomain(): List<Hots> {
    return map { Hots(
        cardId = it.cardId,
        nftImg = it.imgUrl,
        nftName = it.name,
        heartCount = it.count,
        price = it.price
    ) }
}

@JvmName("toDomainHotSellerResponse")
fun List<HotSellerResponse>.toDomain(): List<HotSeller> {
    return map { HotSeller(
        userId = it.userId,
        sellerBackground = it.imgUrl,
        sellerProfile = it.profileUrl,
        sellerName = it.name
    ) }
}

@JvmName("toDomainRecentCardResponse")
fun List<RecentCardResponse>.toDomain(): List<UserNft> {
    return map { UserNft(
        cardId = it.cardId,
        userName = it.userName,
        imgUrl = it.imgUrl,
        cardName = it.cardName,
        price = it.price,
        liked = it.liked
    ) }
}

@JvmName("toDomainHotUserResponse")
fun List<HotUserResponse>.toDomain(): List<Collection> {
    return map { Collection(
        userId = it.userId,
        userImg = it.profileUrl,
        userName = it.name,
        userDetail = it.count+" 작품",
        follow = it.followed
    ) }
}

fun UserProfileResponse.toDomain(): UserProfile {
    return UserProfile(
        cardCnt = cardCnt,
        description = description,
        followerCnt = followerCnt,
        followingCnt = followingCnt,
        getWallet = getWallet,
        nickname = nickname,
        profileUrl = profileUrl,
        userId = userId
    )
}

fun GetDetailNFTResponse.toDomain(): DetailNFT {
    return DetailNFT(
        cardId = cardId,
        contracts = contracts,
        description = description,
        id = id,
        imgUrl = imgUrl,
        nickname = nickname,
        price = price,
        profileUrl = profileUrl,
        title = title,
        liked = liked,
        type = type,
        userId = userId
    )
}

