package com.arvin.swapi.domain.repository

import com.arvin.swapi.domain.model.Planet

interface SWApiRepository {
    suspend fun getPlanets(page: Int): List<Planet>
    suspend fun getPlanetById(id: Int): Planet
}