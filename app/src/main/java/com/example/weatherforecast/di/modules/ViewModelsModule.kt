package com.example.weatherforecast.di.modules

import androidx.lifecycle.ViewModel
import com.example.weatherforecast.di.utils.ViewModelKey
import com.example.weatherforecast.presentation.fragments.CitiesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsModule {
    @Binds
    @[IntoMap ViewModelKey(CitiesViewModel::class)]
    fun provideMainViewModule(mainViewModel: CitiesViewModel) : ViewModel
}