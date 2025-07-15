package com.arvin.swapi.data.repository

import com.arvin.swapi.data.mapper.toEntityList
import com.arvin.swapi.data.mapper.toPlanet
import com.arvin.swapi.data.remote.model.ApiResult
import com.arvin.swapi.domain.model.Planet
import com.arvin.swapi.domain.repository.ApiRepository
import com.arvin.swapi.domain.repository.DBRepository
import com.arvin.swapi.domain.repository.PlanetRepository

/**
 * Implementation of [PlanetRepository] that coordinates data retrieval from both remote (API) and local (database) sources.
 *
 * Attempts to fetch planets from the remote API, and on failure (except for 404 errors),
 * falls back to cached data from the local database.
 * Successful API responses are also cached locally for offline access.
 *
 * @property apiRepository Repository for handling remote API operations.
 * @property dbRepository Repository for handling local database operations.
 */
class PlanetRepositoryImpl(
    private val apiRepository: ApiRepository,
    private val dbRepository: DBRepository
) : PlanetRepository {

    /**
     * Retrieves a paginated list of planets, prioritizing the remote API.
     * On API failure (except for 404), attempts to return cached results from the local database.
     * Successful API responses are cached locally for offline access.
     *
     * @param page The page number to retrieve.
     * @return [ApiResult] containing a list of [Planet] objects or error details.
     */
    override suspend fun getPlanets(page: Int): ApiResult<List<Planet>> {
        val result = apiRepository.getPlanets(page)

        if (result is ApiResult.Success) {
            dbRepository.insertPlanets(result.data.toEntityList())
        } else if (result is ApiResult.Error) {

            if (result.statusCode != 404) {
                try {
                    val planets =
                        dbRepository.getPlanets(limit = 10, offset = ((page - 1) * 10)).map {
                            it.toPlanet()
                        }

                    if (!planets.isEmpty()) {
                        return ApiResult.Success(data = planets)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        return result
    }

    /**
     * Retrieves details for a single planet by ID, prioritizing the remote API.
     * On API failure, attempts to return cached data from the local database if available.
     *
     * @param id The unique identifier of the planet.
     * @return [ApiResult] containing the [Planet] details or error information.
     */
    override suspend fun getPlanetById(id: Int): ApiResult<Planet> {
        val result = apiRepository.getPlanetById(id)

        if (result is ApiResult.Error) {
            try {
                val planet = dbRepository.getPlanetById(id)

                if (planet != null) {
                    return ApiResult.Success(data = planet.toPlanet())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return result
    }
}