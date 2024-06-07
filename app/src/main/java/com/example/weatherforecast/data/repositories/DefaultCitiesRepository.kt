package com.example.weatherforecast.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.weatherforecast.data.sources.CitiesPageLoader
import com.example.weatherforecast.data.sources.CitiesPagingSource
import com.example.weatherforecast.data.sources.CitiesRetrofitSource
import com.example.weatherforecast.domain.repositories.CitiesRepository
import com.example.weatherforecast.domain.entities.City
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.min

@Singleton
class DefaultCitiesRepository @Inject constructor(
    private val citiesRetrofitSource: CitiesRetrofitSource
) : CitiesRepository {

    override fun getCities(): Flow<PagingData<City>> {

        val citiesPageLoader: CitiesPageLoader = {pageIndex, pageSize ->
            getCities(pageIndex, pageSize)
        }

        return Pager(
            config = PagingConfig(PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { CitiesPagingSource(citiesPageLoader, PAGE_SIZE) }
        ).flow
    }

    private suspend fun getCities(
        pageIndex: Int,
        pageSize: Int,
    ): List<City> {
        val cities: List<City> = citiesRetrofitSource.getCities()
            .sortedBy { it.name }
            .filter { it.name.isNotBlank() } // i think city can't be empty

        val offset = pageIndex * pageSize

        val limit = min(offset + pageSize, cities.size)
        return cities.slice(offset until limit)
    }

    private companion object{
        const val PAGE_SIZE = 30
    }

}