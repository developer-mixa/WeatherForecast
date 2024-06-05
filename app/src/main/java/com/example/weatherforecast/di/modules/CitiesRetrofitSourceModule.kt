package com.example.weatherforecast.di.modules

import com.example.weatherforecast.config.CitiesApiConfig
import com.example.weatherforecast.data.sources.CitiesRetrofitSource
import com.example.weatherforecast.data.sources.RetrofitConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class CitiesRetrofitSourceModule {

    @Provides
    @Singleton
    fun provideRetrofitSource(retrofitConfig: RetrofitConfig) : CitiesRetrofitSource{
        return CitiesRetrofitSource(retrofitConfig)
    }

    @Provides
    @Singleton
    fun provideRetrofitConfig(retrofit: Retrofit, moshi: Moshi) : RetrofitConfig{
        return RetrofitConfig(
            retrofit, moshi
        )
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(CitiesApiConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }
    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

}