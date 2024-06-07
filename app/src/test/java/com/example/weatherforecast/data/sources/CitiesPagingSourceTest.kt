package com.example.weatherforecast.data.sources

import androidx.paging.PagingSource
import com.example.weatherforecast.domain.entities.City
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CitiesPagingSourceTest {

    private lateinit var citiesPageLoader: CitiesPageLoader
    private lateinit var citiesPagingSource: CitiesPagingSource

    @Before
    fun setUp() {
        citiesPageLoader = mockk()
        citiesPagingSource = CitiesPagingSource(citiesPageLoader, 10)
    }

    @Test
    fun loadReturnsSuccessResult() = runTest {
        // arrange
        val pageIndex = 0
        val pageSize = 20
        val expectedCities = listOf(
            City("1", "test", 0f, 0f),
            City("1", "test", 0f, 0f)
        )
        coEvery { citiesPageLoader(pageIndex, pageSize) } returns expectedCities

        // act
        val result = citiesPagingSource.load(PagingSource.LoadParams.Refresh(pageIndex, pageSize, false))

        // assert
        assertEquals(PagingSource.LoadResult.Page(expectedCities, null, null), result)
    }

    @Test
    fun loadReturnsError() = runTest {
        // arrange
        val pageIndex = 0
        val pageSize = 20
        val exception = RuntimeException("Test exception")
        coEvery { citiesPageLoader(pageIndex, pageSize) } throws exception

        // act
        val result = citiesPagingSource.load(PagingSource.LoadParams.Refresh(pageIndex, pageSize, false))

        // assert
        assertEquals(PagingSource.LoadResult.Error<Int, City>(exception), result)
    }
}

