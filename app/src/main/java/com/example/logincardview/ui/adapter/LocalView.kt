package com.example.logincardview.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.logincardview.R
import com.example.logincardview.databinding.ActivityLocalBinding
import com.example.logincardview.models.Local

class LocalView(
    view: View,
    var deleteOnClick: (Int) -> Unit,
    var updateOnClick: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val binding = ActivityLocalBinding.bind(view)

    // Lista de estrellas para actualizar su estado
    private val starImages = listOf(
        binding.imageView3,
        binding.imageView4,
        binding.imageView5,
        binding.imageView6,
        binding.imageView7
    )

    // Método para renderizar el Local
    fun renderize(local: Local) {
        binding.localName.text = local.nombre
        binding.localAddress.text = local.direccion
        binding.localPhone.text = local.contacto

        Glide.with(itemView.context)
            .load(R.drawable.cardview_img1)
            .centerCrop()
            .into(binding.localImage)

        // Actualiza las estrellas según el rate
        updateStars(local.valoracion)

        // Asignar los listeners
        setOnClickListener(adapterPosition)
    }

    // Método para asignar el click al botón de eliminar
    private fun setOnClickListener(position: Int) {
        binding.deleteBtn.setOnClickListener {
            deleteOnClick(position)
        }

        binding.editBtn.setOnClickListener {
            updateOnClick(position)
        }
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
