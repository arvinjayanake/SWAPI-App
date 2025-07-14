package com.arvin.swapi.domain.model

data class Planet(

    val name: String?,
    val orbital_period: String?,
    val climate: String?,
    val gravity: String?,
    val url: String?

)

fun Planet.getImgUrl(): String {
    return "https://picsum.photos/id/${getIdFromUrl()}/200/200"
}

fun Planet.getIdFromUrl(): Int {
    return url?.trimEnd('/')
        ?.split('/')
        ?.lastOrNull()
        ?.toIntOrNull() ?: 0
}