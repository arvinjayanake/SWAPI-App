package com.arvin.swapi.data.repository

import com.arvin.swapi.data.remote.api.SWApiService
import com.arvin.swapi.data.remote.model.ApiResult
import com.arvin.swapi.data.remote.model.toDomain
import com.arvin.swapi.domain.model.Planet
import com.arvin.swapi.domain.repository.ApiRepository
import retrofit2.HttpException
import java.io.IOException

/**
 * Implementation of [ApiRepository] that interacts with the Star Wars API (SWAPI) using [SWApiService].
 *
 * Handles API requests for fetching planets and their details, mapping responses to domain models,
 * and wraps results in [ApiResult] for consistent error handling.
 *
 * @property api The Retrofit service used for API calls.
 */
class ApiRepositoryImpl(private val api: SWApiService) : ApiRepository {

    /**
     * Fetches a paginated list of planets from the API.
     *
     * @param page The page number to retrieve.
     * @return [ApiResult] containing either a list of [Planet] or error details.
     */
    override suspend fun getPlanets(page: Int): ApiResult<List<Planet>> {
        return safeApiCall {
            api.getPlanets(page).results.map { it.toDomain() }
        }
    }

    /**
     * Fetches the details of a single planet by its ID from the API.
     *
     * @param id The unique identifier of the planet.
     * @return [ApiResult] containing either a [Planet] or error details.
     */
    override suspend fun getPlanetById(id: Int): ApiResult<Planet> {
        return safeApiCall {
            api.getPlanetById(id).toDomain()
        }
    }

    /**
     * Safely executes an API call, wrapping the result in [ApiResult].
     *
     * Catches HTTP, network, and general exceptions, and converts them into [ApiResult.Error].
     *
     * @param apiCall The API call to execute.
     * @return [ApiResult] containing either the result or error details.
     */
    private inline fun <T> safeApiCall(apiCall: () -> T): ApiResult<T> {
        return try {
            ApiResult.Success(apiCall())
        } catch (e: HttpException) {
            val code = e.code()
            val message = e.response()?.errorBody()?.string() ?: e.message()
            ApiResult.Error(exception = e, statusCode = code, message = message)
        } catch (e: IOException) {
            ApiResult.Error(e, "Please check your internet connection.")
        } catch (e: Exception) {
            ApiResult.Error(e, e.localizedMessage)
        }
    }

}