package com.dida.data.shareperference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.data.R

class MySharedPreferences(context: Context) {
    private val prefs : SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.prefs_key),MODE_PRIVATE)

    fun setAccessToken(accessToken: String?, refreshToken: String?) {
        var editor = prefs.edit()
        editor.putString("X-ACCESS-TOKEN", accessToken)
        editor.putString("refresh-token", refreshToken)
        editor.commit()
    }

    fun getAccessToken(): String? {
        var token = prefs.getString("X-ACCESS-TOKEN", null)
        return token
    }

    fun getRefreshToken(): String?{
        var token = prefs.getString("refresh-token", null)
        return token
    }

    fun removeAccessToken(){
        var editor = prefs.edit()
        editor.putString("X-ACCESS-TOKEN", "")
        editor.putString("refresh-token", "")
        editor.commit()
    }

    fun getHeaderToken(): String?{
        var token = prefs.getString("X-ACCESS-TOKEN", null)
        return "Bearer "+token
    }

    fun getRefreshHeaderToken(): String?{
        var token = prefs.getString("refresh-token", null)
        return "Bearer "+token
    }

    fun setFCM(token: String?) {
        var editor = prefs.edit()
        editor.putString("FCM", token!!)
        editor.commit()
    }

    fun getFCM(context: Context): String? {
        var token = prefs.getString("FCM", null)
        return token
    }
}