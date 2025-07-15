package com.arvin.swapi.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arvin.swapi.data.local.db.entity.PlanetEntity

/**
 * Data Access Object (DAO) for performing database operations on the PlanetEntity table.
 *
 * Provides methods to fetch, insert, and delete planet records in the local database.
 */
@Dao
interface PlanetDao {

    /**
     * Retrieves a planet by its unique ID.
     *
     * @param id The unique identifier of the planet.
     * @return The [PlanetEntity] if found, or null if no planet with the given ID exists.
     */
    @Query("SELECT * FROM planets WHERE id = :id LIMIT 1")
    suspend fun getPlanetById(id: Int): PlanetEntity?

    /**
     * Fetches a list of planets, ordered by ID in ascending order, with pagination support.
     *
     * @param limit The maximum number of planets to return (default is 10).
     * @param offset The number of planets to skip before starting to return results.
     * @return A list of [PlanetEntity] objects.
     */
    @Query("SELECT * FROM planets ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getPlanets(limit: Int = 10, offset: Int): List<PlanetEntity>

    /**
     * Inserts a list of planets into the database.
     *
     * If a planet already exists (conflict), the insertion for that planet is ignored.
     *
     * @param planets The list of [PlanetEntity] objects to insert.
     */
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertPlanets(planets: List<PlanetEntity>)

    /**
     * Deletes all planet records from the database.
     */
    @Query("DELETE FROM planets")
    suspend fun clearPlanets()

}