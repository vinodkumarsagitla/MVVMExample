package com.vks.repository.di

import com.vks.repository.MyRepository
import dagger.Subcomponent

@Subcomponent
interface RepositorySubcomponent {
    val myRepository: MyRepository
}
