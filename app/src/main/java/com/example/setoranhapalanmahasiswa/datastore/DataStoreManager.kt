package com.example.setoranhapalanmahasiswa.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("user_preferences")

class DataStoreManager @Inject constructor(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val NIM_KEY = stringPreferencesKey("nim")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { it[TOKEN_KEY] = token }
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        context.dataStore.edit { it[REFRESH_TOKEN_KEY] = refreshToken }
    }

    suspend fun saveUserProfile(name: String, email: String, nim: String) {
        context.dataStore.edit {
            it[NAME_KEY] = name
            it[EMAIL_KEY] = email
            it[NIM_KEY] = nim
        }
    }

    val token: Flow<String?> = context.dataStore.data.map { it[TOKEN_KEY] }

    suspend fun getRefreshToken(): String? =
        context.dataStore.data.map { it[REFRESH_TOKEN_KEY] }.firstOrNull()

    val userName: Flow<String?> = context.dataStore.data.map { it[NAME_KEY] }

    val userEmail: Flow<String?> = context.dataStore.data.map { it[EMAIL_KEY] }

    val userNim: Flow<String?> = context.dataStore.data.map { it[NIM_KEY] }

    suspend fun clearToken() {
        context.dataStore.edit { it.remove(TOKEN_KEY) }
    }

    suspend fun clearRefreshToken() {
        context.dataStore.edit { it.remove(REFRESH_TOKEN_KEY) }
    }

    suspend fun clearUserProfile() {
        context.dataStore.edit {
            it.remove(NAME_KEY)
            it.remove(EMAIL_KEY)
            it.remove(NIM_KEY)
        }
    }

    suspend fun clearAllData() {
        context.dataStore.edit { it.clear() }
    }
}
