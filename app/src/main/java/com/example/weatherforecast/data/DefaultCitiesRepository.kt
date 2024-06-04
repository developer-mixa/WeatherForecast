package com.example.weatherforecast.data

import androidx.paging.PagingData
import com.example.weatherforecast.domain.CitiesRepository
import com.example.weatherforecast.domain.entities.City
import kotlinx.coroutines.flow.Flow

class DefaultCitiesRepository : CitiesRepository {
    override fun getCities(): Flow<PagingData<City>> {
        return TODO()
    }
}