package com.arvin.swapi.data.mapper

import com.arvin.swapi.data.local.db.entity.PlanetEntity
import com.arvin.swapi.data.remote.model.PlanetDto
import com.arvin.swapi.data.remote.model.toDomain
import com.arvin.swapi.domain.model.Planet
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MappingExtensionsTest {

    @Test
    fun `PlanetDto toDomain maps all fields correctly`() {
        val dto = PlanetDto(
            name = "Tatooine",
            orbitalPeriod = "304",
            climate = "arid",
            gravity = "1 standard",
            url = "https://swapi.dev/api/planets/1/"
        )
        val planet = dto.toDomain()

        assertEquals("Tatooine", planet.name)
        assertEquals("304", planet.orbitalPeriod)
        assertEquals("arid", planet.climate)
        assertEquals("1 standard", planet.gravity)
        assertEquals("https://swapi.dev/api/planets/1/", planet.url)
    }

    @Test
    fun `PlanetEntity toPlanet maps all fields correctly`() {
        val entity = PlanetEntity(
            id = 1,
            name = "Alderaan",
            orbitalPeriod = "364",
            climate = "temperate",
            gravity = "1 standard",
            url = "https://swapi.dev/api/planets/2/"
        )
        val planet = entity.toPlanet()

        assertEquals("Alderaan", planet.name)
        assertEquals("364", planet.orbitalPeriod)
        assertEquals("temperate", planet.climate)
        assertEquals("1 standard", planet.gravity)
        assertEquals("https://swapi.dev/api/planets/2/", planet.url)
    }

    @Test
    fun `Planet toEntity maps all fields correctly`() {
        val planet = Planet(
            name = "Yavin IV",
            orbitalPeriod = "4818",
            climate = "temperate, tropical",
            gravity = "1 standard",
            url = "https://swapi.dev/api/planets/3/"
        )
        val entity = planet.toEntity()

        assertEquals(3, entity.id)
        assertEquals("Yavin IV", entity.name)
        assertEquals("4818", entity.orbitalPeriod)
        assertEquals("temperate, tropical", entity.climate)
        assertEquals("1 standard", entity.gravity)
        assertEquals("https://swapi.dev/api/planets/3/", entity.url)
    }

    @Test
    fun `List Planet toEntityList maps each item`() {
        val planetList = listOf(
            Planet(name = "Dagobah", orbitalPeriod = "341", climate = "murky", gravity = "N/A", url = "https://swapi.dev/api/planets/5/")
        )
        val entities = planetList.toEntityList()

        assertEquals(1, entities.size)
        assertEquals("Dagobah", entities[0].name)
        assertEquals(5, entities[0].id)
    }

    @Test
    fun `PlanetDto toDomain handles null fields`() {
        val dto = PlanetDto(
            name = null,
            orbitalPeriod = null,
            climate = null,
            gravity = null,
            url = null
        )
        val planet = dto.toDomain()

        assertNull(planet.name)
        assertNull(planet.orbitalPeriod)
        assertNull(planet.climate)
        assertNull(planet.gravity)
        assertNull(planet.url)
    }

    @Test
    fun `Planet toEntity handles null url`() {
        val planet = Planet(
            name = "Unknown",
            orbitalPeriod = "0",
            climate = "unknown",
            gravity = "unknown",
            url = null
        )
        val entity = planet.toEntity()
        assertEquals(0, entity.id)
    }

    @Test
    fun `Planet toEntity handles malformed url`() {
        val planet = Planet(
            name = "Unknown",
            orbitalPeriod = "0",
            climate = "unknown",
            gravity = "unknown",
            url = "invalid-url"
        )
        val entity = planet.toEntity()
        assertEquals(0, entity.id)
    }

    @Test
    fun `toEntityList returns empty list for empty input`() {
        val emptyList = emptyList<Planet>()
        val entities = emptyList.toEntityList()
        assertTrue(entities.isEmpty())
    }
}