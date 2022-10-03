package com.dida.data.shareperference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dida.data.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferences(val context: Context) {

    private val Context.dataStore  by preferencesDataStore(name = "dataStore")

    // Token Variance
    private val accessTokenPreference = stringPreferencesKey("X-ACCESS-TOKEN")
    private val refreshTokenPreference = stringPreferencesKey("refresh-token")
    private val fcmTokenPreference = stringPreferencesKey("FCM-TOKEN")

    suspend fun setAccessToken(accessToken: String?, refreshToken: String?) {
        accessToken?.let {
            context.dataStore.edit { preference ->
                preference[accessTokenPreference] = it
            }
        }
        refreshToken?.let {
            context.dataStore.edit { preference ->
                preference[refreshTokenPreference] = it
            }
        }
    }

    fun getAccessToken(): Flow<String?> {
        return context.dataStore.data.map {
            it[accessTokenPreference]
        }
    }

    fun getRefreshToken(): Flow<String?> {
        return context.dataStore.data.map {
            it[refreshTokenPreference]
        }
    }

    suspend fun removeAccessToken() {
        context.dataStore.edit { preference ->
            preference[accessTokenPreference] = ""
            preference[refreshTokenPreference] = ""
        }
    }

    suspend fun setFcmToken(token: String?) {
        token?.let {
            context.dataStore.edit { preference ->
                preference[fcmTokenPreference] = it
            }
        }
    }

    fun getFcmToken(): Flow<String?> {
        return context.dataStore.data.map {
            it[fcmTokenPreference]
        }
    }
}