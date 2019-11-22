package com.chains.chains.weatherlogger.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chains.chains.weatherlogger.R
import com.chains.chains.weatherlogger.databinding.RwItemBinding
import com.chains.chains.weatherlogger.util.MyDiffUtilCallback
import com.chains.chains.weatherlogger.viewmodel.ConditionViewModel
import com.chains.chains.weatherlogger.vo.WeatherConditions

class RwAdapter : RecyclerView.Adapter<RwAdapter.ActionViewHolder>() {

    private var actions: List<WeatherConditions>

    init {
        actions = emptyList()
    }

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.bind(actions[position])
    }


    override fun onCreateViewHolder(v: ViewGroup, viewType: Int): ActionViewHolder {
        val binding = DataBindingUtil.inflate<RwItemBinding>(
            LayoutInflater.from(v.context),
            R.layout.rw_item,
            v,
            false

        )
        return ActionViewHolder(binding)
    }

    override fun getItemCount() = actions.size

    fun setData(actions: List<WeatherConditions>) {
        val result = DiffUtil.calculateDiff(MyDiffUtilCallback(this.actions, actions))
        result.dispatchUpdatesTo(this)
        this.actions = actions
    }

    class ActionViewHolder(private val binding: RwItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val viewModel = ConditionViewModel()

        fun bind(data: WeatherConditions) {
            viewModel.bind(data)
            binding.viewModel = viewModel
        }

    }
}
