package com.example.weatherforecast.data.sources

import com.squareup.moshi.Moshi
import retrofit2.Retrofit

data class RetrofitConfig(
    val retrofit: Retrofit,
    val moshi: Moshi
)
