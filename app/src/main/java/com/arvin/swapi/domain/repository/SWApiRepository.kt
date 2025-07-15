package com.arvin.swapi.domain.repository

import com.arvin.swapi.data.remote.model.ApiResult
import com.arvin.swapi.domain.model.Planet

interface SWApiRepository {
    suspend fun getPlanets(page: Int): ApiResult<List<Planet>>
    suspend fun getPlanetById(id: Int): ApiResult<Planet>
}