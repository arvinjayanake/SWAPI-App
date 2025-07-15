package com.arvin.swapi.data.remote.model

/**
 * Data Transfer Object (DTO) representing the paginated response from the SWAPI planets endpoint.
 *
 * Contains the list of planets for the current page, the total count, and pagination links.
 *
 * @property results The list of [PlanetDto] objects returned for the current page.
 * @property count The total number of planets available in the API.
 * @property next The URL for the next page of results, or null if this is the last page.
 * @property previous The URL for the previous page of results, or null if this is the first page.
 */
data class PlanetsResponseDto(
    val results: List<PlanetDto>,
    val count: Int,
    val next: String?,
    val previous: String?
)

