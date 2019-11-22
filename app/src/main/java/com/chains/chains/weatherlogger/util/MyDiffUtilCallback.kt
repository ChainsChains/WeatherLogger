package com.chains.chains.weatherlogger.util

import androidx.recyclerview.widget.DiffUtil
import com.chains.chains.weatherlogger.vo.WeatherConditions

class MyDiffUtilCallback(
    private var oldList: List<WeatherConditions>,
    private var newList: List<WeatherConditions>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].timestamp == newList[newItemPosition].timestamp

    override fun getOldListSize() = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]


}
