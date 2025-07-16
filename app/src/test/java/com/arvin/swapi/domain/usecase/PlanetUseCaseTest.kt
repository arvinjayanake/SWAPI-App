package com.arvin.swapi.domain.usecase

import com.arvin.swapi.data.remote.model.ApiResult
import com.arvin.swapi.domain.model.Planet
import com.arvin.swapi.domain.repository.PlanetRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class PlanetUseCaseTest {

    private lateinit var planetRepository: PlanetRepository
    private lateinit var planetUseCase: PlanetUseCase

    @Before
    fun setUp() {
        planetRepository = mock()
        planetUseCase = PlanetUseCase(planetRepository)
    }

    @Test
    fun `getPlanets returns success result`() = runTest {
        // Given
        val fakePlanets = listOf(
            Planet(
                name = "Tatooine",
                orbitalPeriod = "304",
                climate = "arid",
                gravity = "1 standard",
                url = "https://swapi.dev/api/planets/1/"
            ),
            Planet(
                name = "Alderaan",
                orbitalPeriod = "364",
                climate = "temperate",
                gravity = "1 standard",
                url = "https://swapi.dev/api/planets/2/"
            )
        )
        whenever(planetRepository.getPlanets(1)).thenReturn(ApiResult.Success(fakePlanets))

        // When
        val result = planetUseCase.getPlanets(1)

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(2, (result as ApiResult.Success).data.size)
        assertEquals("Tatooine", result.data[0].name)
    }

    @Test
    fun `getPlanets returns error result`() = runTest {
        // Given
        val exception = Exception("Network error")
        whenever(planetRepository.getPlanets(1)).thenReturn(
            ApiResult.Error(
                exception,
                "Network error",
                500
            )
        )

        // When
        val result = planetUseCase.getPlanets(1)

        // Then
        assertTrue(result is ApiResult.Error)
        assertEquals("Network error", (result as ApiResult.Error).message)
        assertEquals(500, result.statusCode)
        assertEquals(exception, result.exception)
    }

    @Test
    fun `getPlanetById returns success result`() = runTest {
        // Given
        val fakePlanet = Planet(
            name = "Tatooine",
            orbitalPeriod = "304",
            climate = "arid",
            gravity = "1 standard",
            url = "https://swapi.dev/api/planets/1/"
        )
        whenever(planetRepository.getPlanetById(1)).thenReturn(ApiResult.Success(fakePlanet))

        // When
        val result = planetUseCase.getPlanetById(1)

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals("Tatooine", (result as ApiResult.Success).data.name)
    }

    @Test
    fun `getPlanetById returns error result`() = runTest {
        // Given
        val exception = Exception("Not found")
        whenever(planetRepository.getPlanetById(1)).thenReturn(
            ApiResult.Error(
                exception,
                "Not found",
                404
            )
        )

        // When
        val result = planetUseCase.getPlanetById(1)

        // Then
        assertTrue(result is ApiResult.Error)
        assertEquals("Not found", (result as ApiResult.Error).message)
        assertEquals(404, result.statusCode)
        assertEquals(exception, result.exception)
    }
}