package com.arvin.swapi.domain.repository

/**
 * Repository interface for managing application preferences and simple key-value storage.
 *
 * Defines properties for storing launch dates and the FCM (Firebase Cloud Messaging) token,
 * and provides a method to clear all stored preferences.
 */
interface PreferencesRepository {

    /**
     * The timestamp (in milliseconds) when the app was first launched.
     */
    var firstLaunchDate: Long

    /**
     * The timestamp (in milliseconds) when the app was last launched.
     */
    var lastLaunchDate: Long

    /**
     * The Firebase Cloud Messaging (FCM) token for the device.
     */
    var fcmToken: String?

    /**
     * Clears all stored preferences.
     */
    fun clear()
}