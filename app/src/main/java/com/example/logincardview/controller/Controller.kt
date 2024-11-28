package com.example.logincardview.controller

import android.content.Context
import android.widget.Toast
import com.example.logincardview.MainScreenActivity
import com.example.logincardview.adapter.AdapterLocal
import com.example.logincardview.dao.DaoLocal
import com.example.logincardview.models.Local



class Controller(val context : Context) {
    lateinit var listLocales : MutableList<Local>

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

    fun setAdapter() {
        val myActivity = context as MainScreenActivity
        myActivity.getBinding().myRecyclerView.adapter = AdapterLocal(listLocales)
    }
}