package ru.binnyatoff.WeatherApp.screens.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import ru.binnyatoff.WeatherApp.R
import ru.binnyatoff.WeatherApp.appComponent
import ru.binnyatoff.WeatherApp.databinding.FragmentHomeBinding
import ru.binnyatoff.WeatherApp.screens.viewmodels.home.HomeViewModel
import ru.binnyatoff.WeatherApp.screens.viewmodels.home.HomeViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel> {
        viewModelFactory
    }
    private val binding: FragmentHomeBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            viewModel.state.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is HomeState.Loading -> {
                        temp.visibility = View.GONE
                        humidity.visibility = View.GONE
                        wind.visibility = View.GONE
                        location.visibility = View.GONE
                        data.visibility = View.GONE
                    }
                    is HomeState.Loaded -> {
                        temp.text = state.temp.toString()
                        humidity.text = state.humidity.toString()
                        wind.text = state.wind.toString()
                        location.text = state.location
                        data.text = state.currentTime
                        temp.visibility = View.VISIBLE
                        humidity.visibility = View.VISIBLE
                        wind.visibility = View.VISIBLE
                        location.visibility = View.VISIBLE
                        data.visibility = View.VISIBLE
                        val url = "http://openweathermap.org/img/wn/${state.icon}@2x.png"
                        Log.e("TAG", url)
                        Glide
                            .with(view)
                            .load(url)
                            .into(image)

                    }
                    is HomeState.Empty -> {
                        temp.visibility = View.GONE
                        humidity.visibility = View.GONE
                        wind.visibility = View.GONE
                        location.visibility = View.GONE
                        data.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
                    }
                    is HomeState.Error -> {
                        temp.visibility = View.GONE
                        humidity.visibility = View.GONE
                        wind.visibility = View.GONE
                        location.visibility = View.GONE
                        data.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
