package com.arvin.swapi.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a planet record in the local database.
 *
 * Stores details about a planet as retrieved from the Star Wars API (SWAPI).
 *
 * @property id Auto-generated unique identifier for the planet record.
 * @property name Name of the planet.
 * @property orbitalPeriod Orbital period of the planet (as a string).
 * @property climate Description of the planet's climate.
 * @property gravity Description of the planet's gravity.
 * @property url API endpoint URL for the planet.
 */
@Entity(tableName = "planets")
data class PlanetEntity(

    /** Auto-generated unique identifier for the planet record. */
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    /** Name of the planet. */
    @ColumnInfo(name = "name") val name: String?,

    /** Orbital period of the planet (as a string). */
    @ColumnInfo(name = "orbital_period") val orbitalPeriod: String?,

    /** Description of the planet's climate. */
    @ColumnInfo(name = "climate") val climate: String?,

    /** Description of the planet's gravity. */
    @ColumnInfo(name = "gravity") val gravity: String?,

    /** API endpoint URL for the planet. */
    @ColumnInfo(name = "url") val url: String?

)