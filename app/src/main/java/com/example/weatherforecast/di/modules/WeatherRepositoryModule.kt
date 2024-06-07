package com.example.weatherforecast.di.modules

import com.example.weatherforecast.data.repositories.DefaultWeatherRepository
import com.example.weatherforecast.domain.repositories.WeatherRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface WeatherRepositoryModule {
    @Binds
    @Singleton
    fun bindWeatherRepository(defaultWeatherRepository: DefaultWeatherRepository) : WeatherRepository
}