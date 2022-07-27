package com.dida.data

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.dida.data.shareperference.MySharedPreferences

class DataApplication(val context: Application){
    // 코틀린의 전역변수 문법
    companion object {
        // 만들어져있는 SharedPreferences 를 사용해야합니다. 재생성하지 않도록 유념해주세요
        lateinit var mySharedPreferences: MySharedPreferences
        lateinit var editor: SharedPreferences.Editor
    }

    fun getPreference() {
        mySharedPreferences = MySharedPreferences(context)
    }
}