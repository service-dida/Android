package com.dida.data.di

import com.dida.data.api.MainAPIService
import com.dida.data.repository.MainRepository
import com.dida.domain.usecase.MainUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMainRepository(
        mainAPIService: MainAPIService
    ): MainUsecase {
        return MainRepository(mainAPIService)
    }
}