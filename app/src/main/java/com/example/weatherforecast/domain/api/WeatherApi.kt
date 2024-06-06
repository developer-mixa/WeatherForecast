package com.example.weatherforecast.domain.api

import com.example.weatherforecast.config.WeatherApiConfig
import com.example.weatherforecast.domain.entities.TemperatureResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(WeatherApiConfig.GET_WEATHER_BY_CITY_URL)
    suspend fun getTemperatureByCity(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ) : TemperatureResponseBody
}