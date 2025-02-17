package com.vks.repository.api.interceptors

import android.util.Base64
import com.vks.api.interceptors.HeaderFactory
import javax.inject.Inject

class AuthorizationHeaderFactory @Inject constructor() : HeaderFactory {
    override fun getAuthHeaders(host: String): List<Pair<String, String>> {
        val credentials = "0013fadfa3694cb5b978b7bfb10a9ea3:3761dccf50444745a64f4c1c10de09bc"
        val base64Credentials = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        return listOf(Pair("Authorization", "Basic $base64Credentials"))
    }
}
