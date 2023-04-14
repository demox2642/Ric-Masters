package com.example.ricmasters.di // ktlint-disable package-name

import com.example.data.repository.*
import com.example.data.servises.CamerasApi
import com.example.data.servises.DoorsApi
import com.example.domain.repository.*
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideUrl(): String {
        return "http://cars.cprogroup.ru/"
    }

    @Provides
    @Named("logger")
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            .apply {
                level =  HttpLoggingInterceptor.Level.HEADERS

            }
    }



    @Provides
    fun provideOkHttp(
        @Named("logger") loggingInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(baseUrl: String, client: OkHttpClient): Retrofit.Builder {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideHomeService(retrofit: Retrofit.Builder): DoorsApi {
        return retrofit.build()
            .create(DoorsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDirectoryApi(retrofit: Retrofit.Builder): CamerasApi {
        return retrofit.build()
            .create(CamerasApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDoorsRepository(doorsApi: DoorsApi): DoorsRepository {
        return DoorsRepositoryImpl(doorsApi)
    }

    @Singleton
    @Provides
    fun provideCamerasRepository(camerasApi: CamerasApi): CamerasRepository {
        return CamerasRepositoryImpl(camerasApi)
    }

}
