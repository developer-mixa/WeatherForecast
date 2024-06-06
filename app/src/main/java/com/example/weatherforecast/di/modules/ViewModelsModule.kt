package com.example.weatherforecast.di.modules

import androidx.lifecycle.ViewModel
import com.example.weatherforecast.di.utils.ViewModelKey
import com.example.weatherforecast.presentation.fragments.cities.CitiesViewModel
import com.example.weatherforecast.presentation.fragments.weather.WeatherCityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsModule {
    @Binds
    @[IntoMap ViewModelKey(CitiesViewModel::class)]
    fun provideCitiesViewModule(citiesViewModel: CitiesViewModel) : ViewModel

    @Binds
    @[IntoMap ViewModelKey(WeatherCityViewModel::class)]
    fun provideWeatherCityViewModule(weatherCityViewModel: WeatherCityViewModel) : ViewModel

}