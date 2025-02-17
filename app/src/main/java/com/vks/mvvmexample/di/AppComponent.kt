package com.vks.mvvmexample.di

import android.app.Activity
import android.app.Application
import com.vks.mvvmexample.MainActivity
import com.vks.mvvmexample.di.modules.AppModule
import com.vks.repository.di.RepositorySubcomponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class],
    dependencies = [RepositorySubcomponent::class]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance app: Application,
            repositorySubcomponent: RepositorySubcomponent
        ): AppComponent
    }
}

fun MainActivity.appComponent() = coreComponent() as AppComponent

fun Activity.coreComponent() = (applicationContext as CoreDependencyInjectionHolder).coreComponent

interface CoreComponent

interface CoreDependencyInjectionHolder {
    val coreComponent: AppComponent
}
