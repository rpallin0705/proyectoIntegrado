package com.example.logincardview.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.logincardview.R
import com.example.logincardview.domain.models.Restaurant

class RestaurantAdapter(
    var restaurantList: List<Restaurant> = emptyList(),
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<RestaurantView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantView {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutItemRestaurant = R.layout.activity_restaurant
        return RestaurantView(
            layoutInflater.inflate(layoutItemRestaurant, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RestaurantView, position: Int) {
        val restaurant = restaurantList[position]
        holder.itemView.findViewById<ImageButton>(R.id.delete_btn).setOnClickListener {
            onDeleteClick(position)
        }
        holder.renderize(restaurant)
    }

    override fun getItemCount(): Int = restaurantList.size
}
