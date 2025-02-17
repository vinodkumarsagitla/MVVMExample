package com.vks.api.di

import com.vks.api.adapters.ApiResponseAdapterFactory
import com.vks.api.interceptors.GenericInterceptor
import com.vks.api.interceptors.HeaderFactory
import com.vks.api.retrofit.ApiService
import com.vks.api.retrofit.ApiServiceImpl
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ApiServiceFactory {
    val baseUrl = "https://dummyjson.com"

    fun createApiService(
        headerFactory: HeaderFactory,
        moshi: Moshi = Moshi.Builder().build(),
    ): ApiService {
        val genericInterceptor = GenericInterceptor(headerFactory)

        val httpClient = lazy {
            OkHttpClient.Builder().apply {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(loggingInterceptor)
                addInterceptor(genericInterceptor)
                connectTimeout(60, TimeUnit.SECONDS)
                readTimeout(60, TimeUnit.SECONDS)
            }.build()
        }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(ApiResponseAdapterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .callFactory { request ->
                // OkHttpClient will be created lazily on the first network call and not at startup
                httpClient.value.newCall(request)
            }
            .build()
            .create<ApiServiceImpl>()
    }
}
