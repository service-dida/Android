package com.dida.domain.usecase.klaytn

import com.dida.domain.NetworkResult
import com.dida.domain.model.klaytn.Asset
import com.dida.domain.model.login.LoginResponseModel
import com.dida.domain.model.login.NicknameResponseModel
import com.dida.domain.model.nav.createwallet.RandomNumber
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.home.SoldOut
import com.dida.domain.model.nav.mypage.UserCardsResponseModel
import com.dida.domain.model.nav.mypage.UserProfileResponseModel
import com.dida.domain.model.splash.AppVersionResponse
import com.dida.domain.repository.KlaytnRepository
import com.dida.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named


class UploadAssetUsecase @Inject constructor(
    private val repository: KlaytnRepository
){
    suspend operator fun invoke(file: MultipartBody.Part) : NetworkResult<Asset> {
        return repository.uploadAsset(file)
    }
}