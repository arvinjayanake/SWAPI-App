package com.arvin.swapi.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Object responsible for configuring and providing the [SWApiService] Retrofit instance.
 *
 * Initializes Retrofit with the SWAPI base URL, a Gson converter, and an unsafe OkHttp client
 */
object RetrofitClient {

    /**
     * Lazily initialized instance of [SWApiService] for accessing the Star Wars API.
     *
     * Uses Retrofit to handle HTTP requests and responses.
     */
    val api: SWApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://swapi.dev/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getUnsafeOkHttpClient())
            .build()
            .create(SWApiService::class.java)
    }
}