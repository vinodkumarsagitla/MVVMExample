package com.vks.repository

import com.vks.api.retrofit.ApiErrorResponse
import com.vks.api.retrofit.ApiNetworkError
import com.vks.api.retrofit.ApiSuccessResponse
import com.vks.api.retrofit.ApiTimeoutError
import com.vks.repository.api.RemoteDataSourceProvider
import com.vks.repository.convertor.toPostResult
import com.vks.repository.model.PostsResult
import com.vks.repository.resource.NetworkError
import com.vks.repository.resource.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyRepository @Inject constructor(
    remoteDataSourceProvider: RemoteDataSourceProvider
) {
    private var apiService = remoteDataSourceProvider.apiService

    suspend fun getPosts(): Resource<PostsResult> {
        return when (val response = apiService.getPosts(0, 20)) {
            is ApiSuccessResponse ->  Resource.success(response.body.toPostResult())
            is ApiTimeoutError -> Resource.error(null, networkError = NetworkError.TIMEOUT)
            is ApiErrorResponse -> Resource.error(null, response.errorMessage)
            is ApiNetworkError -> Resource.error(null)
            else -> Resource.error(null)
        }
    }
}