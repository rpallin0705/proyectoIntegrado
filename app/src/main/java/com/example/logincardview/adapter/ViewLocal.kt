package com.example.logincardview.adapter

import android.icu.text.Transliterator.Position
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.logincardview.R
import com.example.logincardview.databinding.ActivityLocalBinding
import com.example.logincardview.databinding.ActivityMainScreenBinding
import com.example.logincardview.models.Local

class ViewLocal(view : View, var deleteOnClick : (Int) -> Unit) : RecyclerView.ViewHolder(view) {

    private val binding = ActivityLocalBinding.bind(view)

    //Método para renderizar el Local
    fun renderize(local : Local){
        binding.localName.text = local.nombre
        binding.localAddress.text = local.direccion
        binding.localPhone.text = local.contacto

        Glide.with(itemView.context)
            .load(R.drawable.cardview_img1)
            .centerCrop()
            .into(binding.localImage)

        // Llamar a setOnClickListener para el botón de eliminar
        setOnClickListener(adapterPosition)
    }

    //Método para asignar el click al botón de eliminar
    private fun setOnClickListener(position : Int){
        binding.deleteBtn.setOnClickListener {
            deleteOnClick(position)
        }
    }
}