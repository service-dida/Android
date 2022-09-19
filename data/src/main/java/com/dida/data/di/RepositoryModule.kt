package com.dida.data.di

import com.dida.data.api.KlaytnAPIService
import com.dida.data.api.MainAPIService
import com.dida.data.repository.KlaytnRepositoryImpl
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.usecase.KlaytnUsecase
import com.dida.domain.usecase.MainUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMainRepository(
        @Named("Main") mainAPIService: MainAPIService
    ): MainUsecase {
        return MainRepositoryImpl(mainAPIService)
    }

    @Provides
    @Singleton
    fun provideKlaytnRepository(
        @Named("Klaytn") klaytnAPIService: KlaytnAPIService
    ): KlaytnUsecase {
        return KlaytnRepositoryImpl(klaytnAPIService)
    }
}