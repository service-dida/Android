package com.dida.data.di

import com.dida.data.BuildConfig
import com.dida.data.api.ApiClient.TEST_URL
import com.dida.data.interceptor.BearerInterceptor
import com.dida.data.main.DidaApi
import com.dida.data.interceptor.XAccessTokenInterceptor
import com.google.gson.GsonBuilder
import com.dida.data.interceptor.AccessTokenInterceptor
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
object NetworkModule {

    @Singleton
    @Provides
    @Named("Main")
    fun provideOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(AccessTokenInterceptor()) // JWT 자동 헤더 전송
            .addInterceptor(BearerInterceptor()) // Refresh Token
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    } else {
        OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(AccessTokenInterceptor()) // JWT 자동 헤더 전송
            .addInterceptor(BearerInterceptor()) // Refresh Token
            .build()
    }

    @Singleton
    @Provides
    @Named("Main")
    fun provideRetrofit(@Named("Main") okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(TEST_URL)
        .addConverterFactory(GsonConverterFactory.create(
            GsonBuilder()
            .disableHtmlEscaping()
            .create()))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @Named("Main")
    fun provideMainAPIService(@Named("Main") retrofit: Retrofit): DidaApi =
        retrofit.create(DidaApi::class.java)
}
