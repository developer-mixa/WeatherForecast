package com.example.weatherforecast.data.sources

import com.example.weatherforecast.domain.api.CitiesApi
import com.example.weatherforecast.domain.source.CitiesSource
import com.example.weatherforecast.domain.entities.City
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CitiesRetrofitSource @Inject constructor(retrofitConfig: RetrofitConfig) : BaseRetrofitSource(retrofitConfig.moshi),
    CitiesSource {

    private val citiesApi = retrofitConfig.retrofit.create(CitiesApi::class.java)

    override suspend fun getCities(): List<City> = wrapRetrofitExceptions {
        citiesApi.getCities().map { it.toCity() }
    }
}