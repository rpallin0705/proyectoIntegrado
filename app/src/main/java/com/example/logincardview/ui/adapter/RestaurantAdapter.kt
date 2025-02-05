package com.example.logincardview.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logincardview.R
import com.example.logincardview.models.Local


class RestaurantAdapter (
    var listLocal : MutableList<Local>,
    var deleteOnClick : (Int) ->  Unit,
    var updateOnClick : (Int) -> Unit
) : RecyclerView.Adapter<RestaurantView> (){

  //Este método me creará un objeto de tipo ViewHolderLocal, por cada objeto de la listLocal
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantView {
        val layoutInflater = LayoutInflater.from(parent. context)
        val layoutItemLocal = R.layout.activity_restaurant
        return RestaurantView(
            layoutInflater.inflate(layoutItemLocal, parent, false),
            deleteOnClick,
            updateOnClick)
    }

    //Inmediatamente de crear el objeto ViewHodlerLocal, deberá renderizar la vista
    override fun onBindViewHolder(holder: RestaurantView, position: Int) {
        holder.renderize(listLocal[position])
    }


    override fun getItemCount(): Int = listLocal.size


}