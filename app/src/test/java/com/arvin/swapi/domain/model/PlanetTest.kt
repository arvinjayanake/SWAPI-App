package com.arvin.swapi.domain.model

import org.junit.Test
import kotlin.test.assertEquals

class PlanetTest {

    @Test
    fun `getIdFromUrl extracts correct id from well-formed url`() {
        val planet = Planet(
            name = "Test",
            orbitalPeriod = "100",
            climate = "arid",
            gravity = "1 standard",
            url = "https://swapi.dev/api/planets/42/"
        )
        assertEquals(42, planet.getIdFromUrl())
    }

    @Test
    fun `getIdFromUrl returns 0 if url is null`() {
        val planet = Planet(
            name = "Test",
            orbitalPeriod = "100",
            climate = "arid",
            gravity = "1 standard",
            url = null
        )
        assertEquals(0, planet.getIdFromUrl())
    }

    @Test
    fun `getIdFromUrl returns 0 if url is empty`() {
        val planet = Planet(
            name = "Test",
            orbitalPeriod = "100",
            climate = "arid",
            gravity = "1 standard",
            url = ""
        )
        assertEquals(0, planet.getIdFromUrl())
    }

    @Test
    fun `getIdFromUrl returns 0 if url is malformed`() {
        val planet = Planet(
            name = "Test",
            orbitalPeriod = "100",
            climate = "arid",
            gravity = "1 standard",
            url = "not-a-number"
        )
        assertEquals(0, planet.getIdFromUrl())
    }

    @Test
    fun `getHighResImgUrl returns correct url for valid planet url`() {
        val planet = Planet(
            name = "Test",
            orbitalPeriod = "100",
            climate = "arid",
            gravity = "1 standard",
            url = "https://swapi.dev/api/planets/5/"
        )
        assertEquals("https://picsum.photos/id/5/1000/1000", planet.getHighResImgUrl())
    }

    @Test
    fun `getHighResImgUrl returns url with id 0 for invalid planet url`() {
        val planet = Planet(
            name = "Test",
            orbitalPeriod = "100",
            climate = "arid",
            gravity = "1 standard",
            url = null
        )
        assertEquals("https://picsum.photos/id/0/1000/1000", planet.getHighResImgUrl())
    }

    @Test
    fun `getThumbnailImgUrl returns correct url for valid planet url`() {
        val planet = Planet(
            name = "Test",
            orbitalPeriod = "100",
            climate = "arid",
            gravity = "1 standard",
            url = "https://swapi.dev/api/planets/8/"
        )
        assertEquals("https://picsum.photos/id/8/300/300", planet.getThumbnailImgUrl())
    }

    @Test
    fun `getThumbnailImgUrl returns url with id 0 for invalid planet url`() {
        val planet = Planet(
            name = "Test",
            orbitalPeriod = "100",
            climate = "arid",
            gravity = "1 standard",
            url = ""
        )
        assertEquals("https://picsum.photos/id/0/300/300", planet.getThumbnailImgUrl())
    }

    @Test
    fun `getIdFromUrl handles url with multiple trailing slashes`() {
        val planet = Planet(
            name = "Test",
            orbitalPeriod = "100",
            climate = "arid",
            gravity = "1 standard",
            url = "https://swapi.dev/api/planets/9///"
        )
        assertEquals(9, planet.getIdFromUrl())
    }

    @Test
    fun `getIdFromUrl trims whitespace and parses id`() {
        val planet = Planet(
            name = "Test",
            orbitalPeriod = "100",
            climate = "arid",
            gravity = "1 standard",
            url = " https://swapi.dev/api/planets/10/ "
        )
        assertEquals(10, planet.copy(url = planet.url?.trim()).getIdFromUrl())
    }
}