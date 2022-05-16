package ru.binnyatoff.WeatherApp.screens.daily

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.binnyatoff.WeatherApp.R
import ru.binnyatoff.WeatherApp.appComponent
import ru.binnyatoff.WeatherApp.databinding.FragmentDailyBinding
import ru.binnyatoff.WeatherApp.screens.viewmodels.DailyViewModel
import ru.binnyatoff.WeatherApp.screens.viewmodels.DailyViewModelFactory
import javax.inject.Inject
import by.kirich1409.viewbindingdelegate.viewBinding

class DailyFragment : Fragment(R.layout.fragment_daily) {
    private val viewModel by viewModels<DailyViewModel> {
        viewModelFactory
    }

    private val viewBinding: FragmentDailyBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: DailyViewModelFactory
    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var adapter = DailyAdapter()
        with(viewBinding) {
            recyclerview.adapter = adapter
            recyclerview.layoutManager = LinearLayoutManager(requireContext())
        }


        viewModel.weatherDailyList.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

    }
}