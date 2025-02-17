package com.vks.api.retrofit

import com.vks.api.dto.PostsResultDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ApiServiceImpl : ApiService {
    @GET("/posts")
    override suspend fun getPosts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): ApiResponse<PostsResultDto>
}
