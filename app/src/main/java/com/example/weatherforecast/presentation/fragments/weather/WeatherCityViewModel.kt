package com.example.weatherforecast.presentation.fragments.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.domain.repositories.WeatherRepository
import com.example.weatherforecast.presentation.models.Container
import com.example.weatherforecast.presentation.models.ErrorContainer
import com.example.weatherforecast.presentation.models.PendingContainer
import com.example.weatherforecast.presentation.models.SuccessContainer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherCityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _temperature = MutableStateFlow<Container<Double>>(PendingContainer())
    val temperature = _temperature.asStateFlow()

    fun getTemperature(latitude: Float, longitude: Float) = viewModelScope.launch{
        _temperature.value = PendingContainer()
        delay(200)
        try {
            val temperature = weatherRepository.getTemperatureByCity(latitude.toDouble(), longitude.toDouble())
            val roundTemperature = String.format("%.1f", temperature).toDouble()
            _temperature.value = SuccessContainer(roundTemperature)
        } catch (e: Exception){
            _temperature.value = ErrorContainer(e)
        }
    }

}