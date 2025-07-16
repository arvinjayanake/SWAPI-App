package com.arvin.swapi.data.repository

import com.arvin.swapi.data.remote.api.SWApiService
import com.arvin.swapi.data.remote.model.ApiResult
import com.arvin.swapi.data.remote.model.PlanetDto
import com.arvin.swapi.data.remote.model.PlanetsResponseDto
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApiRepositoryImplTest {

    private lateinit var api: SWApiService
    private lateinit var repository: ApiRepositoryImpl

    @Before
    fun setup() {
        api = mock()
        repository = ApiRepositoryImpl(api)
    }

    @Test
    fun `getPlanets returns Success when API call is successful`() = runTest {
        val planetDto =
            PlanetDto(
                "Tatooine",
                "304",
                "arid",
                "1 standard",
                "https://swapi.dev/api/planets/1/"
            )
        val response = PlanetsResponseDto(
            results = listOf(planetDto), count = 1, next = null, previous = null
        )
        whenever(api.getPlanets(1)).thenReturn(response)

        val result = repository.getPlanets(1)

        assertTrue(result is ApiResult.Success)
        result as ApiResult.Success
        assertEquals(1, result.data.size)
        assertEquals("Tatooine", result.data[0].name)
    }

    @Test
    fun `getPlanets returns Error when HttpException is thrown`() = runTest {
        val httpException = mock<HttpException> {
            on { code() } doReturn 500
            on { message() } doReturn "Internal Server Error"
            on { response() } doReturn null
        }
        whenever(api.getPlanets(1)).thenThrow(httpException)

        val result = repository.getPlanets(1)

        assertTrue(result is ApiResult.Error)
        result as ApiResult.Error
        assertEquals(500, result.statusCode)
        assertEquals("Internal Server Error", result.message)
        assertEquals(httpException, result.exception)
    }

    @Test
    fun `getPlanets returns Error when unexpected Exception is thrown`() = runTest {
        val exception = IllegalStateException("Something went wrong")
        whenever(api.getPlanets(1)).thenThrow(exception)

        val result = repository.getPlanets(1)

        assertTrue(result is ApiResult.Error)
        result as ApiResult.Error
        assertEquals("Something went wrong", result.message)
        assertEquals(exception, result.exception)
    }

    @Test
    fun `getPlanetById returns Success when API call is successful`() = runTest {
        val planetDto = PlanetDto(
            "Alderaan",
            "364",
            "temperate",
            "1 standard",
            "https://swapi.dev/api/planets/2/"
        )
        whenever(api.getPlanetById(2)).thenReturn(planetDto)

        val result = repository.getPlanetById(2)

        assertTrue(result is ApiResult.Success)
        result as ApiResult.Success
        assertEquals("Alderaan", result.data.name)
    }

    @Test
    fun `getPlanetById returns Error when HttpException is thrown`() = runTest {
        val httpException = mock<HttpException> {
            on { code() } doReturn 404
            on { message() } doReturn "Not Found"
            on { response() } doReturn null
        }
        whenever(api.getPlanetById(2)).thenThrow(httpException)

        val result = repository.getPlanetById(2)

        assertTrue(result is ApiResult.Error)
        result as ApiResult.Error
        assertEquals(404, result.statusCode)
        assertEquals("Not Found", result.message)
        assertEquals(httpException, result.exception)
    }

    @Test
    fun `getPlanetById returns Error when unexpected Exception is thrown`() = runTest {
        val exception = RuntimeException("Random error")
        whenever(api.getPlanetById(2)).thenThrow(exception)

        val result = repository.getPlanetById(2)

        assertTrue(result is ApiResult.Error)
        result as ApiResult.Error
        assertEquals("Random error", result.message)
        assertEquals(exception, result.exception)
    }
}