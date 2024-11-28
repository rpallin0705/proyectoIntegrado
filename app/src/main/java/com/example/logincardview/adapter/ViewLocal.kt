package com.example.logincardview.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.logincardview.R
import com.example.logincardview.models.Local

class ViewLocal(view : View) : RecyclerView.ViewHolder (view){

    private var textViewName = view.findViewById<TextView>(R.id.local_name)
    private var textViewAddress = view.findViewById<TextView>(R.id.local_address)
    private var textViewPhone = view.findViewById<TextView>(R.id.local_phone)
    private var imageView = view.findViewById<ImageView>(R.id.local_image)


    //me cogerá la view y un objeto Local y sólo setea la vista
    fun renderize (local : Local){
        textViewPhone.text = local.nombre
        textViewAddress.text = local.direccion
        textViewPhone.text = local.contacto

        Glide.with(itemView.context)
            .load(R.drawable.cardview_img1)
            .centerCrop()
            .into(imageView)
    }

}