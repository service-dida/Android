package com.dida.data.main

import com.dida.data.api.handleApi
import com.dida.data.model.additional.PostMakePictureRequest
import com.dida.data.model.additional.PostReportRequest
import com.dida.data.model.additional.toDomain
import com.dida.data.model.dex.DeleteSellNftRequest
import com.dida.data.model.dex.PostBuyNftRequest
import com.dida.data.model.dex.PostNftRequest
import com.dida.data.model.dex.PostSellNftRequest
import com.dida.data.model.dex.PostSwapRequest
import com.dida.data.model.dex.toDomain
import com.dida.data.model.login.PatchMemberDeviceRequest
import com.dida.data.model.login.PostLoginRequest
import com.dida.data.model.login.PostNicknameRequest
import com.dida.data.model.login.PostUserRequest
import com.dida.data.model.login.PostWalletRequest
import com.dida.data.model.login.toDomain
import com.dida.data.model.main.toDomain
import com.dida.data.model.market.toDomain
import com.dida.data.model.profile.PatchMemberPasswordRequest
import com.dida.data.model.profile.PatchProfileDescriptionRequest
import com.dida.data.model.profile.PatchProfileNicknameRequest
import com.dida.data.model.profile.toDomain
import com.dida.data.model.sns.PatchCommonPostRequest
import com.dida.data.model.sns.PostCommonCommentsRequest
import com.dida.data.model.sns.PostCommonPostRequest
import com.dida.data.model.sns.toDomain
import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.CommonProfile
import com.dida.domain.main.model.CommonProfileNft
import com.dida.domain.Contents
import com.dida.domain.main.model.AiPicture
import com.dida.domain.main.model.Alarm
import com.dida.domain.main.model.Block
import com.dida.domain.main.model.Comment
import com.dida.domain.main.model.CommonFollow
import com.dida.domain.main.model.DealingHistory
import com.dida.domain.main.model.HotPost
import com.dida.domain.main.model.HotSellerPage
import com.dida.domain.main.model.LoginToken
import com.dida.domain.main.model.Main
import com.dida.domain.main.model.MemberProfile
import com.dida.domain.main.model.MemberWallet
import com.dida.domain.main.model.Nft
import com.dida.domain.main.model.OwnNft
import com.dida.domain.main.model.Post
import com.dida.domain.main.model.PublicKey
import com.dida.domain.main.model.RecentNft
import com.dida.domain.main.model.Report
import com.dida.domain.main.model.SoldOut
import com.dida.domain.main.model.Swap
import com.dida.domain.main.model.TransactionInfo
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(
    @Named("Main") private val didaApi: DidaApi
): MainRepository {

    override suspend fun login(idToken: String): NetworkResult<LoginToken> {
        val body = PostLoginRequest(idToken = idToken)
        return handleApi { didaApi.login(body).toDomain() }
    }

    override suspend fun postUser(email: String, nickname: String): NetworkResult<LoginToken> {
        val body = PostUserRequest(email = email, nickname = nickname)
        return handleApi { didaApi.postUser(body).toDomain() }
    }

    override suspend fun checkNickname(nickname: String): NetworkResult<Boolean> {
        val body = PostNicknameRequest(nickname = nickname)
        return handleApi { didaApi.postNickname(body).used }
    }

    override suspend fun refreshToken(refreshToken: String): NetworkResult<LoginToken> {
        return handleApi { didaApi.patchRefreshToken(request = refreshToken).toDomain() }
    }

    override suspend fun emailAuth(): NetworkResult<String> {
        return handleApi { didaApi.getEmailAuth().random }
    }

    override suspend fun checkWallet(): NetworkResult<Boolean> {
        return handleApi { didaApi.getCommonWallet().existed }
    }

    override suspend fun patchDeviceToken(deviceToken: String): NetworkResult<Unit> {
        val body = PatchMemberDeviceRequest(deviceToken = deviceToken)
        return handleApi { didaApi.patchMemberDevice(body) }
    }

    override suspend fun deleteUser(): NetworkResult<Unit> {
        return handleApi { didaApi.deleteUser() }
    }

    override suspend fun commonProfile(): NetworkResult<CommonProfile> {
        return handleApi { didaApi.getCommonProfile().toDomain() }
    }

    override suspend fun commonProfileNft(
        page: Int,
        size: Int,
        sort: String
    ): NetworkResult<Contents<CommonProfileNft>> {
        return handleApi { didaApi.getCommonProfileNft(page, size, sort).toDomain() }
    }

    override suspend fun memberProfile(memberId: Long): NetworkResult<MemberProfile> {
        return handleApi { didaApi.getMemberProfile(memberId = memberId).toDomain() }
    }

    override suspend fun memberProfileNFt(
        memberId: Long,
        page: Int,
        size: Int,
        sort: String
    ): NetworkResult<Contents<CommonProfileNft>> {
        return handleApi { didaApi.getMemberProfileNft(memberId, page, size, sort).toDomain() }
    }

    override suspend fun memberFollow(memberId: Long): NetworkResult<Unit> {
        return handleApi { didaApi.patchMemberFollow(memberId) }
    }

    override suspend fun commonFollow(page: Int, size: Int): NetworkResult<Contents<CommonFollow>> {
        return handleApi { didaApi.getCommonFollow(page, size).toDomain() }
    }

    override suspend fun commonFollowing(page: Int, size: Int): NetworkResult<Contents<CommonFollow>> {
        return handleApi { didaApi.getCommonFollowing(page, size).toDomain() }
    }

    override suspend fun memberWallet(): NetworkResult<MemberWallet> {
        return handleApi { didaApi.getMemberWallet().toDomain() }
    }

    override suspend fun patchProfileImage(file: MultipartBody.Part): NetworkResult<Unit> {
        return handleApi { didaApi.patchProfileImage(file) }
    }

    override suspend fun patchProfileDescription(description: String): NetworkResult<Unit> {
        val body = PatchProfileDescriptionRequest(description)
        return handleApi { didaApi.patchProfileDescription(body) }
    }

    override suspend fun patchProfileNickname(nickname: String): NetworkResult<Unit> {
        val body = PatchProfileNicknameRequest(nickname)
        return handleApi { didaApi.patchProfileNickname(body) }
    }

    override suspend fun patchTempMemberPassword(): NetworkResult<Unit> {
        return handleApi { didaApi.patchTempMemberPassword() }
    }

    override suspend fun deleteNft(nftId: Long): NetworkResult<Unit> {
        return handleApi { didaApi.deleteNft(nftId) }
    }

    override suspend fun memberSwap(page: Int, size: Int): NetworkResult<Contents<Swap>> {
        return handleApi { didaApi.getMemberSwap(page, size).toDomain() }
    }

    override suspend fun nftDetail(nftId: Long): NetworkResult<Nft> {
        return handleApi { didaApi.getNftDetail(nftId).toDomain() }
    }

    override suspend fun transactionInfos(page: Int, size: Int): NetworkResult<Contents<TransactionInfo>> {
        return handleApi { didaApi.getTransactionInfos(page, size).toDomain() }
    }

    override suspend fun saleTransactionInfos(page: Int, size: Int): NetworkResult<Contents<DealingHistory>> {
        return handleApi { didaApi.getSaleTransactionInfos(page, size).toDomain() }
    }

    override suspend fun purchaseTransactionInfos(page: Int, size: Int): NetworkResult<Contents<DealingHistory>> {
        return handleApi { didaApi.getPurchaseTransactionInfos(page, size).toDomain() }
    }

    override suspend fun main(): NetworkResult<Main> {
        return handleApi { didaApi.getMain().toDomain() }
    }

    override suspend fun soldOut(range: Int): NetworkResult<SoldOut> {
        return handleApi { didaApi.getSoldOut(range).toDomain() }
    }

    override suspend fun soldOutPage(range: Int, page: Int, size: Int): NetworkResult<Contents<SoldOut>> {
        return handleApi { didaApi.getSoldOutPage(range, page, size).toDomain() }
    }

    override suspend fun recentNfts(page: Int, size: Int): NetworkResult<Contents<RecentNft>> {
        return handleApi { didaApi.getRecentNfts(page, size).toDomain() }
    }

    override suspend fun hotSellerPage(page: Int, size: Int): NetworkResult<Contents<HotSellerPage>> {
        return handleApi { didaApi.getHotSellerPage(page, size).toDomain() }
    }

    override suspend fun hotMemberPage(page: Int, size: Int): NetworkResult<Contents<HotSellerPage>> {
        return handleApi { didaApi.getHotMemberPage(page, size).toDomain() }
    }

    override suspend fun writePost(nftId: Long, title: String, content: String): NetworkResult<Unit> {
        val body = PostCommonPostRequest(nftId, title, content)
        return handleApi { didaApi.writePost(body) }
    }

    override suspend fun patchPost(postId: Long, title: String, content: String): NetworkResult<Unit> {
        val body = PatchCommonPostRequest(postId, title, content)
        return handleApi { didaApi.patchPost(body) }
    }

    override suspend fun deletePost(postId: Long): NetworkResult<Unit> {
        return handleApi { didaApi.deletePost(postId) }
    }

    override suspend fun posts(page: Int, size: Int): NetworkResult<Contents<Post>> {
        return handleApi { didaApi.getPosts(page, size).toDomain() }
    }

    override suspend fun postsFromNft(nftId: Long, page: Int, size: Int): NetworkResult<Contents<Post>> {
        return handleApi { didaApi.getPostsFromNft(nftId, page, size).toDomain() }
    }

    override suspend fun postDetail(postId: Long): NetworkResult<Post> {
        return handleApi { didaApi.getPostDetail(postId).toDomain() }
    }

    override suspend fun writePostComments(postId: Long, content: String): NetworkResult<Unit> {
        val body = PostCommonCommentsRequest(postId, content)
        return handleApi { didaApi.writePostComments(body) }
    }

    override suspend fun deletePostComments(commentId: Long): NetworkResult<Unit> {
        return handleApi { didaApi.deletePostComments(commentId) }
    }

    override suspend fun commentsFromPost(postId: Long, page: Int, size: Int): NetworkResult<Contents<Comment>> {
        return handleApi { didaApi.getCommentsFromPost(postId, page, size).toDomain() }
    }

    override suspend fun hotPosts(page: Int, size: Int): NetworkResult<Contents<HotPost>> {
        return handleApi { didaApi.getHotPosts(page, size).toDomain() }
    }

    override suspend fun ownNfts(page: Int, size: Int): NetworkResult<Contents<OwnNft>> {
        return handleApi { didaApi.getOwnNfts(page, size).toDomain() }
    }

    override suspend fun likedNfts(page: Int, size: Int): NetworkResult<Contents<OwnNft>> {
        return handleApi { didaApi.getLikedNfts(page, size).toDomain() }
    }

    override suspend fun block(type: Block, blockId: Long): NetworkResult<Unit> {
        return when (type) {
            Block.NFT -> handleApi { didaApi.blockNft(blockId) }
            Block.MEMBER -> handleApi { didaApi.blockMember(blockId) }
            Block.POST -> handleApi { didaApi.blockPost(blockId) }
            Block.COMMENT -> handleApi { didaApi.blockComments(blockId) }
        }
    }

    override suspend fun cancelBlock(type: Block, blockId: Long): NetworkResult<Unit> {
        return when (type) {
            Block.NFT -> handleApi { didaApi.cancelBlockNft(blockId) }
            Block.MEMBER -> handleApi { didaApi.cancelBlockMember(blockId) }
            Block.POST -> NetworkResult.Success(Unit)
            Block.COMMENT -> NetworkResult.Success(Unit)
        }
    }
    override suspend fun report(
        type: Report,
        reportedId: Long,
        description: String
    ): NetworkResult<Unit> {
        val body = PostReportRequest(reportedId, description)
        return when (type) {
            Report.NFT -> handleApi { didaApi.reportNft(body) }
            Report.MEMBER -> handleApi { didaApi.reportMember(body) }
            Report.POST -> handleApi { didaApi.reportPost(body) }
            Report.COMMENT -> handleApi { didaApi.reportComment(body) }
        }
    }

    override suspend fun nftLike(nftId: Long): NetworkResult<Unit> {
        return handleApi { didaApi.postNftLike(nftId) }
    }

    override suspend fun makeAiPicture(payPwd: String, setence: String): NetworkResult<AiPicture> {
        val body = PostMakePictureRequest(payPwd, setence)
        return handleApi { didaApi.makeAiPicture(body).toDomain() }
    }

    override suspend fun readAlarm(alarmId: Long): NetworkResult<Unit> {
        return handleApi { didaApi.patchReadAlarm(alarmId) }
    }

    override suspend fun alarms(page: Int, size: Int): NetworkResult<Contents<Alarm>> {
        return handleApi { didaApi.getAlarms(page, size).toDomain() }
    }

    override suspend fun getPublicKey(): NetworkResult<PublicKey> {
        return handleApi { didaApi.getPublicKey().toDomain() }
    }

    override suspend fun createWallet(payPwd: String, checkPwd: String): NetworkResult<Unit> {
        val body = PostWalletRequest(payPwd, checkPwd)
        return handleApi { didaApi.postWallet(body) }
    }

    override suspend fun patchPassword(nowPwd: String, changePwd: String): NetworkResult<Unit> {
        val body = PatchMemberPasswordRequest(nowPwd, changePwd)
        return handleApi { didaApi.patchMemberPassword(body) }
    }

    override suspend fun createNft(
        payPwd: String,
        title: String,
        description: String,
        image: String
    ): NetworkResult<Long> {
        val body = PostNftRequest(payPwd, title, description, image)
        return handleApi { didaApi.postNft(body).nftId }
    }

    override suspend fun swapToDida(payPwd: String, coin: Int): NetworkResult<Unit> {
        val body = PostSwapRequest(payPwd, coin)
        return handleApi { didaApi.postSwapToDida(body) }
    }

    override suspend fun swapToKlay(payPwd: String, coin: Int): NetworkResult<Unit> {
        val body = PostSwapRequest(payPwd, coin)
        return handleApi { didaApi.postSwapToKlay(body) }
    }

    override suspend fun sellNft(payPwd: String, nftId: Long, price: Double): NetworkResult<Unit> {
        val body = PostSellNftRequest(payPwd, nftId, price)
        return handleApi { didaApi.postSellNft(body) }
    }

    override suspend fun cancelSellNft(pawPwd: String, marketId: Long): NetworkResult<Unit> {
        val body = DeleteSellNftRequest(pawPwd, marketId)
        return handleApi { didaApi.deleteSellNft(body) }
    }

    override suspend fun buyNft(payPwd: String, marketId: Long): NetworkResult<Unit> {
        val body = PostBuyNftRequest(payPwd, marketId)
        return handleApi { didaApi.postBuyNft(body) }
    }

}

