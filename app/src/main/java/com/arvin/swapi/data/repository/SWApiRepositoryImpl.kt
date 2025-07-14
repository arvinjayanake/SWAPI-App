package com.arvin.swapi.data.repository

import com.arvin.swapi.data.remote.api.SWApiService
import com.arvin.swapi.data.remote.model.toDomain
import com.arvin.swapi.domain.model.Planet
import com.arvin.swapi.domain.repository.SWApiRepository

class SWApiRepositoryImpl(private val api: SWApiService) : SWApiRepository {

    override suspend fun getPlanets(page: Int): List<Planet> {
        return api.getPlanets(page).results.map { it.toDomain() }
    }

    override suspend fun getPlanetById(id: Int): Planet {
        return api.getPlanetById(id).toDomain()
    }

}