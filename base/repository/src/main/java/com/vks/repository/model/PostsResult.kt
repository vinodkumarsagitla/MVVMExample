package com.vks.repository.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostsResult(
    val posts: List<Posts>
)

@JsonClass(generateAdapter = true)
data class Posts(
    val id: Int?,
    val title: String?,
    val body: String?,
    val tags: List<String>?,
    val reactions: Reactions?,
    val views: String?,
    val userId: Int?
)

@JsonClass(generateAdapter = true)
data class Reactions(
    val likes: Int?,
    val dislikes: Int?
)