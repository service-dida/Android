package com.dida.data.di

import com.dida.data.BuildConfig
import com.dida.data.api.ApiClient.KLAYTN_URL
import com.dida.data.api.KlaytnAPIService
import com.dida.data.api.MainAPIService
import com.dida.data.interceptor.KlaytnAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KlaytnNetworkModule {

    @Singleton
    @Provides
    @Named("Klaytn")
    fun provideKlaytnOkHttpClient() = if(BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .callTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(KlaytnAuthInterceptor())
            .build()
    }else{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .callTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(KlaytnAuthInterceptor())
            .build()
    }


    @Singleton
    @Provides
    @Named("Klaytn")
    fun provideKlaytnRetrofit(@Named("Klaytn") okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(KLAYTN_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @Named("Klaytn")
    fun provideKlaytnAPIService(@Named("Klaytn") retrofit: Retrofit) : KlaytnAPIService =
        retrofit.create(KlaytnAPIService::class.java)


}