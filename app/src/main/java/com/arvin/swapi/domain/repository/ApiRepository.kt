package com.arvin.swapi.domain.repository

import com.arvin.swapi.data.remote.model.ApiResult
import com.arvin.swapi.domain.model.Planet

/**
 * Repository interface for remote API operations related to planets.
 *
 * Provides methods to fetch a paginated list of planets and details for a single planet from the API,
 * returning results wrapped in [ApiResult] for error handling.
 */
interface ApiRepository {

    /**
     * Fetches a paginated list of planets from the remote API.
     *
     * @param page The page number to retrieve.
     * @return [ApiResult] containing either a list of [Planet] or error details.
     */
    suspend fun getPlanets(page: Int): ApiResult<List<Planet>>

    /**
     * Fetches details for a single planet by its unique ID from the remote API.
     *
     * @param id The unique identifier of the planet.
     * @return [ApiResult] containing either the [Planet] details or error information.
     */
    suspend fun getPlanetById(id: Int): ApiResult<Planet>
}