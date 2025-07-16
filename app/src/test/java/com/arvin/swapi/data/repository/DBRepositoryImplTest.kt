package com.arvin.swapi.data.repository

import com.arvin.swapi.data.local.db.dao.PlanetDao
import com.arvin.swapi.data.local.db.entity.PlanetEntity
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class DBRepositoryImplTest {

    private lateinit var planetDao: PlanetDao
    private lateinit var repository: DBRepositoryImpl

    @Before
    fun setUp() {
        planetDao = mock()
        repository = DBRepositoryImpl(planetDao)
    }

    @Test
    fun `getPlanetById delegates to DAO and returns result`() = runTest {
        val expected = PlanetEntity(
            id = 1,
            name = "Tatooine",
            orbitalPeriod = "23",
            climate = "arid",
            gravity = "1 standard",
            url = "https://swapi.dev/api/planets/1/"
        )
        whenever(planetDao.getPlanetById(1)).thenReturn(expected)

        val result = repository.getPlanetById(1)

        assertEquals(expected, result)
        verify(planetDao).getPlanetById(1)
    }

    @Test
    fun `getPlanets delegates to DAO and returns result`() = runTest {
        val expectedList = listOf(
            PlanetEntity(
                1,
                "Tatooine",
                "23",
                "arid",
                "1 standard",
                "https://swapi.dev/api/planets/1/"
            ),
            PlanetEntity(
                2,
                "Alderaan",
                "24",
                "temperate",
                "1 standard",
                "https://swapi.dev/api/planets/2/"
            )
        )
        whenever(planetDao.getPlanets(10, 0)).thenReturn(expectedList)

        val result = repository.getPlanets(10, 0)

        assertEquals(expectedList, result)
        verify(planetDao).getPlanets(10, 0)
    }

    @Test
    fun `insertPlanets delegates to DAO`() = runTest {
        val entities = listOf(
            PlanetEntity(
                1,
                "Tatooine",
                "23",
                "arid",
                "1 standard",
                "https://swapi.dev/api/planets/1/"
            ),
            PlanetEntity(
                2,
                "Alderaan",
                "24",
                "temperate",
                "1 standard",
                "https://swapi.dev/api/planets/2/"
            )
        )

        repository.insertPlanets(entities)

        verify(planetDao).insertPlanets(entities)
    }

    @Test
    fun `clearPlanets delegates to DAO`() = runTest {
        repository.clearPlanets()
        verify(planetDao).clearPlanets()
    }
}