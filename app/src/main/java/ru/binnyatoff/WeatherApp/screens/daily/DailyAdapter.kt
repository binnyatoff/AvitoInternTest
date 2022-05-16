package ru.binnyatoff.WeatherApp.screens.daily

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.binnyatoff.WeatherApp.R
import ru.binnyatoff.WeatherApp.data.model.Daily
import ru.binnyatoff.WeatherApp.databinding.WeatherItemBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat


class DailyAdapter : RecyclerView.Adapter<DailyViewHolder>() {
    private var weatherDailyList: List<Daily> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)
        return DailyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val currentWeatherDaily = weatherDailyList[position]
        holder.bind(currentWeatherDaily = currentWeatherDaily)
    }

    override fun getItemCount(): Int = weatherDailyList.size

    fun setData(weatherDailyList: List<Daily>) {
        val diffUtilCalback = DailyDiffUtil(weatherDailyList, this.weatherDailyList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCalback)
        this.weatherDailyList = weatherDailyList
        diffResult.dispatchUpdatesTo(this)
    }
}


class DailyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val viewBinding: WeatherItemBinding by viewBinding()

    fun bind(currentWeatherDaily: Daily?) {
        if (currentWeatherDaily != null) {
           Log.e("TAG", currentWeatherDaily.dt.toString())
            with(viewBinding) {
                date.text = currentWeatherDaily.dt
                temp.text = currentWeatherDaily.temp.max.toString()
                val url = "http://openweathermap.org/img/wn/${currentWeatherDaily.icon}@2x.png"

                Glide
                    .with(itemView)
                    .load(url)
                    .into(image)
            }
        }
    }

}

class DailyDiffUtil(var oldList: List<Daily>, var newList: List<Daily>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].temp == newList[newItemPosition].temp &&
                oldList[oldItemPosition].humidity == newList[newItemPosition].humidity &&
                oldList[oldItemPosition].pressure == newList[newItemPosition].pressure
    }

}