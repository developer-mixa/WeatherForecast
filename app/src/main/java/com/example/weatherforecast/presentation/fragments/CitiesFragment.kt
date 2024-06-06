package com.example.weatherforecast.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigation.BaseScreen
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.FragmentCitiesBinding
import com.example.weatherforecast.di.components.DaggerAppComponent
import com.example.weatherforecast.domain.entities.City
import com.example.weatherforecast.presentation.models.ErrorContainer
import com.example.weatherforecast.presentation.models.PendingContainer
import com.example.weatherforecast.presentation.models.SuccessContainer
import com.example.weatherforecast.presentation.sticky.RecyclerSectionItemDecoration
import com.example.weatherforecast.utils.collectFlow
import com.example.weatherforecast.utils.viewBinding


class CitiesFragment : Fragment(R.layout.fragment_cities) {

    private val binding: FragmentCitiesBinding by viewBinding()

    private val adapter = CitiesAdapter()

    private lateinit var viewModel: CitiesViewModel

    class Screen : BaseScreen

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
                progressBar.isVisible = container is PendingContainer
                errorContainer.isVisible = container is ErrorContainer

                if (container is SuccessContainer)
                    adapter.submitData(container.value)

                if (container is ErrorContainer)
                    textError.text = getString(R.string.happen_mistake, container.error.message)
            }
        }
    }
}