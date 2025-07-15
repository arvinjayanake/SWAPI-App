package com.arvin.swapi.data.remote.model

import com.arvin.swapi.domain.model.Planet
import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) representing a planet as returned by the Star Wars API (SWAPI).
 *
 * Used for parsing the API response before mapping to the domain model.
 *
 * @property name Name of the planet.
 * @property orbitalPeriod Orbital period of the planet.
 * @property climate Description of the planet's climate.
 * @property gravity Description of the planet's gravity.
 * @property url API URL for the planet.
 */
data class PlanetDto(
    @SerializedName("name") val name: String?,
    @SerializedName("orbital_period") val orbitalPeriod: String?,
    @SerializedName("climate") val climate: String?,
    @SerializedName("gravity") val gravity: String?,
    @SerializedName("url") val url: String?
)

/**
 * Maps a [PlanetDto] to the [Planet] domain model.
 *
 * @return The corresponding [Planet] domain model instance.
 */
fun PlanetDto.toDomain(): Planet {
    return Planet(
        name = name,
        orbitalPeriod = orbitalPeriod,
        climate = climate,
        gravity = gravity,
        url = url
    )
}
