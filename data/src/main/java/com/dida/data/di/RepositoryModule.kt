package com.dida.data.di

import com.dida.data.klaytn.KlaytnApi
import com.dida.data.main.DidaApi
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
        @Named("Main") didaApi: DidaApi
    ): MainRepository {
        return MainRepositoryImpl(didaApi)
    }

    @Provides
    @Singleton
    fun provideKlaytnRepository(
        @Named("Klaytn") klaytnApi: KlaytnApi
    ): KlaytnRepository {
        return KlaytnRepositoryImpl(klaytnApi)
    }
}
