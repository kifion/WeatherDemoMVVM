package com.example.weather.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.domain.model.City
import kotlinx.android.synthetic.main.recycler_view_search_item.view.*

class SearchListAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    var layout = R.layout.recycler_view_search_item

    var items = ArrayList<City>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(
                    layout,
                    viewGroup,
                    false
                )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], clickListener)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: City, clickListener: ClickListener) = with(itemView) {
            setOnClickListener { clickListener.onCitySelected(item) }
            city_name.text = item.name
        }
    }

    interface ClickListener {
        fun onCitySelected(city: City)
    }
}