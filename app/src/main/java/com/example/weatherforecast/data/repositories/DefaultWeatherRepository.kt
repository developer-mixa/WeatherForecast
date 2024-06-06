package com.example.weatherforecast.data.repositories

import com.example.weatherforecast.BuildConfig
import com.example.weatherforecast.data.sources.BaseRetrofitSource
import com.example.weatherforecast.data.sources.RetrofitConfig
import com.example.weatherforecast.di.utils.WeatherRetrofit
import com.example.weatherforecast.domain.api.WeatherApi
import com.example.weatherforecast.domain.repositories.WeatherRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultWeatherRepository @Inject constructor(
    @WeatherRetrofit private val retrofitConfig: RetrofitConfig
) : BaseRetrofitSource(retrofitConfig.moshi), WeatherRepository {

    private val weatherApi = retrofitConfig.retrofit.create(WeatherApi::class.java)

    override suspend fun getTemperatureByCity(latitude: Double, longitude: Double) : Double{
        val apiKey = BuildConfig.WEATHER_API_KEY
        val temperatureResponse = weatherApi.getTemperatureByCity(latitude, longitude, apiKey)
        val kelvinTemperature = temperatureResponse.main.temp
        return kelvinTemperature.toCelsius()
    }

    private fun Double.toCelsius() : Double{
        return this - KELVIN_CELSIUS_MORE
    }

    private companion object{
        const val KELVIN_CELSIUS_MORE = 273.15
    }

}