package com.example.weatherforecast.data.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.weatherforecast.domain.entities.City

typealias CitiesPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<City>

class CitiesPagingSource(
    private val citiesPageLoader: CitiesPageLoader,
    private val citiesSize: Int
) : PagingSource<Int, City>() {
    override fun getRefreshKey(state: PagingState<Int, City>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, City> {
        val key = params.key ?: 0

        return try {
            val cities = citiesPageLoader(key, params.loadSize)
            return LoadResult.Page(
                data = cities,
                prevKey = if (key == 0) null else key - 1,
                nextKey = if (cities.size == params.loadSize) key + (params.loadSize / citiesSize) else null
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }

    }
}