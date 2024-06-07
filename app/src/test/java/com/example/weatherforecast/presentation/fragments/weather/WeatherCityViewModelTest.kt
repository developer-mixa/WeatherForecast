package com.example.weatherforecast.presentation.fragments.weather

import com.example.weatherforecast.base.ViewModelTest
import com.example.weatherforecast.domain.repositories.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherCityViewModelTest : ViewModelTest() {

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var weatherCityViewModel: WeatherCityViewModel

    @Before
    fun setup(){
        weatherRepository = mockk(relaxed = true)
        weatherCityViewModel = WeatherCityViewModel(weatherRepository)
    }

    @Test
    fun testGetTemperatureCallWeatherRepository() = runTest{
        // arrange
        val testTemperature = 12.23f.toDouble()
        coEvery { weatherRepository.getTemperatureByCity(0.1f.toDouble(), 2f.toDouble()) } returns testTemperature

        // act
        weatherCityViewModel.getTemperature(0.1f, 2f)
        advanceTimeBy(10000)
        println(weatherCityViewModel.temperature.value)
        // assert
        coVerify(exactly = 1) { weatherRepository.getTemperatureByCity(0.1f.toDouble(), 2f.toDouble()) }
    }

}