package com.example.weatherforecast.domain.source

import com.example.weatherforecast.domain.entities.City

interface CitiesSource {
    suspend fun getCities() : List<City>
}