package ru.binnyatoff.WeatherApp.screens.splash

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import android.window.SplashScreenView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.binnyatoff.WeatherApp.R

class SplashFragment : Fragment(R.layout.fragment_splash) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkSelfPermission()
    }

    private fun checkSelfPermission() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                findNavController().navigate(
                    R.id.action_splashFragment_to_mainFragment
                )
            } else {
                Log.e("TAG", "GOVNO")
                return@registerForActivityResult
            }
        }

        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}
