package com.example.weatherforecast.data.sources

import com.example.weatherforecast.data.mappers.CitiesMapper
import com.example.weatherforecast.domain.CitiesApi
import com.example.weatherforecast.domain.CitiesSource
import com.example.weatherforecast.domain.entities.City
import com.squareup.moshi.Moshi

class CitiesRetrofitSource(
    private val retrofitConfig: RetrofitConfig
) : BaseRetrofitSource(retrofitConfig.moshi), CitiesSource {

    private val citiesApi = retrofitConfig.retrofit.create(CitiesApi::class.java)

    override suspend fun getCities(): List<City> = wrapRetrofitExceptions {
        CitiesMapper.mapResponseToCities(citiesApi.getCities())
    }
}