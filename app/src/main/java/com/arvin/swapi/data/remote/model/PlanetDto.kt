package com.arvin.swapi.data.remote.model

import com.arvin.swapi.domain.model.Planet

data class PlanetDto(
    val name: String?,
    val orbital_period: String?,
    val climate: String?,
    val gravity: String?,
    val url: String?
)

fun PlanetDto.toDomain(): Planet {
    return Planet(
        name = name,
        orbital_period = orbital_period,
        climate = climate,
        gravity = gravity,
        url = url
    )
}
