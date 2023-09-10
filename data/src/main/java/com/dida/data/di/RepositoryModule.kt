package com.dida.data.di

import com.dida.data.klaytn.KlaytnAPIService
import com.dida.data.api.MainAPIService
import com.dida.data.klaytn.KlaytnRepositoryImpl
import com.dida.data.main.MainRepositoryImpl
import com.dida.domain.klaytn.KlaytnRepository
import com.dida.domain.main.MainRepository
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
