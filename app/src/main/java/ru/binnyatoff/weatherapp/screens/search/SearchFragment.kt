package ru.binnyatoff.weatherapp.screens.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.binnyatoff.weatherapp.R
import ru.binnyatoff.weatherapp.appComponent
import ru.binnyatoff.weatherapp.databinding.FragmentSearchBinding
import ru.binnyatoff.weatherapp.screens.search.viewmodels.SearchViewModel
import ru.binnyatoff.weatherapp.screens.search.viewmodels.SearchViewModelFactory
import ru.binnyatoff.weatherapp.screens.search.viewmodels.SearchViewState
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {
    @Inject
    lateinit var searchViewModelFactory: SearchViewModelFactory

    private val viewModel by viewModels<SearchViewModel> {
        searchViewModelFactory
    }

    private val binding: FragmentSearchBinding by viewBinding()

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SearchRWAdapter()

        with(binding) {
            searchRw.adapter = adapter
            searchRw.layoutManager = LinearLayoutManager(requireContext())

            viewModel.currentWeather.observe(viewLifecycleOwner){searchViewState->
                when (searchViewState){
                    is SearchViewState.Loading ->{
                        searchView.isEnabled = false
                        searchView.visibility = View.GONE
                    }
                    is SearchViewState.Loaded ->{
                        adapter.submitList(searchViewState.currentWeatherDTO)
                        searchView.visibility = View.VISIBLE
                    }
                }
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    Log.e("TAG", query)
                    viewModel.getCityWeather(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
    }
}