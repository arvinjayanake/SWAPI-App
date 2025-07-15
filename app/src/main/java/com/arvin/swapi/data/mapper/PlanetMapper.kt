package com.arvin.swapi.data.mapper

import com.arvin.swapi.data.local.db.entity.PlanetEntity
import com.arvin.swapi.domain.model.Planet
import com.arvin.swapi.domain.model.getIdFromUrl

/**
 * Converts a list of [Planet] domain models to a list of [PlanetEntity] database entities.
 *
 * @return A list of [PlanetEntity] objects corresponding to each [Planet] in the original list.
 */
fun List<Planet>.toEntityList(): List<PlanetEntity> = map { it.toEntity() }


/**
 * Converts a [Planet] domain model to a [PlanetEntity] database entity.
 *
 * @return The [PlanetEntity] representation of this [Planet].
 */
fun Planet.toEntity(): PlanetEntity = PlanetEntity(
    id = this.getIdFromUrl(),
    name = this.name,
    orbitalPeriod = this.orbitalPeriod,
    climate = this.climate,
    gravity = this.gravity,
    url = this.url
)

/**
 * Converts a [PlanetEntity] database entity to a [Planet] domain model.
 *
 * @return The [Planet] domain model representation of this [PlanetEntity].
 */
fun PlanetEntity.toPlanet(): Planet = Planet(
    name = this.name,
    orbitalPeriod = this.orbitalPeriod,
    climate = this.climate,
    gravity = this.gravity,
    url = this.url
)