package com.vks.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response

/**
 * OkHttpClient Interceptor that adds needed headers to request to communicate
 * with TE servers correctly.
 */
internal class GenericInterceptor(
    private val authorizationHeaderFactory: HeaderFactory
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        requestBuilder.header("Accept", "application/json")

        val host = chain.request().url.host
        authorizationHeaderFactory.getAuthHeaders(host).forEach { header ->
            requestBuilder.header(header.first, header.second)
        }
        return chain.proceed(requestBuilder.build())
    }
}
