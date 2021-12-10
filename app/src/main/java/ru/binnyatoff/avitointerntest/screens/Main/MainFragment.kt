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
        val temperature:TextView = view.findViewById(R.id.temperature)
        viewModel.temperature.observe(viewLifecycleOwner,{
            temperature.text = "${floor(it).toInt()}"
        })
    }
}