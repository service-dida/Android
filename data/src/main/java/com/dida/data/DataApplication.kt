package com.dida.data

import android.annotation.SuppressLint
import android.app.Application
import com.dida.data.shareperference.DataStorePreferences

class DataApplication : Application() {
    // 코틀린의 전역변수 문법
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var dataStorePreferences: DataStorePreferences
    }

    override fun onCreate() {
        super.onCreate()
        dataStorePreferences = DataStorePreferences(applicationContext)
    }
}
