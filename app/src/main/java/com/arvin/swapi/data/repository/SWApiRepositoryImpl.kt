package com.arvin.swapi.data.repository

import com.arvin.swapi.data.remote.api.SWApiService
import com.arvin.swapi.data.remote.model.ApiResult
import com.arvin.swapi.data.remote.model.toDomain
import com.arvin.swapi.domain.model.Planet
import com.arvin.swapi.domain.repository.SWApiRepository
import retrofit2.HttpException
import java.io.IOException

class SWApiRepositoryImpl(private val api: SWApiService) : SWApiRepository {

    override suspend fun getPlanets(page: Int): ApiResult<List<Planet>> {
        return safeApiCall {
            api.getPlanets(page).results.map { it.toDomain() }
        }
    }

    override suspend fun getPlanetById(id: Int): ApiResult<Planet> {
        return safeApiCall {
            api.getPlanetById(id).toDomain()
        }
    }

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