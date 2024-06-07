package com.example.weatherforecast.presentation.fragments.cities

import com.example.weatherforecast.base.ViewModelTest
import com.example.weatherforecast.domain.repositories.CitiesRepository
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class CitiesViewModelTest : ViewModelTest() {

    private lateinit var citiesRepository: CitiesRepository

    private lateinit var citiesViewModel: CitiesViewModel

    @Before
    fun setup() = runTest{
        citiesRepository = mockk(relaxed = true)
        citiesViewModel = CitiesViewModel(citiesRepository)
        advanceTimeBy(10000)
    }

    @Test
    fun testGetCitiesInInit() = runTest{
        // it calls in init
        verify(exactly = 1) { citiesRepository.getCities() }
    }

    @Test
    fun testRetry() = runTest{
        citiesViewModel.retry()
        advanceTimeBy(10000)
        verify(exactly = 2) { citiesRepository.getCities() }
    }

}