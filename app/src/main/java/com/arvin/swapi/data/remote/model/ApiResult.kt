package com.arvin.swapi.data.remote.model

sealed class ApiResult<out T> {

    data class Success<T>(val data: T) : ApiResult<T>()

    data class Error(
        val exception: Throwable,
        val message: String? = null,
        val statusCode: Int? = null
    ) : ApiResult<Nothing>()

}