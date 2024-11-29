package com.example.logincardview.controller

import android.content.Context
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.logincardview.MainScreenActivity
import com.example.logincardview.adapter.AdapterLocal
import com.example.logincardview.dao.DaoLocal
import com.example.logincardview.models.Local



class Controller(val context : Context) {
    lateinit var listLocales : MutableList<Local>
    private lateinit var adapterLocal: AdapterLocal

    init {
        initData()
    }

    private fun initData() {
        listLocales = DaoLocal.myDao.getDataLocals().toMutableList()
    }

    fun logOut() {
        Toast.makeText(context, "He mostrado datos en pantalla", Toast.LENGTH_LONG).show()
        listLocales.forEach(){
            println()
        }
    }

    fun setAdapter(recyclerView: RecyclerView) {
        adapterLocal = AdapterLocal(
            listLocales
        ) { pos ->
            delHotel(pos)
        }

        recyclerView.adapter = adapterLocal
    }


    private fun delHotel(pos: Int) {
        Toast.makeText(context, "Local $pos borrado", Toast.LENGTH_LONG).show()
        listLocales.removeAt(pos)
        adapterLocal.notifyItemRemoved(pos)

    }
}