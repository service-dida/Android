package com.dida.domain.main

import com.dida.domain.Contents
import com.dida.domain.NetworkResult
import com.dida.domain.main.model.AiPicture
import com.dida.domain.main.model.Alarm
import com.dida.domain.main.model.Block
import com.dida.domain.main.model.Comment
import com.dida.domain.main.model.CommonProfile
import com.dida.domain.main.model.CommonProfileNft
import com.dida.domain.main.model.CommonFollow
import com.dida.domain.main.model.DealingHistory
import com.dida.domain.main.model.HideMember
import com.dida.domain.main.model.HideNft
import com.dida.domain.main.model.HotMember
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
import com.dida.domain.main.model.Sort
import com.dida.domain.main.model.Swap
import com.dida.domain.main.model.TransactionInfo
import okhttp3.MultipartBody

interface MainRepository {

    suspend fun login(idToken: String): NetworkResult<LoginToken>

    suspend fun postUser(email: String, nickname: String): NetworkResult<LoginToken>

    suspend fun checkNickname(nickname: String): NetworkResult<Boolean>

    suspend fun refreshToken(refreshToken: String): NetworkResult<LoginToken>

    suspend fun emailAuth(): NetworkResult<String>

    suspend fun checkWallet(): NetworkResult<Boolean>

    suspend fun patchDeviceToken(deviceToken: String): NetworkResult<Unit>

    suspend fun deleteUser(): NetworkResult<Unit>

    suspend fun commonProfile(): NetworkResult<CommonProfile>

    suspend fun commonProfileNft(page: Int, size: Int, sort: Sort): NetworkResult<Contents<CommonProfileNft>>

    suspend fun memberProfile(memberId: Long): NetworkResult<MemberProfile>

    suspend fun memberProfileNFt(memberId: Long, page: Int, size: Int, sort: Sort): NetworkResult<Contents<CommonProfileNft>>

    suspend fun memberFollow(memberId: Long): NetworkResult<Unit>

    suspend fun commonFollow(page: Int, size: Int): NetworkResult<Contents<CommonFollow>>

    suspend fun commonFollowing(page: Int, size: Int): NetworkResult<Contents<CommonFollow>>

    suspend fun memberWallet(): NetworkResult<MemberWallet>

    suspend fun patchProfileImage(file: MultipartBody.Part): NetworkResult<Unit>

    suspend fun patchProfileDescription(description: String): NetworkResult<Unit>

    suspend fun patchProfileNickname(nickname: String): NetworkResult<Unit>

    suspend fun patchTempMemberPassword(): NetworkResult<Unit>

    suspend fun deleteNft(nftId: Long): NetworkResult<Unit>

    suspend fun memberSwap(page: Int, size: Int): NetworkResult<Contents<Swap>>

    suspend fun nftDetail(nftId: Long): NetworkResult<Nft>

    suspend fun transactionInfos(page: Int, size: Int): NetworkResult<Contents<TransactionInfo>>

    suspend fun saleTransactionInfos(page: Int, size: Int): NetworkResult<Contents<DealingHistory>>

    suspend fun purchaseTransactionInfos(page: Int, size: Int): NetworkResult<Contents<DealingHistory>>

    suspend fun main(): NetworkResult<Main>

    suspend fun soldOut(range: Int): NetworkResult<List<SoldOut>>

    suspend fun soldOutPage(range: Int, page: Int, size: Int): NetworkResult<Contents<SoldOut>>

    suspend fun recentNfts(page: Int, size: Int): NetworkResult<Contents<RecentNft>>

    suspend fun hotSellerPage(page: Int, size: Int): NetworkResult<Contents<HotSellerPage>>

    suspend fun hotMemberPage(page: Int, size: Int): NetworkResult<Contents<HotSellerPage>>

    suspend fun writePost(nftId: Long, title: String, content: String): NetworkResult<Unit>

    suspend fun patchPost(postId: Long, title: String, content: String): NetworkResult<Unit>

    suspend fun deletePost(postId: Long): NetworkResult<Unit>

    suspend fun posts(page: Int, size: Int): NetworkResult<Contents<Post>>

    suspend fun postsFromNft(nftId: Long, page: Int, size: Int): NetworkResult<Contents<Post>>

    suspend fun postDetail(postId: Long): NetworkResult<Post>

    suspend fun writePostComments(postId: Long, content: String): NetworkResult<Unit>

    suspend fun deletePostComments(commentId: Long): NetworkResult<Unit>

    suspend fun commentsFromPost(postId: Long, page: Int, size: Int): NetworkResult<Contents<Comment>>

    suspend fun hotPosts(page: Int, size: Int): NetworkResult<Contents<HotPost>>

    suspend fun ownNfts(page: Int, size: Int): NetworkResult<Contents<OwnNft>>

    suspend fun likedNfts(page: Int, size: Int): NetworkResult<Contents<OwnNft>>

    suspend fun hideNfts(page: Int, size: Int): NetworkResult<Contents<HideNft>>

    suspend fun hideMembers(page: Int, size: Int): NetworkResult<Contents<HideMember>>

    suspend fun block(type: Block, blockId: Long): NetworkResult<Unit>

    suspend fun cancelBlock(type: Block, blockId: Long): NetworkResult<Unit>

    suspend fun report(type: Report, reportedId: Long, description: String): NetworkResult<Unit>

    suspend fun nftLike(nftId: Long): NetworkResult<Unit>

    suspend fun makeAiPicture(payPwd: String, setence: String): NetworkResult<AiPicture>

    suspend fun readAlarm(alarmId: Long): NetworkResult<Unit>

    suspend fun alarms(page: Int, size: Int): NetworkResult<Contents<Alarm>>

    /**
     * 암호화 영역
     **/

    suspend fun getPublicKey(): NetworkResult<PublicKey>

    suspend fun createWallet(payPwd: String, checkPwd: String): NetworkResult<Unit>

    suspend fun patchPassword(nowPwd: String, changePwd: String): NetworkResult<Unit>

    suspend fun createNft(payPwd: String, title: String, description: String, image: String): NetworkResult<Long>

    suspend fun swapToDida(payPwd: String, coin: Int): NetworkResult<Unit>

    suspend fun swapToKlay(payPwd: String, coin: Int): NetworkResult<Unit>

    suspend fun sellNft(payPwd: String, nftId: Long, price: Double): NetworkResult<Unit>

    suspend fun cancelSellNft(pawPwd: String, marketId: Long): NetworkResult<Unit>

    suspend fun buyNft(payPwd: String, marketId: Long): NetworkResult<Unit>

    suspend fun checkPassword(payPwd: String): NetworkResult<Unit>

}

