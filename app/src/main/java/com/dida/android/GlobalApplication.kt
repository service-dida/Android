package com.dida.android

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit

@HiltAndroidApp
class GlobalApplication :Application(){
    // 코틀린의 전역변수 문법
    companion object {
        // 만들어져있는 SharedPreferences 를 사용해야합니다. 재생성하지 않도록 유념해주세요
        lateinit var sSharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
    }

    override fun onCreate() {
        super.onCreate()
        // 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        sSharedPreferences =
            applicationContext.getSharedPreferences("DIDA", MODE_PRIVATE)

        editor = sSharedPreferences.edit()

        // Kakao SDK 초기화
//        KakaoSdk.init(this, "5820a967bb81030d649333f17f04f062")
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