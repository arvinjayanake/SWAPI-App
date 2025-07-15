package com.arvin.swapi.data.local.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arvin.swapi.data.local.db.dao.PlanetDao
import com.arvin.swapi.data.local.db.entity.PlanetEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlanetDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var planetDao: PlanetDao

    val planet1 =  PlanetEntity(
        id = 1,
        name = "Tatooine",
        orbitalPeriod = "23",
        climate = "arid",
        gravity = "1 standard",
        url = "https://swapi.dev/api/planets/1/"
    )

    val planet2 = PlanetEntity(
        id = 2,
        name = "Alderaan",
        orbitalPeriod = "24",
        climate = "temperate",
        gravity = "1 standard",
        url = "https://swapi.dev/api/planets/2/"
    )

    val planetList = listOf( planet1, planet2)


    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        planetDao = database.planetDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndGetPlanet_success() = runBlocking {
        planetDao.insertPlanets(planetList)
        val planesRes = planetDao.getPlanets(limit = 2, offset = 0)
        assertTrue(planesRes.any { it.name == "Tatooine" })
    }

    @Test
    fun deleteAllPlanets_clearsTable() = runBlocking {
        planetDao.insertPlanets(planetList)
        planetDao.clearPlanets()
        val result = planetDao.getPlanets(limit = 10, offset = 0)
        assertTrue(result.isEmpty())
    }

    @Test
    fun getPlanets_paginationWorks() = runBlocking {
        val planets = listOf(
            PlanetEntity(1, "A", "1", "c1", "g1", "u1"),
            PlanetEntity(2, "B", "2", "c2", "g2", "u2"),
            PlanetEntity(3, "C", "3", "c3", "g3", "u3")
        )
        planetDao.insertPlanets(planets)
        val page1 = planetDao.getPlanets(limit = 2, offset = 0)
        val page2 = planetDao.getPlanets(limit = 2, offset = 2)
        assertTrue(page1.size == 2)
        assertTrue(page2.size == 1)
        assertTrue(page2.first().name == "C")
    }

    @Test
    fun insertDuplicateId_IgnoreSecond() = runBlocking {
        val planet = PlanetEntity(1, "A", "1", "c1", "g1", "u1")
        planetDao.insertPlanets(listOf(planet))

        val duplicate = planet.copy(name = "A updated")
        planetDao.insertPlanets(listOf(duplicate))

        val result = planetDao.getPlanets(limit = 1, offset = 0)

        assertTrue(result.first().name == "A")
    }


}