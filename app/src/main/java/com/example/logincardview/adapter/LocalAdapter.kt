package com.example.logincardview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logincardview.R
import com.example.logincardview.models.Local


class LocalAdapter (
    var listLocal : MutableList<Local>,
    var deleteOnClick : (Int) ->  Unit,
    var updateOnClick : (Int) -> Unit
) : RecyclerView.Adapter<LocalView> (){

  //Este método me creará un objeto de tipo ViewHolderLocal, por cada objeto de la listLocal
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalView {
        val layoutInflater = LayoutInflater.from(parent. context)
        val layoutItemLocal = R.layout.activity_local
        return LocalView(
            layoutInflater.inflate(layoutItemLocal, parent, false),
            deleteOnClick,
            updateOnClick)
    }

    //Inmediatamente de crear el objeto ViewHodlerLocal, deberá renderizar la vista
    override fun onBindViewHolder(holder: LocalView, position: Int) {
        holder.renderize(listLocal[position])
    }


    override fun getItemCount(): Int = listLocal.size


}