package ru.binnyatoff.weatherapp.screens.home

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import ru.binnyatoff.weatherapp.R
import ru.binnyatoff.weatherapp.appComponent
import ru.binnyatoff.weatherapp.databinding.FragmentHomeBinding
import ru.binnyatoff.weatherapp.screens.home.viewmodels.HomeViewModel
import ru.binnyatoff.weatherapp.screens.home.viewmodels.HomeViewModelFactory
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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    viewModel.obtainEvent(HomeEvent.ScreenInit)
                }

                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                }

                else -> {
                    // No location access granted.
                }
            }
        }

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))


        with(binding) {
            viewModel.state.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is HomeState.Loading -> {
                        temp.visibility = View.GONE
                        humidity.visibility = View.GONE
                        wind.visibility = View.GONE
                        location.visibility = View.GONE
                        data.visibility = View.GONE
                        viewModel.obtainEvent(HomeEvent.ScreenInit)
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

    override fun onPause() {
        super.onPause()
        viewModel.removeUpdatesLocationListener()
    }
}
