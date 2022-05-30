package com.dida.android.data.repository

import com.dida.android.domain.model.login.NicknameResponseModel
import com.dida.android.domain.usecase.MainAPIService
import com.dida.android.presentation.viewmodel.login.NicknameViewModel
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainAPIService: MainAPIService){
    suspend fun checkVersion() = mainAPIService.checkVersion()

    fun loginAPIServer(idToken : String) = mainAPIService.loginAPIServer(idToken = idToken)

    fun nicknamePIServer(nickName : String) = mainAPIService.nicknameAPIServer(nickName = nickName)

//    suspend fun nicknamePIServer(nickName : String): Response<NicknameResponseModel> {
//        return mainAPIService.nicknameAPIServer(nickName)
//    }
}