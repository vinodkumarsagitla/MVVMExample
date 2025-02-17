package com.vks.repository.di.modules

import com.vks.api.interceptors.HeaderFactory
import com.vks.repository.api.MyRemoteDataSourceProvider
import com.vks.repository.api.RemoteDataSourceProvider
import com.vks.repository.api.interceptors.AuthorizationHeaderFactory
import com.vks.repository.di.api.ApiServiceSubcomponent
import dagger.Binds
import dagger.Module

@Module(subcomponents = [ApiServiceSubcomponent::class])
internal abstract class NetworkModule {
    @Binds
    abstract fun provideHeaderFactory(headerFactory: AuthorizationHeaderFactory): HeaderFactory

    @Binds
    abstract fun provideRemoteDataSourceProvider(
        teRemoteDataSourceProvider: MyRemoteDataSourceProvider
    ): RemoteDataSourceProvider

}
