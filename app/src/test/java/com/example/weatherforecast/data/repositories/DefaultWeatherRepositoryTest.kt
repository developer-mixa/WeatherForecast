package com.example.weatherforecast.data.repositories

import com.example.weatherforecast.data.sources.RetrofitConfig
import com.example.weatherforecast.domain.api.WeatherApi
import com.example.weatherforecast.domain.entities.Main
import com.example.weatherforecast.domain.entities.TemperatureResponseBody
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultWeatherRepositoryTest {

    private val retrofitConfig: RetrofitConfig = mockk()
    private val weatherApi: WeatherApi = mockk()

    @Before
    fun setup(){
        every { retrofitConfig.retrofit.create(WeatherApi::class.java) } returns weatherApi
        every { retrofitConfig.moshi } returns mockk(relaxed = true)
    }

    @Test
    fun getTemperatureByCityTest(): Unit = runTest{
        // arrange
        val exceptedTemperature = 20.0
        val testTemperatureResponseBody = TemperatureResponseBody(getTestMain().copy(temp = exceptedTemperature + KELVIN_CELSIUS_MORE))
        val defaultWeatherRepository = DefaultWeatherRepository(retrofitConfig)
        coEvery { weatherApi.getTemperatureByCity(0.0, any(), any()) } returns testTemperatureResponseBody

        // act
        val moscowTemperature = defaultWeatherRepository.getTemperatureByCity(0.0, 0.0)

        // assert
        assertEquals(moscowTemperature, exceptedTemperature)
    }

    private fun getTestMain() : Main{
        return Main(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    }

    private companion object{
        const val KELVIN_CELSIUS_MORE = 273.15
    }
}