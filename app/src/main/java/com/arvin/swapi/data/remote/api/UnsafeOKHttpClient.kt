package com.arvin.swapi.data.remote.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Creates and returns an [OkHttpClient] instance that bypasses SSL certificate validation.
 *
 * This client trusts all SSL certificates and hostnames, making it useful for development or testing
 * against endpoints with expired, invalid, or self-signed SSL certificates.
 *
 * **WARNING:** This approach is insecure and should never be used in production as it exposes
 * the application to man-in-the-middle (MITM) attacks and other security risks.
 *
 * @return An [OkHttpClient] that skips all SSL validation and logs HTTP request/response bodies.
 * @throws RuntimeException if the SSL context cannot be initialized.
 */
fun getUnsafeOkHttpClient(): OkHttpClient {
    try {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        val sslSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier(HostnameVerifier { _, _ -> true })
            .addInterceptor(loggingInterceptor)
            .build()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}