package com.example.logincardview.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logincardview.R
import com.example.logincardview.domain.models.Restaurant

class RestaurantAdapter(
    var restaurantList: List<Restaurant> = emptyList() // Lista vacía por defecto
) : RecyclerView.Adapter<RestaurantView>() {

    // Este método creará un objeto de tipo RestaurantView (ViewHolder) por cada objeto de la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantView {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutItemRestaurant = R.layout.activity_restaurant // Asegúrate de que este layout existe
        return RestaurantView(
            layoutInflater.inflate(layoutItemRestaurant, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RestaurantView, position: Int) {
        // Renderizamos el restaurant en el ViewHolder correspondiente
        holder.renderize(restaurantList[position])
    }

    override fun getItemCount(): Int = restaurantList.size
}
