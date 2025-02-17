package com.vks.repository.di.api

import com.vks.api.retrofit.ApiService
import dagger.Subcomponent

@NetworkScope
@Subcomponent(modules = [ApiServiceModule::class])
interface ApiServiceSubcomponent {
    val apiService: ApiService

    @Subcomponent.Factory
    interface Factory {
        fun create(): ApiServiceSubcomponent
    }
}
