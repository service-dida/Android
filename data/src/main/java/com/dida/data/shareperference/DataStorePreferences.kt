package com.dida.data.shareperference

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dida.data.R
import com.dida.domain.main.model.Keywords
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class DataStorePreferences(val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = context.getString(R.string.prefs_key))

    // Token Variance
    private val accessTokenPreference = stringPreferencesKey("X-ACCESS-TOKEN")
    private val refreshTokenPreference = stringPreferencesKey("refresh-token")
    private val fcmTokenPreference = stringPreferencesKey("FCM-TOKEN")
    private val fcmIndexPreference = intPreferencesKey("FCM-INDEX")
    private val userIdPreferences = longPreferencesKey("USER-ID")
    private val thingsPreference = stringPreferencesKey("thingsPreference")
    private val placesPreference = stringPreferencesKey("placesPreference")

    suspend fun setUserId(userId: Long) {
        context.dataStore.edit { preference ->
            preference[userIdPreferences] = userId
        }
    }

    suspend fun getUserId(): Long {
        return context.dataStore.data.first().let {
            it[userIdPreferences] ?: -1
        }
    }

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

    suspend fun getAccessToken(): String? {
        return context.dataStore.data.first().let {
            it[accessTokenPreference]
        }
    }

    suspend fun getRefreshToken(): String? {
        return context.dataStore.data.first().let {
            it[refreshTokenPreference]
        }
    }

    suspend fun removeAccountToken() {
        context.dataStore.edit { preference ->
            preference.clear()
        }
    }

    suspend fun setFcmToken(token: String?) {
        token?.let {
            context.dataStore.edit { preference ->
                preference[fcmTokenPreference] = it
            }
        }
    }

    suspend fun getFcmToken(): String? {
        return context.dataStore.data.first().let {
            it[fcmTokenPreference]
        }
    }

    suspend fun setFcmIndex(index: Int?) {
        index?.let {
            context.dataStore.edit { preference ->
                preference[fcmIndexPreference] = it
            }
        }
    }

    suspend fun getFcmIndex(): Int? {
        if (context.dataStore.data.first()[fcmIndexPreference] == null) {
            setFcmIndex(0)
            return 0
        } else {
            return context.dataStore.data.first()[fcmIndexPreference]
        }
    }

    suspend fun setKeywords(keywords: Keywords) {
        context.dataStore.edit { preference ->
            preference[thingsPreference] = keywords.things.joinToString(",")
            preference[placesPreference] = keywords.places.joinToString(",")
        }
    }

    suspend fun getKeywordThings(): List<String> {
        return context.dataStore.data.firstOrNull()?.let {
            it[thingsPreference]?.split(",")?.shuffled()?.slice(0..9) ?: emptyList<String>()
        } ?: emptyList<String>()
    }

    suspend fun getKeywordPlaces(): List<String> {
        return context.dataStore.data.firstOrNull()?.let {
            it[placesPreference]?.split(",")?.shuffled()?.slice(0..9) ?: emptyList<String>()
        } ?: emptyList<String>()
    }
}
