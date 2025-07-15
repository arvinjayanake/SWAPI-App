package com.arvin.swapi.domain.repository

import com.arvin.swapi.data.local.db.entity.PlanetEntity

/**
 * Repository interface for local database operations related to planets.
 *
 * Defines methods for retrieving, inserting, and clearing planet entities in the local database.
 */
interface DBRepository {

    /**
     * Retrieves a planet entity from the local database by its unique ID.
     *
     * @param id The unique identifier of the planet.
     * @return The [PlanetEntity] if found, or null if no planet with the given ID exists.
     */
    suspend fun getPlanetById(id: Int): PlanetEntity?

    /**
     * Retrieves a paginated list of planet entities from the local database.
     *
     * @param limit The maximum number of planets to return (default is 10).
     * @param offset The number of planets to skip before returning results.
     * @return A list of [PlanetEntity] objects.
     */
    suspend fun getPlanets(limit: Int = 10, offset: Int): List<PlanetEntity>

    /**
     * Inserts a list of planet entities into the local database.
     *
     * @param planets The list of [PlanetEntity] objects to insert.
     */
    suspend fun insertPlanets(planets: List<PlanetEntity>)

    /**
     * Deletes all planet entities from the local database.
     */
    suspend fun clearPlanets()

}