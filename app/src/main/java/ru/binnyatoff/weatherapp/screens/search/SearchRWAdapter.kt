package ru.binnyatoff.weatherapp.screens.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.binnyatoff.weatherapp.data.modelsDTO.CurrentWeatherDTO
import ru.binnyatoff.weatherapp.data.toWeatherIcon
import ru.binnyatoff.weatherapp.databinding.WeatherItemBinding

class SearchRWAdapter : ListAdapter<CurrentWeatherDTO, SearchViewHolder>(SearchDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            WeatherItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class SearchViewHolder(private val binding: WeatherItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(currentWeatherDaily: CurrentWeatherDTO) {
        binding.temp.text = currentWeatherDaily.main.temp.toString()
        binding.day.text = currentWeatherDaily.name

        val url = currentWeatherDaily.weather[0].icon.toWeatherIcon()

        Glide
            .with(itemView)
            .load(url)
            .into(binding.image)
    }
}

object SearchDiffUtil : DiffUtil.ItemCallback<CurrentWeatherDTO>() {
    override fun areItemsTheSame(oldItem: CurrentWeatherDTO, newItem: CurrentWeatherDTO): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: CurrentWeatherDTO,
        newItem: CurrentWeatherDTO,
    ): Boolean {
        return oldItem.main == newItem.main &&
                oldItem.name == newItem.name
    }

}