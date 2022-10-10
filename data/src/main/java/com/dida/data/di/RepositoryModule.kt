package com.dida.data.di

import com.dida.data.api.KlaytnAPIService
import com.dida.data.api.MainAPIService
import com.dida.data.interceptor.BearerInterceptor
import com.dida.data.repository.KlaytnRepositoryImpl
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.repository.KlaytnRepository
import com.dida.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMainRepository(
        @Named("Main") mainAPIService: MainAPIService
    ): MainRepository {
        return MainRepositoryImpl(mainAPIService)
    }

    @Provides
    @Singleton
    fun provideKlaytnRepository(
        @Named("Klaytn") klaytnAPIService: KlaytnAPIService
    ): KlaytnRepository {
        return KlaytnRepositoryImpl(klaytnAPIService)
    }
}