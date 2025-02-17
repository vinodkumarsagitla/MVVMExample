package com.vks.mvvmexample

import android.app.Application
import com.vks.mvvmexample.di.AppComponent
import com.vks.mvvmexample.di.CoreDependencyInjectionHolder
import com.vks.mvvmexample.di.DaggerAppComponent
import com.vks.repository.di.RepositoryComponent

class MyApp : Application(), CoreDependencyInjectionHolder {

    override val coreComponent: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(app = this, repositorySubcomponent = RepositoryComponent.create(this))
    }
}