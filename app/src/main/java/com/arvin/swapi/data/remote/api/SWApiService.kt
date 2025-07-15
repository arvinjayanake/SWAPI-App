package com.arvin.swapi.data.remote.api

import com.arvin.swapi.data.remote.model.PlanetDto
import com.arvin.swapi.data.remote.model.PlanetsResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit service interface for accessing Star Wars API (SWAPI) endpoints related to planets.
 */
interface SWApiService {

    /**
     * Fetches a paginated list of planets from SWAPI.
     *
     * @param page The page number to retrieve.
     * @return [PlanetsResponseDto] containing a list of planets and pagination info.
     */
    @GET("planets/")
    suspend fun getPlanets(@Query("page") page: Int): PlanetsResponseDto

    /**
     * Fetches detailed information for a single planet by its ID.
     *
     * @param id The unique identifier of the planet.
     * @return [PlanetDto] containing details of the specified planet.
     */
    @GET("planets/{id}")
    suspend fun getPlanetById(@Path("id") id: Int): PlanetDto

}