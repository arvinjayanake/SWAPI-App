package com.arvin.swapi.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import androidx.core.content.edit
import com.arvin.swapi.domain.repository.PreferencesRepository

open class SharedPreferencesRepository(
    context: Context
) : PreferencesRepository {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    override var firstLaunchDate: Long
        get() = getLong(PreferencesKeys.KEY_FIRST_LAUNCH_DATE)
        set(value) {
            putLong(PreferencesKeys.KEY_FIRST_LAUNCH_DATE, value)
        }

    override var lastLaunchDate: Long
        get() = getLong(PreferencesKeys.KEY_LAST_LAUNCH_DATE)
        set(value) {
            putLong(PreferencesKeys.KEY_LAST_LAUNCH_DATE, value)
        }

    override var fcmToken: String?
        get() = getString(PreferencesKeys.KEY_FCM_TOKEN)
        set(value) {
            putString(PreferencesKeys.KEY_FCM_TOKEN, value)
        }

    override fun clear() {
        prefs.edit { clear() }
    }

    private fun getLong(key: String): Long {
        return prefs.getLong(key, -1)
    }

    private fun putLong(key: String, value: Long) {
        prefs.edit { putLong(key, value).apply() }
    }

    private fun getString(key: String): String? {
        val encoded = prefs.getString(key, null) ?: return null
        return try {
            String(Base64.decode(encoded, Base64.DEFAULT), Charsets.UTF_8)
        } catch (e: Exception) {
            null
        }
    }

    private fun putString(key: String, value: String?) {
        val encoded = value?.let {
            Base64.encodeToString(it.toByteArray(Charsets.UTF_8), Base64.DEFAULT)
        }
        prefs.edit { putString(key, encoded).apply() }
    }

}


