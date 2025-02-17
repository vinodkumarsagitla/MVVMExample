package com.vks.repository.api

import com.vks.api.retrofit.ApiService

/**
 * This class provides the retrofit service instance.
 * We need these wrapper because in qa builds, testers can log using
 * different servers, so we need to change the ApiService instance at runtime.
 */
interface RemoteDataSourceProvider {

    /**
     * Current ApiService
     */
    val apiService: ApiService


    /**
     * Creates the network component graph again, with a new possible, server URL.
     * Note: This method should works for debug builds only where we can change remote server.
     *
     * @return the just created [ApiService]
     */
    fun createNewApiService(): ApiService
}
