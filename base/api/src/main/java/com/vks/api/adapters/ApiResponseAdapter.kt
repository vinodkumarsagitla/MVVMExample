package com.vks.api.adapters

import com.vks.api.retrofit.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class ApiResponseAdapter(
    private val type: Type
) : CallAdapter<Type, Call<ApiResponse<Type>>> {

    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<ApiResponse<Type>> = ApiResponseCall(call)
}

internal class ApiResponseAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                ApiResponse::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    ApiResponseAdapter(resultType)
                }
                else -> null
            }
        }
        else -> null
    }
}
