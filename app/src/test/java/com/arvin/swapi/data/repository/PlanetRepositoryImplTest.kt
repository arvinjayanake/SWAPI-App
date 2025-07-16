package com.arvin.swapi.data.repository

import com.arvin.swapi.data.local.db.entity.PlanetEntity
import com.arvin.swapi.data.mapper.toEntityList
import com.arvin.swapi.data.remote.model.ApiResult
import com.arvin.swapi.domain.model.Planet
import com.arvin.swapi.domain.repository.ApiRepository
import com.arvin.swapi.domain.repository.DBRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PlanetRepositoryImplTest {

    private lateinit var apiRepository: ApiRepository
    private lateinit var dbRepository: DBRepository
    private lateinit var planetRepository: PlanetRepositoryImpl

    @Before
    fun setUp() {
        apiRepository = mock()
        dbRepository = mock()
        planetRepository = PlanetRepositoryImpl(apiRepository, dbRepository)
    }

    @Test
    fun `getPlanets returns API data and caches it when API call is successful`() = runTest {
        val apiPlanets = listOf(
            Planet("Tatooine",
                "23",
                "arid",
                "1 standard",
                "https://swapi.dev/api/planets/1/"),
            Planet("Alderaan",
                "24",
                "temperate",
                "1 standard",
                "https://swapi.dev/api/planets/2/")
        )
        whenever(apiRepository.getPlanets(1)).thenReturn(ApiResult.Success(apiPlanets))

        val result = planetRepository.getPlanets(1)

        assertTrue(result is ApiResult.Success)
        verify(dbRepository).insertPlanets(apiPlanets.toEntityList())
        assertEquals("Tatooine", (result as ApiResult.Success).data[0].name)
    }

    @Test
    fun `getPlanets falls back to cache if API fails and cache is not empty`() = runTest {
        whenever(apiRepository.getPlanets(1)).thenReturn(
            ApiResult.Error(
                Exception("No internet"),
                "No internet",
                500
            )
        )
        val cacheEntities = listOf(
            PlanetEntity(1,
                "Cached",
                "30",
                "dry",
                "1G",
                "https://swapi.dev/api/planets/1/")
        )
        whenever(dbRepository.getPlanets(10, 0))
            .thenReturn(cacheEntities)

        val result = planetRepository.getPlanets(1)

        assertTrue(result is ApiResult.Success)
        assertEquals("Cached", (result as ApiResult.Success).data[0].name)
    }

    @Test
    fun `getPlanets returns API error if API fails and cache is empty`() = runTest {
        whenever(apiRepository.getPlanets(1)).thenReturn(
            ApiResult.Error(
                Exception("No internet"),
                "No internet",
                500
            )
        )
        whenever(dbRepository.getPlanets(10, 0)).thenReturn(emptyList())

        val result = planetRepository.getPlanets(1)

        assertTrue(result is ApiResult.Error)
        assertEquals("No internet", (result as ApiResult.Error).message)
    }

    @Test
    fun `getPlanets does not fall back to cache on 404 error`() = runTest {
        whenever(apiRepository.getPlanets(1)).thenReturn(
            ApiResult.Error(
                Exception("Not found"),
                "Not found",
                404
            )
        )

        val result = planetRepository.getPlanets(1)

        assertTrue(result is ApiResult.Error)
        verify(dbRepository, never()).getPlanets(any(), any())
    }

    @Test
    fun `getPlanetById returns API data if API is successful`() = runTest {
        val planet =
            Planet(
                "Alderaan",
                "364",
                "temperate",
                "1 standard",
                "https://swapi.dev/api/planets/2/"
            )
        whenever(apiRepository.getPlanetById(2))
            .thenReturn(ApiResult.Success(planet))

        val result = planetRepository.getPlanetById(2)

        assertTrue(result is ApiResult.Success)
        assertEquals("Alderaan", (result as ApiResult.Success).data.name)
        verify(dbRepository, never()).getPlanetById(any())
    }

    @Test
    fun `getPlanetById falls back to cache if API fails and cache is not null`() = runTest {
        whenever(apiRepository.getPlanetById(3)).thenReturn(
            ApiResult.Error(
                Exception("API down"),
                "API down",
                500
            )
        )
        val cachedEntity =
            PlanetEntity(3,
                "Dagobah",
                "341",
                "murky",
                "N/A",
                "https://swapi.dev/api/planets/3/")
        whenever(dbRepository.getPlanetById(3)).thenReturn(cachedEntity)

        val result = planetRepository.getPlanetById(3)

        assertTrue(result is ApiResult.Success)
        assertEquals("Dagobah", (result as ApiResult.Success).data.name)
    }

    @Test
    fun `getPlanetById returns API error if API fails and cache is null`() = runTest {
        whenever(apiRepository.getPlanetById(5)).thenReturn(
            ApiResult.Error(
                Exception("API down"),
                "API down",
                500
            )
        )
        whenever(dbRepository.getPlanetById(5)).thenReturn(null)

        val result = planetRepository.getPlanetById(5)

        assertTrue(result is ApiResult.Error)
        assertEquals("API down", (result as ApiResult.Error).message)
    }
}