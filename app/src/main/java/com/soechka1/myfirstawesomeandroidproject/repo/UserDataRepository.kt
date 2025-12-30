@file:Suppress("DEPRECATION")

package com.soechka1.myfirstawesomeandroidproject.repo

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.soechka1.myfirstawesomeandroidproject.Keys
import androidx.core.content.edit

object UserDataRepository {

    private const val PREFS_NAME = Keys.TOKEN_SHARED_PREF_NAME
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        val appContext = context.applicationContext

        val masterKey = MasterKey.Builder(appContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        // для "безопасности", а так бы еще локальную базу зашифровать чтобы смысл был...
        sharedPreferences = EncryptedSharedPreferences.create(
            appContext,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveData(key: String, data: String) {
        check(::sharedPreferences.isInitialized) { "UserDataRepository must be init" }
        sharedPreferences.edit { putString(key, data) }
    }

    fun remove(key: String) {
        check(::sharedPreferences.isInitialized) { "UserDataRepository must be init" }
        sharedPreferences.edit { remove(key) }
    }

    fun getData(key: String): String? {
        check(::sharedPreferences.isInitialized) { "UserDataRepository must be init" }
        return sharedPreferences.getString(key, null)
    }


    fun saveToken(token: String) {
        UserDataRepository.saveData(Keys.TOKEN, token)
    }

    fun getToken(): String? {
        return UserDataRepository.getData(Keys.TOKEN)
    }

    fun clear() {
        UserDataRepository.remove(Keys.TOKEN)
    }

}
