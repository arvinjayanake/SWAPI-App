package com.arvin.swapi.domain.model

/**
 * Domain model representing a planet.
 *
 * Used throughout the app for business logic and UI display, independent of data source.
 *
 * @property name Name of the planet.
 * @property orbitalPeriod Orbital period of the planet.
 * @property climate Description of the planet's climate.
 * @property gravity Description of the planet's gravity.
 * @property url Unique API URL identifying the planet.
 */
data class Planet(

    val name: String?,
    val orbitalPeriod: String?,
    val climate: String?,
    val gravity: String?,
    val url: String?

)

/**
 * Returns the URL for a high-resolution placeholder image for this planet.
 *
 * Uses the Picsum Photos service with a unique ID based on the planet's URL.
 *
 * @return The high-res image URL as a [String].
 */
fun Planet.getHighResImgUrl(): String {
    return "https://picsum.photos/id/${getIdFromUrl()}/1000/1000"
}

/**
 * Returns the URL for a thumbnail placeholder image for this planet.
 *
 * Uses the Picsum Photos service with a unique ID based on the planet's URL.
 *
 * @return The thumbnail image URL as a [String].
 */
fun Planet.getThumbnailImgUrl(): String {
    return "https://picsum.photos/id/${getIdFromUrl()}/300/300"
}

/**
 * Extracts the numeric planet ID from its URL.
 *
 * The ID is used for image generation and internal identification.
 * Returns 0 if the URL is null or the ID cannot be parsed.
 *
 * @return The planet ID as an [Int], or 0 if extraction fails.
 */
fun Planet.getIdFromUrl(): Int {
    return url?.trimEnd('/')
        ?.split('/')
        ?.lastOrNull()
        ?.toIntOrNull() ?: 0
}