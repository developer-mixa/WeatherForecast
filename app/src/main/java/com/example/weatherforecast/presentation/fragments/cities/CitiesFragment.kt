package com.example.weatherforecast.presentation.fragments.cities

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.FragmentCitiesBinding
import com.example.weatherforecast.di.components.DaggerAppComponent
import com.example.weatherforecast.domain.entities.City
import com.example.weatherforecast.presentation.fragments.weather.WeatherCityFragment
import com.example.weatherforecast.presentation.models.ErrorContainer
import com.example.weatherforecast.presentation.models.PendingContainer
import com.example.weatherforecast.presentation.models.SuccessContainer
import com.example.weatherforecast.presentation.sticky.RecyclerSectionItemDecoration
import com.example.weatherforecast.utils.collectFlow
import com.example.weatherforecast.utils.launchFragment
import com.example.weatherforecast.utils.viewBinding


class CitiesFragment : Fragment(R.layout.fragment_cities), CitiesAdapter.OnCityTap {

    private val binding: FragmentCitiesBinding by viewBinding()

    private val adapter = CitiesAdapter(this)

    private lateinit var viewModel: CitiesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = DaggerAppComponent.create().factory
        viewModel = factory.create(CitiesViewModel::class.java)
        setupAdapter()
        setupOnClickListeners()
        renderCities()
    }

    private fun setupAdapter() = with(binding){
        val tryAgainAction: TryAgainAction = { viewModel.retry() }
        val footerAdapter = MenuLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)

        val sectionItemDecoration = RecyclerSectionItemDecoration(
            resources.getDimensionPixelSize(R.dimen.recycler_section_header_height),
            adapter.getSessionCallback())


        recyclerCities.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerCities.adapter = adapterWithLoadState
        recyclerCities.addItemDecoration(sectionItemDecoration)
    }

    private fun setupOnClickListeners() = with(binding){
        buttonRefresh.setOnClickListener {
            viewModel.retry()
        }
    }

    private fun renderCities() = collectFlow {
        viewModel.cities.collect { container ->
            with(binding){
                progressBar.isVisible = container is PendingContainer && recyclerCities.adapter?.itemCount == 0
                errorContainer.isVisible = container is ErrorContainer

                if (container is SuccessContainer)
                    adapter.submitData(container.value)

                if (container is ErrorContainer)
                    textError.text = getString(R.string.happen_mistake, container.error.message)
            }
        }
    }

    /**I think it's not critical for a small project.
    For large projects, I think it's better to make some kind of Navigator class that will be implemented in ViewModel.
    You can also make an Event in the ViewModel,
    and when it triggers, make a transition to a fragment, but here it seemed pointless to me
     **/
    override fun tap(city: City) {
        activity?.launchFragment(WeatherCityFragment.newInstance(city), addToBackStack = true)
    }
}