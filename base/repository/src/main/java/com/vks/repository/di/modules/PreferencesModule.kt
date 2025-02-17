package com.vks.repository.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal object PreferencesModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE)
    }

    private const val PREF_FILE_KEY = "myAPP"
}
