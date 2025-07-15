package com.arvin.swapi.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import androidx.core.content.edit
import com.arvin.swapi.domain.repository.PreferencesRepository

/**
 * Contains keys used for storing values in SharedPreferences.
 *
 * Each constant represents a unique preference key for app-wide settings.
 */
private object PreferencesKeys {

    /** Key for storing the app's first launch date. */
    const val KEY_FIRST_LAUNCH_DATE = "pref_0001"

    /** Key for storing the app's last launch date. */
    const val KEY_LAST_LAUNCH_DATE = "pref_0002"

    /** Key for storing the FCM (Firebase Cloud Messaging) token. */
    const val KEY_FCM_TOKEN = "pref_0003"
}

/**
 * Repository for managing app preferences using [SharedPreferences].
 *
 * Stores and retrieves key application data such as launch dates and FCM tokens.
 * All string values are Base64-encoded before saving to ensure data integrity.
 *
 * @param context Application context for accessing [SharedPreferences].
 */
open class SharedPreferencesRepository(
    context: Context
) : PreferencesRepository {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    /**
     * The timestamp (in milliseconds) when the app was first launched.
     * Returns -1 if not set.
     */
    override var firstLaunchDate: Long
        get() = getLong(PreferencesKeys.KEY_FIRST_LAUNCH_DATE)
        set(value) {
            putLong(PreferencesKeys.KEY_FIRST_LAUNCH_DATE, value)
        }

    /**
     * The timestamp (in milliseconds) when the app was last launched.
     * Returns -1 if not set.
     */
    override var lastLaunchDate: Long
        get() = getLong(PreferencesKeys.KEY_LAST_LAUNCH_DATE)
        set(value) {
            putLong(PreferencesKeys.KEY_LAST_LAUNCH_DATE, value)
        }

    /**
     * The Firebase Cloud Messaging token for this device.
     * Value is stored as a Base64-encoded string.
     */
    override var fcmToken: String?
        get() = getString(PreferencesKeys.KEY_FCM_TOKEN)
        set(value) {
            putString(PreferencesKeys.KEY_FCM_TOKEN, value)
        }

    /**
     * Clears all stored preferences.
     */
    override fun clear() {
        prefs.edit { clear() }
    }

    /**
     * Retrieves a [Long] value from preferences, or -1 if not present.
     */
    private fun getLong(key: String): Long {
        return prefs.getLong(key, -1)
    }

    /**
     * Stores a [Long] value in preferences.
     */
    private fun putLong(key: String, value: Long) {
        prefs.edit { putLong(key, value).apply() }
    }

    /**
     * Retrieves a decoded string from preferences, or null if not set or on error.
     */
    private fun getString(key: String): String? {
        val encoded = prefs.getString(key, null) ?: return null
        return try {
            String(Base64.decode(encoded, Base64.DEFAULT), Charsets.UTF_8)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Stores a Base64-encoded string in preferences.
     */
    private fun putString(key: String, value: String?) {
        val encoded = value?.let {
            Base64.encodeToString(it.toByteArray(Charsets.UTF_8), Base64.DEFAULT)
        }
        prefs.edit { putString(key, encoded).apply() }
    }

}


