package com.dida.data.mapper

import com.dida.data.model.response.*
import com.dida.domain.model.klaytn.Asset
import com.dida.domain.model.nav.community.HotCard
import com.dida.domain.model.nav.createwallet.RandomNumber
import com.dida.domain.model.nav.detailnft.DetailNFT
import com.dida.domain.model.nav.home.*
import com.dida.domain.model.nav.home.Collection
import com.dida.domain.model.nav.mypage.BuySellList
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.model.nav.mypage.UserProfile
import com.dida.domain.model.nav.post.*
import com.dida.domain.model.nav.swap_history.SwapHistory

fun GetMainResponse.toDomain() : Home {
    return Home(
        getHotItems = getHotItems.toDomain(),
        getHotSellers = getHotSellers.toDomain(),
        getRecentCards = getRecentCards.toDomain(),
        getHotUsers = getHotUsers.toDomain()
    )
}

@JvmName("toDomainGetSoldOutResponse")
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

fun GetWalletAmountResponse.toDomain(): com.dida.domain.model.nav.swap.WalletAmount {
    return com.dida.domain.model.nav.swap.WalletAmount(
        dida = dida,
        klay = klay
    )
}

@JvmName("toDomainGetSwapHistoryResponse")
fun List<GetSwapHistoryResponse>.toDomain() : List<SwapHistory>{
    return map{SwapHistory(
        sendCoinType = it.sendCoinType,
        receiveCoinType = it.receiveCoinType,
        sendAmount = it.sendAmount,
        time = it.time
    )}
}

@JvmName("toDomainGetPostsResponse")
fun List<GetPostsResponse>.toDomain(): List<Posts> {
    return map { Posts(
        postId = it.postId,
        cardId = it.cardId,
        userName = it.userName,
        userImgUrl = it.userImgUrl,
        title = it.title,
        content = it.content,
        cardName = it.cardName,
        cardImgUrl = it.cardImgUrl,
        price = it.price,
        cardOwnerImgUrl = it.cardOwnerImgUrl,
        commentList = it.commentsList.toDomain()
    ) }
}

@JvmName("toDomainGetPostsCommentsResponse")
fun List<GetPostsCommentsResponse>.toDomain(): List<PostComments> {
    return map { PostComments(
        commentId = it.commentId,
        content = it.content,
        userName = it.userName,
        userImgUrl = it.userImgUrl,
        type = it.type
    ) }
}

fun GetPostPostIdResponse.toDomain(): Post {
    return Post(
        postId = postId,
        cardId = cardId,
        userName = userName,
        userImgUrl = userImgUrl,
        title = title,
        content = content,
        cardName = cardName,
        cardImgUrl = cardImgUrl,
        price = price,
        cardOwnerImgUrl = cardOwnerImgUrl,
        type = type
    )
}

@JvmName("toDomainGetPostIdCommentsResponse")
fun List<GetPostIdCommentsResponse>.toDomain(): List<Comments> {
    return map { Comments(
       commentId = it.commentId,
        postId = it.postId,
        content = it.content,
        userName = it.userName,
        userImgUrl = it.userImgUrl,
        type = it.type
    ) }
}

@JvmName("toDomainGetCardsPostResponse")
fun List<GetCardsPostResponse>.toDomain(): List<CardPost> {
    return map { CardPost(
        cardId = it.cardId,
        cardImgUrl = it.cardImgUrl,
        cardName = it.cardName,
        userImgUrl = it.userImgUrl,
        userName = it.userName
    ) }
}

@JvmName("toDomainGetHotCardsResponse")
fun List<GetHotCardsResponse>.toDomain(): List<HotCard> {
    return map { HotCard(
        cardId = it.cardId,
        cardImgUrl = it.cardImgUrl
    ) }
}

@JvmName("toDomainGetBuySellListResponse")
fun List<GetBuySellListResponse>.toDomain(): List<BuySellList> {
    return map { BuySellList(
        cardName = it.cardName,
        cardImgUrl = it.cardImgUrl,
        userName = it.userName,
        price = it.price,
        type = it.type
    ) }
}
