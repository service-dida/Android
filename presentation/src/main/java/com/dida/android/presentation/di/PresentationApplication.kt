package com.dida.android.presentation.di

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.dida.android.BuildConfig
import com.dida.data.DataApplication.Companion.dataStorePreferences
import com.dida.data.shareperference.DataStorePreferences
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility.getKeyHash
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PresentationApplication :Application(){

    override fun onCreate() {
        super.onCreate()
        // 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        dataStorePreferences = DataStorePreferences(applicationContext)
        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
        // kakao hash key 추출
        Log.d("getKeyHash", "" + getKeyHash(this))
    }
}
