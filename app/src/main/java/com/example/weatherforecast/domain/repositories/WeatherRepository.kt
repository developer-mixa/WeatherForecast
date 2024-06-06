package com.example.weatherforecast.domain.repositories

interface WeatherRepository {
    suspend fun getTemperatureByCity(latitude: Double, longitude: Double) : Double
}