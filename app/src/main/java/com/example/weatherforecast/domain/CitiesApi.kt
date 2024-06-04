package com.example.weatherforecast.domain

import com.example.weatherforecast.domain.entities.CitiesResponseBody
import retrofit2.http.GET

private const val GET_CITIES = "https://gist.githubusercontent.com/Stronger197/764f9886a1e8392ddcae2521437d5a3b/raw/65164ea1af958c75c81a7f0221bead610590448e/cities.json"

interface CitiesApi {
    @GET(GET_CITIES)
    suspend fun getCities() : List<CitiesResponseBody>

}