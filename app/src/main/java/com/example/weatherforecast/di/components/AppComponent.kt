package com.example.weatherforecast.di.components

import com.example.weatherforecast.di.modules.CitiesRepositoryModule
import com.example.weatherforecast.di.modules.RetrofitModule
import com.example.weatherforecast.di.modules.ViewModelsModule
import com.example.weatherforecast.di.utils.MultiViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    CitiesRepositoryModule::class, RetrofitModule::class, ViewModelsModule::class
])
interface AppComponent {
    val factory: MultiViewModelFactory
}