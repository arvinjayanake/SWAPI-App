package com.arvin.swapi.data.repository

import com.arvin.swapi.data.local.db.dao.PlanetDao
import com.arvin.swapi.data.local.db.entity.PlanetEntity
import com.arvin.swapi.domain.repository.DBRepository

/**
 * Implementation of [DBRepository] for handling local database operations related to planets.
 *
 * Delegates all CRUD operations to the provided [PlanetDao] instance.
 *
 * @property planetDao The DAO used to access planet data in the local database.
 */
class DBRepositoryImpl(private val planetDao: PlanetDao) : DBRepository {

    /**
     * Retrieves a planet entity from the local database by its ID.
     *
     * @param id The unique identifier of the planet.
     * @return The [PlanetEntity] if found, or null otherwise.
     */
    override suspend fun getPlanetById(id: Int): PlanetEntity? {
        return planetDao.getPlanetById(id)
    }

    /**
     * Retrieves a paginated list of planet entities from the local database.
     *
     * @param limit The maximum number of planets to return.
     * @param offset The number of planets to skip before returning results.
     * @return A list of [PlanetEntity] objects.
     */
    override suspend fun getPlanets(
        limit: Int,
        offset: Int
    ): List<PlanetEntity> {
        return planetDao.getPlanets(limit = limit, offset = offset)
    }

    /**
     * Inserts a list of planet entities into the local database.
     *
     * @param planets The list of [PlanetEntity] objects to insert.
     */
    override suspend fun insertPlanets(planets: List<PlanetEntity>) {
        return planetDao.insertPlanets(planets)
    }

    /**
     * Deletes all planet entities from the local database.
     */
    override suspend fun clearPlanets() {
        return planetDao.clearPlanets()
    }

}