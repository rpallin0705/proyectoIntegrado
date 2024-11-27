package com.example.logincardview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logincardview.models.Local


class AdapterLocal (var listLocal : MutableList<Local>) : RecyclerView.Adapter<ViewLocal> (){

  //Este método me creará un objeto de tipo ViewHolderLocal, por cada objeto de la listLocal
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewLocal {
        TODO("Not yet implemented")
    }

    //Inmediatamente de crear el objeto ViewHodlerLocal, deberá renderizar la vista
    override fun onBindViewHolder(holder: ViewLocal, position: Int) {
        TODO("Not yet implemented")
    }


    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}