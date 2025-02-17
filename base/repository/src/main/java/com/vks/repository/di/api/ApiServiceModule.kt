package com.vks.repository.di.api

import com.vks.api.di.ApiServiceFactory
import com.vks.api.interceptors.HeaderFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides

@Module
object ApiServiceModule {
    @NetworkScope
    @Provides
    fun provideApiService(
        headerFactory: HeaderFactory,
        moshi: Moshi,
    ) = ApiServiceFactory.createApiService(
        headerFactory,
        moshi
    )
}
