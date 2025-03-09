package com.example.logincardview.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.logincardview.R
import com.example.logincardview.domain.models.Restaurant

class RestaurantAdapter(
    var restaurantList: List<Restaurant>,
    private val onDeleteClick: (Restaurant) -> Unit,
    private val onEditClick: (Restaurant) -> Unit,
    private val onFavoriteClick: (Long) -> Unit,
    private var favoriteRestaurants: Set<Long>
) : RecyclerView.Adapter<RestaurantView>() {

    var isAdmin: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantView {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutItemRestaurant = R.layout.activity_restaurant
        return RestaurantView(layoutInflater.inflate(layoutItemRestaurant, parent, false))
    }

    fun updateList(newList: List<Restaurant>, favoriteRestaurants: Set<Long>) {
        restaurantList = newList
        this.favoriteRestaurants = favoriteRestaurants
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RestaurantView, position: Int) {
        val restaurant = restaurantList[position]
        val isFavorite = favoriteRestaurants.contains(restaurant.id)
        holder.setAdminVisibility(isAdmin)

        holder.itemView.findViewById<ImageButton>(R.id.delete_btn).setOnClickListener {
            onDeleteClick(restaurant)
            removeItem(position)
        }

        holder.itemView.findViewById<ImageButton>(R.id.edit_btn).setOnClickListener {
            onEditClick(restaurant)
            notifyItemChanged(position)
        }

        holder.renderize(restaurant, isFavorite) { restaurantId ->
            onFavoriteClick(restaurantId)
        }
    }

    fun updateFavorites(newFavorites: Set<Long>) {
        this.favoriteRestaurants = newFavorites
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        restaurantList = restaurantList.toMutableList().apply { removeAt(position) }
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = restaurantList.size

    fun setAdminState(isAdmin: Boolean) {
        this.isAdmin = isAdmin
        notifyDataSetChanged()
    }
}
