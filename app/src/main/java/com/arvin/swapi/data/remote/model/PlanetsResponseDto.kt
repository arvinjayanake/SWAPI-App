package com.arvin.swapi.data.remote.model

data class PlanetsResponseDto(
    val results: List<PlanetDto>,
    val count: Int,
    val next: String?,
    val previous: String?
)

