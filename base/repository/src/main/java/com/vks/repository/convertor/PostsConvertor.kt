package com.vks.repository.convertor

import com.vks.api.dto.PostsDto
import com.vks.api.dto.PostsResultDto
import com.vks.api.dto.ReactionsDto
import com.vks.repository.model.Posts
import com.vks.repository.model.PostsResult
import com.vks.repository.model.Reactions

fun PostsResultDto.toPostResult() = PostsResult(posts.map { it.toPosts() })

fun PostsDto.toPosts() = Posts(id, title, body, tags, reactions?.toReaction(), views, userId)

fun ReactionsDto.toReaction() = Reactions(likes, dislikes)