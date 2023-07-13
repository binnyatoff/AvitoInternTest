package ru.binnyatoff.weatherapp.screens.daily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.binnyatoff.weatherapp.data.DailyMap
import ru.binnyatoff.weatherapp.databinding.WeatherItemBinding


class DailyAdapter : ListAdapter<DailyMap, DailyViewHolder>(DailyDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        return DailyViewHolder(
            WeatherItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class DailyViewHolder(private val binding: WeatherItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(currentWeatherDaily: DailyMap) {
            binding.temp.text = currentWeatherDaily.temp.max.toString()
            binding.day.text = currentWeatherDaily.dt

            val url = currentWeatherDaily.icon

            Glide
                .with(itemView)
                .load(url)
                .into(binding.image)
        }
}

object DailyDiffUtil :
    DiffUtil.ItemCallback<DailyMap>() {
    override fun areItemsTheSame(oldItem: DailyMap, newItem: DailyMap): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DailyMap, newItem: DailyMap): Boolean {
        return oldItem.temp == newItem.temp &&
                oldItem.humidity == newItem.humidity &&
                oldItem.pressure == newItem.pressure
    }

}
