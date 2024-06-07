package com.example.weatherforecast.data.sources

import com.example.weatherforecast.domain.api.CitiesApi
import com.example.weatherforecast.domain.entities.City
import com.example.weatherforecast.domain.entities.CityResponseBody
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CitiesRetrofitSourceTest {
    private val retrofitConfig: RetrofitConfig = mockk()
    private val citiesApi: CitiesApi = mockk()

    @Before
    fun setup(){
        every { retrofitConfig.retrofit.create(CitiesApi::class.java) } returns citiesApi
        every { retrofitConfig.moshi } returns mockk(relaxed = true)
    }

    @Test
    fun getCitiesTest(): Unit = runTest{
        // arrange
        val citiesRetrofitSource = CitiesRetrofitSource(retrofitConfig)
        val citiesResponses = listOf(
            CityResponseBody("1", "test1", 0f, 0f),
            CityResponseBody("2", "test2", 0f, 0f),
        )

        // act
        coEvery { citiesApi.getCities() } returns citiesResponses

        // assert
        assertEquals(citiesRetrofitSource.getCities(), citiesResponses.map { it.toCity() })
    }

    @Test
    fun getCitiesWrappedRetrofitExceptionsTest() = runTest{
        // arrange

        val citiesResponses = listOf(
            CityResponseBody("1", "test1", 0f, 0f),
            CityResponseBody("2", "test2", 0f, 0f),
        )
        val citiesRetrofitSource = CitiesRetrofitSource(retrofitConfig)
        val source = spyk(citiesRetrofitSource)
        coEvery { citiesApi.getCities() } returns citiesResponses
        val slot: CapturingSlot<suspend () -> List<City>> = slot()
        coEvery { source.wrapRetrofitExceptions(capture(slot)) } returns emptyList()

        // act
        source.getCities()
        val cities = slot.captured.invoke()

        // assert
        assertEquals(citiesResponses.map { it.toCity() }, cities)
    }

}