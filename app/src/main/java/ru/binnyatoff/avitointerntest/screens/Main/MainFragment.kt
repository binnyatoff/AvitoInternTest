package ru.binnyatoff.avitointerntest.screens.Main

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.binnyatoff.avitointerntest.R
import kotlin.math.floor

@AndroidEntryPoint
class MainFragment:Fragment(R.layout.fragment_main) {
    private val viewModel:MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val temp:TextView = view.findViewById(R.id.temp)
        val humidity:TextView = view.findViewById(R.id.humidity)
        val wind:TextView = view.findViewById(R.id.wind)
        val location:TextView = view.findViewById(R.id.location)


        viewModel.temp.observe(viewLifecycleOwner,{
            temp.text = "${floor(it).toInt()}"
        })
        viewModel.humidity.observe(viewLifecycleOwner,{
            humidity.text = it.toString()
        })
        viewModel.wind.observe(viewLifecycleOwner,{
            wind.text = it.toString()
        })
        viewModel.location.observe(viewLifecycleOwner,{
            location.text = it.toString()
        })
    }
}