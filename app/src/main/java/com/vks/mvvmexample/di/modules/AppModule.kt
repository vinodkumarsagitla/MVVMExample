package com.vks.mvvmexample.di.modules

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.Binds
import dagger.Module
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Module(includes = [FragmentModule::class, ViewModelModule::class])
abstract class AppModule {
    @Binds
    abstract fun provideFragmentFactory(
        myFragmentFactory: MyFragmentFactory
    ): FragmentFactory
}

@Singleton
class MyFragmentFactory @Inject constructor(
    private val providers: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return providers.mapKeys { (fragmentClass, _) ->
            fragmentClass.name
        }[className]?.get() ?: super.instantiate(classLoader, className)
    }
}
