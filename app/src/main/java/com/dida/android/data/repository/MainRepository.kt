package com.dida.android.data.repository

import javax.inject.Inject

class MainRepository @Inject constructor(private val mainAPIService: MainAPIService){

    suspend fun checkVersion() = mainAPIService.checkVersion()

}