package ru.binnyatoff.WeatherApp.screens.daily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.binnyatoff.WeatherApp.data.model.Daily
import ru.binnyatoff.WeatherApp.databinding.WeatherItemBinding
import com.bumptech.glide.Glide


class DailyAdapter : ListAdapter<Daily, DailyViewHolder>(DailyDiffUtil) {

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

    fun bind(currentWeatherDaily: Daily?) {
        if (currentWeatherDaily != null) {
            binding.date.text = currentWeatherDaily.dt
            binding.temp.text = currentWeatherDaily.temp.max.toString()

            val url = "http://openweathermap.org/img/wn/${currentWeatherDaily.icon}@2x.png"  //Надо бы вынести куда-нибудь, а не хранить ссылку здесь(Mapper)

            Glide
                .with(itemView)
                .load(url)
                .into(binding.image)
        }
    }
}

object DailyDiffUtil :
    DiffUtil.ItemCallback<Daily>() {
    override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem.temp == newItem.temp &&
                oldItem.humidity == newItem.humidity &&
                oldItem.pressure == newItem.pressure
    }

}