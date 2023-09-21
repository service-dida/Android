package com.dida.data.di

import com.dida.data.main.LocalRepositoryImpl
import com.dida.domain.main.LocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class LocalModule {

    @Binds
    @Singleton
    abstract fun provideLocalRepository(
        repositoryImpl: LocalRepositoryImpl
    ): LocalRepository
}
