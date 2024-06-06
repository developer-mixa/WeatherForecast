package com.example.weatherforecast.domain.entities

import java.io.Serializable

data class City(
    val id: String,
    val name: String,
    val latitude: Float,
    val longitude: Float
) : Serializable
