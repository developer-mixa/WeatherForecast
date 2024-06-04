package com.example.weatherforecast.data.mappers

import com.example.weatherforecast.domain.entities.CitiesResponseBody
import com.example.weatherforecast.domain.entities.City

object CitiesMapper {
    fun mapResponseToCities(citiesResponses: List<CitiesResponseBody>) : List<City>{
        return citiesResponses.map {
            City(it.city, it.latitude, it.longitude)
        }
    }
}