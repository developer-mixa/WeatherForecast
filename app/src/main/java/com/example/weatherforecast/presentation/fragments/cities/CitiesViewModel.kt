package com.example.weatherforecast.presentation.fragments.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.weatherforecast.domain.repositories.CitiesRepository
import com.example.weatherforecast.domain.entities.City
import com.example.weatherforecast.presentation.models.Container
import com.example.weatherforecast.presentation.models.ErrorContainer
import com.example.weatherforecast.presentation.models.PendingContainer
import com.example.weatherforecast.presentation.models.SuccessContainer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias ListCityContainer = Container<PagingData<City>>

class CitiesViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository
) : ViewModel() {

    private val _cities = MutableStateFlow<ListCityContainer>(PendingContainer())
    val cities = _cities.asStateFlow()

    init {
        getCities()
    }

    private fun getCities() = viewModelScope.launch{
        try {
            _cities.value = PendingContainer()
            delay(1000)
            citiesRepository.getCities().cachedIn(viewModelScope).collect{
                _cities.value = SuccessContainer(it)
            }
        }catch (e: Exception){
            _cities.value = ErrorContainer(e)
        }

    }

    fun retry() = getCities()

}