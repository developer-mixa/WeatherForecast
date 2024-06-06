package com.example.weatherforecast.di.modules

import com.example.weatherforecast.config.CitiesApiConfig
import com.example.weatherforecast.config.WeatherApiConfig
import com.example.weatherforecast.data.sources.RetrofitConfig
import com.example.weatherforecast.di.utils.WeatherRetrofit
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
class RetrofitModule {
    @Provides
    @Singleton
    fun provideCitiesRetrofitConfig(retrofit: Retrofit, moshi: Moshi) : RetrofitConfig {
        return createRetrofitConfig(retrofit, moshi)
    }

    @Provides
    @Singleton
    @WeatherRetrofit
    fun provideWeatherRetrofitConfig(@WeatherRetrofit retrofit: Retrofit, moshi: Moshi) : RetrofitConfig {
        return createRetrofitConfig(retrofit, moshi)
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
    fun provideCitiesRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return createRetrofit(CitiesApiConfig.BASE_URL, client, moshi)
    }

    @Provides
    @Singleton
    @WeatherRetrofit
    fun provideWeatherRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return createRetrofit(WeatherApiConfig.BASE_URL, client, moshi)
    }

    private fun createRetrofitConfig(retrofit: Retrofit, moshi: Moshi) : RetrofitConfig{
        return RetrofitConfig(
            retrofit, moshi
        )
    }

    private fun createRetrofit(url: String, client: OkHttpClient, moshi: Moshi): Retrofit{
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}