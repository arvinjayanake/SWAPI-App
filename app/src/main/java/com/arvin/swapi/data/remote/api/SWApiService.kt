package com.arvin.swapi.data.remote.api

import com.arvin.swapi.data.remote.model.PlanetDto
import com.arvin.swapi.data.remote.model.PlanetsResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SWApiService {

    @GET("planets/")
    suspend fun getPlanets(@Query("page") page: Int): PlanetsResponseDto

    @GET("planets/{id}")
    suspend fun getPlanetById(@Path("id") id: Int): PlanetDto

}