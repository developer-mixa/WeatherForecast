package com.example.weatherforecast.presentation.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.CityItemBinding
import com.example.weatherforecast.domain.entities.City
import com.example.weatherforecast.presentation.sticky.RecyclerSectionItemDecoration

class CitiesAdapter : PagingDataAdapter<City, CitiesAdapter.ViewHolder>(CitiesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.city_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    fun tryGetItem(position: Int) : City?{
        return try {
            getItem(position)
        } catch (e: Exception){
            null
        }
    }

    fun getSessionCallback() = object : RecyclerSectionItemDecoration.SectionCallback {
        override fun isSection(position: Int): Boolean {
            if (position == 0) return true

            val currentItem = tryGetItem(position) ?: return false
            val prevItem = tryGetItem(position - 1) ?: return false

            return currentItem.name[0] != prevItem.name[0]
        }

        override fun getSectionHeader(position: Int): CharSequence {
            val currentItem = tryGetItem(position) ?: return ""
            return currentItem.name[0].toString()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CityItemBinding.bind(view)
        fun bind(item: City) = with(binding){
            textCityName.text = item.name
            tapperCityItem.setOnClickListener {  }
        }
    }

}

class CitiesDiffCallback : DiffUtil.ItemCallback<City>(){
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem == newItem
    }

}