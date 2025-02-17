package com.vks.api.retrofit

import com.vks.api.dto.PostsResultDto

/**
 * REST API access points
 */
interface ApiService {
    suspend fun getPosts(skip: Int, limit: Int): ApiResponse<PostsResultDto>
}
