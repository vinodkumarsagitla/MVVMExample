package com.vks.api.retrofit

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
sealed class ApiResponse<out T>

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
object ApiEmptyResponse : ApiResponse<Nothing>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String, val code: Int = -1) : ApiResponse<T>()

object ApiTimeoutError : ApiResponse<Nothing>()

object ApiNetworkError : ApiResponse<Nothing>()
