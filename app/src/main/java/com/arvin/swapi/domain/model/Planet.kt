package com.arvin.swapi.domain.model

data class Planet(

    val name: String?,
    val orbital_period: String?,
    val climate: String?,
    val gravity: String?,
    val url: String?

)

fun Planet.getHighResImgUrl(): String {
    return "https://picsum.photos/id/${getIdFromUrl()}/1000/1000"
}

fun Planet.getThumbnailImgUrl(): String {
    return "https://picsum.photos/id/${getIdFromUrl()}/300/300"
}

fun Planet.getIdFromUrl(): Int {
    return url?.trimEnd('/')
        ?.split('/')
        ?.lastOrNull()
        ?.toIntOrNull() ?: 0
}