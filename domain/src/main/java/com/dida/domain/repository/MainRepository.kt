package com.dida.domain.repository

import com.dida.domain.NetworkResult
import com.dida.domain.model.login.LoginResponseModel
import com.dida.domain.model.login.NicknameResponseModel
import com.dida.domain.model.nav.community.HotCard
import com.dida.domain.model.nav.createwallet.RandomNumber
import com.dida.domain.model.nav.detailnft.DetailNFT
import com.dida.domain.model.nav.hide.CardHideList
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.home.SoldOut
import com.dida.domain.model.nav.mypage.BuySellList
import com.dida.domain.model.nav.mypage.OtherUserProfie
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.model.nav.mypage.UserProfile
import com.dida.domain.model.nav.post.CardPost
import com.dida.domain.model.nav.post.Comments
import com.dida.domain.model.nav.post.Post
import com.dida.domain.model.nav.post.Posts
import com.dida.domain.model.nav.swap.WalletAmount
import com.dida.domain.model.nav.swap_history.SwapHistory
import com.dida.domain.model.splash.AppVersionResponse
import okhttp3.MultipartBody

interface  MainRepository {

    suspend fun checkVersionAPI(): NetworkResult<AppVersionResponse>

    suspend fun loginAPI(idToken : String): NetworkResult<LoginResponseModel>

    suspend fun nicknameAPI(nickName: String): NetworkResult<NicknameResponseModel>

    suspend fun createUserAPI(email: String, nickName: String): NetworkResult<LoginResponseModel>

    suspend fun getUserProfileAPI() : NetworkResult<UserProfile>

    suspend fun refreshTokenAPI(request: String): NetworkResult<LoginResponseModel>

    suspend fun getUserCardsAPI() : NetworkResult<List<UserNft>>

    suspend fun getSendEmailAPI() : NetworkResult<RandomNumber>

    suspend fun postCreateWalletAPI(password: String, passwordCheck: String) : NetworkResult<Unit>

    suspend fun getWalletExistsAPI() : NetworkResult<Boolean>

    suspend fun getCheckPasswordAPI(password: String) : NetworkResult<Boolean>

    suspend fun postChangePasswordAPI(beforePassword: String, afterPassword: String) : NetworkResult<Unit>

    suspend fun getTempPasswordAPI() : NetworkResult<Unit>

    suspend fun getMainAPI() : NetworkResult<Home>

    suspend fun getSoldOutAPI(term: Int) : NetworkResult<List<SoldOut>>

    suspend fun mintNFT(payPwd : String, name : String, description : String, image : String) : NetworkResult<Long>

    suspend fun updateProfileImageAPI(file : MultipartBody.Part) : NetworkResult<Unit>

    suspend fun updateProfileDescriptionAPI(description: String) : NetworkResult<Unit>

    suspend fun updateProfileNicknameAPI(nickname: String) : NetworkResult<Unit>

    suspend fun postLikeAPI(cardId: Long) : NetworkResult<Unit>

    suspend fun postUserFollowAPI(userId: Long) : NetworkResult<Unit>

    suspend fun getDetailNFT(cardId: Long) : NetworkResult<DetailNFT>

    suspend fun putDeviceTokenAPI(deviceToken: String) : NetworkResult<Unit>

    suspend fun postBuyNftAPI(password: String, nftId: Long) : NetworkResult<Unit>

    suspend fun postSwapKlayToDida(password: String, klay: Double) : NetworkResult<Unit>

    suspend fun postSwapDidaToKlay(password: String, dida: Double) : NetworkResult<Unit>

    suspend fun postSellNftAPI(payPwd : String, cardId : Long, price : Double) : NetworkResult<Unit>

    suspend fun getWalletAmountAPI() : NetworkResult<WalletAmount>

    suspend fun getSwapHistoryAPI() : NetworkResult<List<SwapHistory>>

    suspend fun getPosts(page: Int) : NetworkResult<List<Posts>>

    suspend fun getPostPostId(postId: Long) : NetworkResult<Post>

    suspend fun getCommentsPostId(postId: Long) : NetworkResult<List<Comments>>

    suspend fun getCardsPostLike() : NetworkResult<List<CardPost>>

    suspend fun getCardsPostMy() : NetworkResult<List<CardPost>>

    suspend fun getPostsCardCardId(cardId: Long) : NetworkResult<List<Posts>>

    suspend fun getHotCards() : NetworkResult<List<HotCard>>

    suspend fun postPostCardId(cardId: Long, title: String, content: String) : NetworkResult<Unit>

    suspend fun patchPostPostId(postId: Long, title: String, content: String) : NetworkResult<Unit>

    suspend fun patchPostPostIdStatus(postId: Long) : NetworkResult<Unit>

    suspend fun postComment(postId: Long, content: String): NetworkResult<Unit>

    suspend fun patchCommentIdStatus(commentId: Long) : NetworkResult<Unit>

    suspend fun getBuySellList() : NetworkResult<List<BuySellList>>

    suspend fun getBuyList() : NetworkResult<List<BuySellList>>

    suspend fun getSellList() : NetworkResult<List<BuySellList>>

    suspend fun getRecentCard(page: Int) : NetworkResult<List<UserNft>>

    suspend fun getHideList() : NetworkResult<List<CardHideList>>

    suspend fun getHideNft(cardId : Long) : NetworkResult<Unit>

    suspend fun getHideCancelNft(cardId : Long) : NetworkResult<Unit>

    suspend fun getUserUserId(userId: Long) : NetworkResult<OtherUserProfie>

    suspend fun getUserCardsUserId(userId: Long) : NetworkResult<List<UserNft>>

    suspend fun patchDeleteNft(cardId: Long) : NetworkResult<Long>
}
