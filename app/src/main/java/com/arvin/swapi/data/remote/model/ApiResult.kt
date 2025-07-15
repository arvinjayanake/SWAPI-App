package com.arvin.swapi.data.remote.model

/**
 * Represents the result of an API call, encapsulating either a successful response or an error.
 *
 * @param T The type of data expected in the successful result.
 */
sealed class ApiResult<out T> {

    /**
     * Indicates a successful API response.
     *
     * @param data The data returned from the API call.
     */
    data class Success<T>(val data: T) : ApiResult<T>()

    /**
     * Indicates an error occurred during the API call.
     *
     * @param exception The exception that was thrown.
     * @param message An optional error message providing more details.
     * @param statusCode An optional HTTP status code if available.
     */
    data class Error(
        val exception: Throwable,
        val message: String? = null,
        val statusCode: Int? = null
    ) : ApiResult<Nothing>()

}