package com.vks.api.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostsResultDto(
    val posts: List<PostsDto>
)

@JsonClass(generateAdapter = true)
data class PostsDto(
    val id: Int?,
    val title: String?,
    val body: String?,
    val tags: List<String>?,
    val reactions: ReactionsDto?,
    val views: String?,
    val userId: Int?
)

@JsonClass(generateAdapter = true)
data class ReactionsDto(
    val likes: Int?,
    val dislikes: Int?
)