package com.example.weatherforecast


import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

class Test {}

private const val GET_CITIES = "https://gist.githubusercontent.com/Stronger197/764f9886a1e8392ddcae2521437d5a3b/raw/65164ea1af958c75c81a7f0221bead610590448e/cities.json"
data class CitiesResponseBody(
    val city: String,
    val latitude: Float,
    val longitude: Float
)
interface CitiesApi {
    @GET(GET_CITIES)
    suspend fun getCities() : List<CitiesResponseBody>

}

fun main() = runBlocking{

    val retrofit = Retrofit.Builder()
        .baseUrl("https://gist.githubusercontent.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val moshi = Moshi.Builder()
        .build()

    val citiesApi = retrofit.create(CitiesApi::class.java)

    println(citiesApi.getCities())

}