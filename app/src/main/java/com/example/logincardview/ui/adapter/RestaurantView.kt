package com.example.logincardview.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.logincardview.R
import com.example.logincardview.databinding.ActivityRestaurantBinding
import com.example.logincardview.domain.models.Restaurant

class RestaurantView(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ActivityRestaurantBinding.bind(view)

    // Lista de estrellas para actualizar su estado
    private val starImages = listOf(
        binding.imageView3,
        binding.imageView4,
        binding.imageView5,
        binding.imageView6,
        binding.imageView7
    )

    // Método para renderizar el Restaurant
    fun renderize(restaurant: Restaurant) {
        binding.localName.text = restaurant.name
        binding.localAddress.text = restaurant.address
        binding.localPhone.text = restaurant.phone

        Glide.with(itemView.context)
            .load(R.drawable.cardview_img1)
            .centerCrop()
            .into(binding.localImage)

        // Actualiza las estrellas según el rate
        updateStars(restaurant.rating)
    }

    // Método para actualizar las estrellas según la valoración
    private fun updateStars(rate: Int) {
        for (i in starImages.indices) {
            if (i < rate) {
                starImages[i].setImageResource(R.drawable.baseline_star_24) // Estrella rellena
            } else {
                starImages[i].setImageResource(R.drawable.baseline_star_outline_24) // Estrella vacía
            }
        }
    }
}
