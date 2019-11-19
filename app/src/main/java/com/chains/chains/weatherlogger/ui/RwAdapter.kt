package com.chains.chains.weatherlogger.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.chains.chains.weatherlogger.R
import com.chains.chains.weatherlogger.vo.WeatherConditions
import kotlinx.android.synthetic.main.rw_item.view.*

class RwAdapter(private var actions: List<WeatherConditions>) :
    RecyclerView.Adapter<RwAdapter.ActionViewHolder>() {

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.bind(actions[position], holder.itemView.context)
    }


    override fun onCreateViewHolder(v: ViewGroup, viewType: Int): ActionViewHolder {
        val view = LayoutInflater.from(v.context).inflate(R.layout.rw_item, v, false)
        return ActionViewHolder(view)
    }

    override fun getItemCount() = actions.size

    fun setData(actions: List<WeatherConditions>) {
        this.actions = actions
        notifyDataSetChanged()
    }

    class ActionViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var weatherConditions: WeatherConditions? = null

        override fun onClick(p0: View?) {
            Toast.makeText(p0?.context, "More ${weatherConditions?.city}", Toast.LENGTH_SHORT)
                .show()
        }

        fun bind(data: WeatherConditions, context: Context) {
            this.weatherConditions = data
            itemView.setOnClickListener(this)
            itemView.item_city.text = data.city
            itemView.item_temp.text = data.temp
            itemView.item_time.text = data.timestamp
        }

    }

}