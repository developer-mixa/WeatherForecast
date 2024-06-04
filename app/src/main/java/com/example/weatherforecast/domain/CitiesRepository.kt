package com.example.weatherforecast.domain

import androidx.paging.PagingData
import com.example.weatherforecast.domain.entities.City
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    fun getCities() : Flow<PagingData<City>>
}