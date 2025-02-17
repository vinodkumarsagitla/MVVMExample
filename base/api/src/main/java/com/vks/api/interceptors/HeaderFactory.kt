package com.vks.api.interceptors

interface HeaderFactory {
    fun getAuthHeaders(host: String): List<Pair<String, String>>
}
