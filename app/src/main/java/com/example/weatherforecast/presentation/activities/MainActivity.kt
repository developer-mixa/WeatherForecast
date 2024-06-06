package com.example.weatherforecast.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherforecast.databinding.ActivityMainBinding
import com.example.weatherforecast.presentation.fragments.cities.CitiesFragment
import com.example.weatherforecast.utils.launchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null){
            launchFragment(CitiesFragment())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}