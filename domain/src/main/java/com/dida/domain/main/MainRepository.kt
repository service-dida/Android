package com.dida.domain.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.*
import okhttp3.MultipartBody

interface  MainRepository {

    suspend fun checkVersionAPI(): NetworkResult<AppVersion>

    suspend fun loginAPI(idToken : String): NetworkResult<Token>

    suspend fun nicknameAPI(nickName: String): NetworkResult<Nickname>

    suspend fun createUserAPI(email: String, nickName: String): NetworkResult<Token>

    suspend fun getUserProfileAPI() : NetworkResult<UserProfile>

    suspend fun refreshTokenAPI(request: String): NetworkResult<Token>

    suspend fun getUserCardsAPI(page: Int) : NetworkResult<List<UserNft>>

    suspend fun getSendEmailAPI() : NetworkResult<RandomNumber>

    suspend fun postCreateWalletAPI(password: String, passwordCheck: String) : NetworkResult<Unit>

    suspend fun getWalletExistsAPI() : NetworkResult<Boolean>

    suspend fun getCheckPasswordAPI(password: String) : NetworkResult<PasswordVerify>

    suspend fun postChangePasswordAPI(nowPwd: String, checkPwd: String) : NetworkResult<Unit>

    suspend fun getTempPasswordAPI() : NetworkResult<Unit>

    suspend fun getMainAPI() : NetworkResult<Home>

    suspend fun getSoldOutAPI(term: Int) : NetworkResult<List<SoldOut>>

    suspend fun mintNFT(payPwd : String, name : String, description : String, image : String) : NetworkResult<Long>

    suspend fun updateProfileImageAPI(file : MultipartBody.Part) : NetworkResult<Unit>

    suspend fun updateProfileDescriptionAPI(description: String) : NetworkResult<Unit>

    suspend fun updateProfileNicknameAPI(nickname: String) : NetworkResult<Unit>

    suspend fun postLikeAPI(cardId: Long) : NetworkResult<Unit>

    suspend fun postUserFollowAPI(userId: Long) : NetworkResult<Unit>

    suspend fun getDetailNFT(cardId: Long) : NetworkResult<DetailNft>

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

    suspend fun getBuySellList() : NetworkResult<List<TradeHistory>>

    suspend fun getBuyList() : NetworkResult<List<TradeHistory>>

    suspend fun getSellList() : NetworkResult<List<TradeHistory>>

    suspend fun getRecentCard(page: Int) : NetworkResult<List<UserNft>>

    suspend fun getHideList() : NetworkResult<List<HideCard>>

    suspend fun getHideNft(cardId : Long) : NetworkResult<Unit>

    suspend fun getHideCancelNft(cardId : Long) : NetworkResult<Unit>

    suspend fun getUserUserId(userId: Long) : NetworkResult<OtherUserProfie>

    suspend fun getUserCardsUserId(userId: Long, page: Int) : NetworkResult<List<UserNft>>

    suspend fun patchDeleteNft(cardId: Long,payPwd : String) : NetworkResult<Long>

    suspend fun getHotUser(page: Int): NetworkResult<List<HotUser>>

    suspend fun getHotSeller(page: Int): NetworkResult<List<HotSellerMore>>

    suspend fun postReportUser(userId: Long, content: String): NetworkResult<Unit>

    suspend fun postReportPost(postId: Long, content: String): NetworkResult<Unit>

    suspend fun postReportCard(cardId: Long, content: String): NetworkResult<Unit>

    suspend fun postUserHide(userId: Long): NetworkResult<Unit>

    suspend fun getUserHideList(): NetworkResult<List<UserHide>>

    suspend fun deleteUserHide(userId: Long): NetworkResult<Unit>

    suspend fun postPostHide(postId: Long): NetworkResult<Unit>

    suspend fun postReportComment(reportedId: Long, content: String): NetworkResult<Unit>
}
