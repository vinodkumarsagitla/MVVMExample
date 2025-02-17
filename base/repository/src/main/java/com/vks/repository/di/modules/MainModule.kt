package com.vks.repository.di.modules

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import dagger.Module
import dagger.Provides
import java.net.URL
import javax.inject.Singleton

@Module
internal abstract class MainModule {
    companion object {
        @Singleton
        @Provides
        fun provideMoshi(): Moshi = Moshi.Builder().add(UrlAdapter()).build()
    }
}

class UrlAdapter {
    @ToJson
    fun toJson(url: URL?) = url.toString()

    @FromJson
    fun fromJson(url: String?): URL? = url?.let { URL(it) }
}
