package com.example.weatherforecast.domain.entities

data class CitiesResponseBody(
    val id: String,
    val city: String,
    val latitude: Float,
    val longitude: Float
)