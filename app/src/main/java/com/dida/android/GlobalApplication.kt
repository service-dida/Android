package com.dida.android

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.dida.android.data.MySharedPreferences
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit

@HiltAndroidApp
class GlobalApplication :Application(){
    // 코틀린의 전역변수 문법
    companion object {
        // 만들어져있는 SharedPreferences 를 사용해야합니다. 재생성하지 않도록 유념해주세요
        lateinit var mySharedPreferences: MySharedPreferences
        lateinit var editor: SharedPreferences.Editor
    }

    override fun onCreate() {
        super.onCreate()
        // 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        mySharedPreferences = MySharedPreferences(applicationContext)

        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
        // kakao hash key 추출
//        Log.d("getKeyHash", "" + getKeyHash(this))

        // fire base settings
//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.w("response!", "Fetching FCM registration token failed", task.exception)
//                return@OnCompleteListener
//            }
//
//            // Get new FCM registration token
//            val token = task.result
//            task.result
//
//            Log.d("fcm_response!", token!!)
//            editor.putString("deviceToken", token)
//            editor.commit()
//        })
    }
}