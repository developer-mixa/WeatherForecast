package com.example.weatherforecast.domain.api

import com.example.weatherforecast.config.CitiesApiConfig
import com.example.weatherforecast.domain.entities.CitiesResponseBody
import retrofit2.http.GET

interface CitiesApi {
    @GET(CitiesApiConfig.GET_CITIES_URL)
    suspend fun getCities() : List<CitiesResponseBody>
}