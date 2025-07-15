package com.arvin.swapi.domain.usecase

import com.arvin.swapi.data.remote.model.ApiResult
import com.arvin.swapi.domain.model.Planet
import com.arvin.swapi.domain.repository.PlanetRepository

/**
 * Use case class for planet-related operations.
 *
 * Acts as an intermediary between the presentation layer and the [PlanetRepository],
 * providing methods to retrieve planet lists and planet details.
 *
 * @property planetRepository The repository for planet data operations.
 */
class PlanetUseCase(
    private val planetRepository: PlanetRepository
) {

    /**
     * Retrieves a paginated list of planets.
     *
     * @param page The page number to retrieve.
     * @return [ApiResult] containing a list of [Planet] objects or error details.
     */
    suspend fun getPlanets(page: Int): ApiResult<List<Planet>> {
        return planetRepository.getPlanets(page)
    }

    /**
     * Retrieves details for a single planet by its unique ID.
     *
     * @param id The unique identifier of the planet.
     * @return [ApiResult] containing the [Planet] details or error information.
     */
    suspend fun getPlanetById(id: Int): ApiResult<Planet> {
        return planetRepository.getPlanetById(id)
    }

}