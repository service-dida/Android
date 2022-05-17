package com.dida.android.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log

class SharedPreferences {
    fun setAccessToken(context: Context, accessToken: String, refreshToken: String) {
        var text = context.getSharedPreferences("DIDA", MODE_PRIVATE)
        var editor = text.edit()
        editor.putString("X-ACCESS-TOKEN", accessToken)
        editor.putString("refresh-token", refreshToken)
        editor.commit()
    }

    fun getAccessToken(context: Context): String? {
        // shared preference
        var text = context.getSharedPreferences("DIDA", MODE_PRIVATE)
        var token = text.getString("X-ACCESS-TOKEN", null)
        return token
    }

    fun getRefreshToken(context: Context): String?{
        // shared preference
        var text = context.getSharedPreferences("DIDA", MODE_PRIVATE)
        var token = text.getString("refresh-token", null)
        return token
    }

    fun getHeaderToken(context: Context): String?{
        // shared preference
        var text = context.getSharedPreferences("DIDA", MODE_PRIVATE)
        var token = text.getString("X-ACCESS-TOKEN", null)
        return "Bearer "+token
    }

    fun getRefreshHeaderToken(context: Context): String?{
        // shared preference
        var text = context.getSharedPreferences("DIDA", MODE_PRIVATE)
        var token = text.getString("refresh-token", null)
        return "Bearer "+token
    }

    fun setFCM(context: Context, token: String?) {
        var text = context.getSharedPreferences("DIDA", MODE_PRIVATE)
        var editor = text.edit()
        editor.putString("FCM", token!!)
        editor.commit()
    }

    fun getFCM(context: Context): String? {
        // shared preference
        var text = context.getSharedPreferences("DIDA", MODE_PRIVATE)
        var token = text.getString("FCM", null)
        return token
    }
}