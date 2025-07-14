package com.arvin.swapi.domain.repository

interface PreferencesRepository {
    var firstLaunchDate: Long
    var lastLaunchDate: Long
    var fcmToken: String?
    fun clear()
}