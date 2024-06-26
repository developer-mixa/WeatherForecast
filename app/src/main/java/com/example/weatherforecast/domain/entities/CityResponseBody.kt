package com.example.weatherforecast.domain.entities

data class CityResponseBody(
    val id: String,
    val city: String,
    val latitude: Float,
    val longitude: Float
){
    fun toCity(): City {
        return City(id, city, latitude, longitude)
    }
}