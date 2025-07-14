package com.arvin.swapi.domain.usecase

import com.arvin.swapi.domain.model.Planet
import com.arvin.swapi.domain.repository.SWApiRepository

class PlanetUseCase(private val repository: SWApiRepository) {

    suspend fun getPlanets(page: Int): List<Planet> {
        return repository.getPlanets(page)
    }

    suspend fun getPlanet(id: Int): Planet {
        return repository.getPlanetById(id)
    }

}