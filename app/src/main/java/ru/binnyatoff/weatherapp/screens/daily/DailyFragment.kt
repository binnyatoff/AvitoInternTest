package ru.binnyatoff.weatherapp.screens.daily

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ru.binnyatoff.weatherapp.screens.daily.viewmodels.DailyViewModelFactory
import javax.inject.Inject
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.binnyatoff.weatherapp.R
import ru.binnyatoff.weatherapp.appComponent
import ru.binnyatoff.weatherapp.databinding.FragmentDailyBinding
import ru.binnyatoff.weatherapp.screens.daily.viewmodels.DailyViewModel

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
        val adapter = DailyAdapter()
        with(viewBinding) {
            recyclerview.adapter = adapter
            recyclerview.layoutManager = LinearLayoutManager(requireContext())
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
       //    viewModel.weatherDailyList.collect(){ data->
         //     adapter.submitList(data)
          // }
        }
    }
}