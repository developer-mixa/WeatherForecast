package com.example.weatherforecast.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.navigation.BaseScreen
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.FragmentCitiesBinding
import com.example.weatherforecast.utils.viewBinding

class CitiesFragment : Fragment(R.layout.fragment_cities) {

    private val binding: FragmentCitiesBinding by viewBinding()

    class Screen: BaseScreen

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}