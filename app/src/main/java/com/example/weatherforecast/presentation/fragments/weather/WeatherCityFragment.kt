package com.example.weatherforecast.presentation.fragments.weather

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.FragmentWeatherCityBinding
import com.example.weatherforecast.di.components.DaggerAppComponent
import com.example.weatherforecast.domain.entities.City
import com.example.weatherforecast.presentation.models.ErrorContainer
import com.example.weatherforecast.presentation.models.PendingContainer
import com.example.weatherforecast.presentation.models.SuccessContainer
import com.example.weatherforecast.utils.collectFlow
import com.example.weatherforecast.utils.viewBinding
import kotlinx.coroutines.flow.collect

class WeatherCityFragment : Fragment(R.layout.fragment_weather_city) {

    private val binding by viewBinding<FragmentWeatherCityBinding>()

    private lateinit var viewModel: WeatherCityViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = DaggerAppComponent.create().factory
        viewModel = factory.create(WeatherCityViewModel::class.java)

        val city = getCity() ?: return

        renderCity(city.name)
        renderTemperature(city.latitude, city.longitude)
    }

    private fun getCity() : City?{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(GET_CITY_PARAM, City::class.java)
        }else{
            arguments?.getSerializable(GET_CITY_PARAM) as City
        }
    }

    private fun renderCity(cityName: String) = with(binding){
        textCityName.text = cityName
    }

    private fun renderTemperature(latitude: Float, longitude: Float) = collectFlow{
        viewModel.getTemperature(latitude, longitude)

        binding.buttonUpdate.setOnClickListener {
            viewModel.getTemperature(latitude, longitude)
        }

        viewModel.temperature.collect{ result ->
            with(binding){
                progressBar.isVisible = result is PendingContainer
                textTemperature.isVisible = result is SuccessContainer
                textError.isVisible = result is ErrorContainer

                if (result is SuccessContainer){
                    textTemperature.text = "${result.value}" + "\u00B0C"
                }

            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(city: City) =
            WeatherCityFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(GET_CITY_PARAM, city)
                }
            }

        private const val GET_CITY_PARAM = "get_city_param"
    }

}
