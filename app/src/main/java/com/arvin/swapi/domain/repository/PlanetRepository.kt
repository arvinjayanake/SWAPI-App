package com.arvin.swapi.domain.repository

import com.arvin.swapi.data.remote.model.ApiResult
import com.arvin.swapi.domain.model.Planet

/**
 * Repository interface that abstracts planet data retrieval and storage,
 * coordinating between remote API and local database sources as needed.
 *
 * Provides unified methods for obtaining planet lists and planet details,
 * returning results wrapped in [ApiResult] for standardized error handling.
 */
interface PlanetRepository {

    /**
     * Retrieves a paginated list of planets.
     * May use remote or local data sources depending on network availability and implementation.
     *
     * @param page The page number to retrieve.
     * @return [ApiResult] containing a list of [Planet] objects or error details.
     */
    suspend fun getPlanets(page: Int): ApiResult<List<Planet>>

    /**
     * Retrieves details for a single planet by its unique ID.
     * May use remote or local data sources depending on network availability and implementation.
     *
     * @param id The unique identifier of the planet.
     * @return [ApiResult] containing the [Planet] details or error information.
     */
    suspend fun getPlanetById(id: Int): ApiResult<Planet>

}