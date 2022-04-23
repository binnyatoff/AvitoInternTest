package ru.binnyatoff.WeatherApp.screens.Search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.binnyatoff.WeatherApp.R

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private val searchViewModel:SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchView = view.findViewById(R.id.search_view) as SearchView

        val temp: TextView = view.findViewById(R.id.search_temp)
        val humidity: TextView = view.findViewById(R.id.search_humidity)
        val wind: TextView = view.findViewById(R.id.search_wind)
        val location: TextView = view.findViewById(R.id.search_location)


        searchViewModel.temp.observe(viewLifecycleOwner) {
            temp.text = "${kotlin.math.floor(it).toInt()}"
        }
        searchViewModel.humidity.observe(viewLifecycleOwner) {
            humidity.text = it.toString()
        }
        searchViewModel.wind.observe(viewLifecycleOwner) {
            wind.text = it.toString()
        }
        searchViewModel.location.observe(viewLifecycleOwner) {
            location.text = it.toString()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.e("TAG", query)
                searchViewModel.getCityWeather(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}