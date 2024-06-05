package com.example.weatherforecast.di.modules

import com.example.weatherforecast.data.repositories.DefaultCitiesRepository
import com.example.weatherforecast.domain.CitiesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface CitiesRepositoryModule {
    @Binds
    @Singleton
    fun bindCitiesRepository(defaultCitiesRepository: DefaultCitiesRepository) : CitiesRepository
}