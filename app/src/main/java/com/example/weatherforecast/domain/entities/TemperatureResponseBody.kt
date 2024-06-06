package com.example.weatherforecast.domain.entities

data class TemperatureResponseBody(
    val main: Main
)

data class Main(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Double,
    val humidity: Double
)