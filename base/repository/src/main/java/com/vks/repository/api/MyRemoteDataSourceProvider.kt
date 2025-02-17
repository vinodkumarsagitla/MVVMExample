package com.vks.repository.api

import com.vks.api.retrofit.ApiService
import com.vks.repository.di.api.ApiServiceSubcomponent
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Wrapper class for the ApiService class. We need these wrapper because in qa builds, testers
 * can log using different servers, so we need to change the ApiService instance at runtime.
 */
@Singleton
class MyRemoteDataSourceProvider @Inject constructor(
    private val apiServiceSubcomponentFactory: ApiServiceSubcomponent.Factory
) : RemoteDataSourceProvider {

    private var networkSubcomponent = apiServiceSubcomponentFactory.create()

    override val apiService: ApiService
        get() = networkSubcomponent.apiService

    /**
     * Creates the network component graph again, with a new possible, server URL.
     * Note: This method works for debug builds only where we can change remote server.
     */
    override fun createNewApiService(): ApiService {
        networkSubcomponent = apiServiceSubcomponentFactory.create()
        return apiService
    }
}
