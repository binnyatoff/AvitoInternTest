package ru.binnyatoff.WeatherApp.screens.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.binnyatoff.WeatherApp.R
import ru.binnyatoff.WeatherApp.appComponent
import ru.binnyatoff.WeatherApp.databinding.FragmentSearchBinding
import ru.binnyatoff.WeatherApp.screens.viewmodels.search.SearchViewModel
import ru.binnyatoff.WeatherApp.screens.viewmodels.search.SearchViewModelFactory
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {
    @Inject
    lateinit var searchViewModelFactory: SearchViewModelFactory
    private lateinit var searchViewModel: SearchViewModel
    private val binding: FragmentSearchBinding by viewBinding()
    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel =
            ViewModelProvider(this, searchViewModelFactory)[SearchViewModel::class.java]
        with(binding) {

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
}