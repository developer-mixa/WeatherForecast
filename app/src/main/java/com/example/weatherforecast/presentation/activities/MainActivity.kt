package com.example.weatherforecast.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecast.presentation.navigation.MainNavigator
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.ActivityMainBinding
import com.example.weatherforecast.presentation.fragments.CitiesFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val navigator by viewModels<MainNavigator>{ ViewModelProvider.AndroidViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null){
            navigator.launchFragment(this, CitiesFragment.Screen(), idFragment = R.id.fragmentMainContainer)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        navigator.whenActivityActive.mainActivity = this
    }

    override fun onPause() {
        super.onPause()
        navigator.whenActivityActive.mainActivity = null
    }

}