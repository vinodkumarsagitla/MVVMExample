package com.vks.api.adapters

import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.Moshi
import com.vks.api.retrofit.ApiEmptyResponse
import com.vks.api.retrofit.ApiErrorResponse
import com.vks.api.retrofit.ApiNetworkError
import com.vks.api.retrofit.ApiResponse
import com.vks.api.retrofit.ApiSuccessResponse
import com.vks.api.retrofit.ApiTimeoutError
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

internal class ApiResponseCall<T>(proxy: Call<T>) : CallDelegate<T, ApiResponse<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<ApiResponse<T>>) =
        proxy.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val apiResponse = create(response)
                callback.onResponse(this@ApiResponseCall, Response.success(apiResponse))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val result = when (t) {
                    is SocketTimeoutException -> ApiTimeoutError
                    is IOException -> ApiNetworkError
                    else -> ApiErrorResponse<T>(errorMessage = t.message ?: "unknown error")
                }
                callback.onResponse(this@ApiResponseCall, Response.success(result))
            }
        })

    override fun cloneImpl() = ApiResponseCall(proxy.clone())
    override fun timeout(): Timeout {
        return timeout()
    }

    private fun <T> create(response: Response<T>): ApiResponse<T> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null || response.code() == 204) {
                ApiEmptyResponse
            } else {
                ApiSuccessResponse(body)
            }
        } else {
            val msg = response.errorBody()?.string()
            val errorMsg = if (msg.isNullOrEmpty()) {
                response.message()
            } else {
                // We try to parse the errorBody as a JSON, returns the errorBody as String if
                // it fails
                try {
                    val apiError =
                        Moshi.Builder().build().adapter(ApiError::class.java).fromJson(msg)
                    var message = apiError?.message
                    if (message.isNullOrBlank())
                        message = apiError?.error_description
                    if (message.isNullOrBlank())
                        message = apiError?.detail
                    if (message.isNullOrBlank())
                        message = apiError?.Error
                    message
                } catch (e: JsonEncodingException) {
                    msg
                }
            } ?: "unknown error"
            ApiErrorResponse(errorMessage = errorMsg, response.code())
        }
    }
}

@JsonClass(generateAdapter = true)
internal data class ApiError(
    val message: String? = "",
    val error_description: String? = "",
    val detail: String? = "",
    val Error: String? = ""
)
