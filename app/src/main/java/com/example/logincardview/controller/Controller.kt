package com.example.logincardview.controller

import AddLocalDFragment
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.logincardview.MainScreenActivity
import com.example.logincardview.adapter.AdapterLocal
import com.example.logincardview.dao.DaoLocal
import com.example.logincardview.models.ArgumentsLocal
import com.example.logincardview.models.Local
import com.example.logincardview.ui.LocalFragment

class Controller(val context: Context, val contextFragment: LocalFragment) {
    lateinit var listLocales: MutableList<Local>
    private lateinit var adapterLocal: AdapterLocal
    private lateinit var layoutManager: RecyclerView.LayoutManager

    init {
        initData()
    }

    private fun initData() {
        setScrollWithOffSetLinearLayout()
        listLocales = DaoLocal.myDao.getDataLocals().toMutableList()
        setAdapter()
        initOnClickListener()
    }

    private fun setScrollWithOffSetLinearLayout() {
        layoutManager = contextFragment
            .getLocalFragmentBinding()
            .recyclerViewLocal
            .layoutManager as LinearLayoutManager
    }

    private fun initOnClickListener() {
        val myActivity = context as MainScreenActivity
        // listener de edit y delete
        myActivity.getMainScreenBinding().btnAdd.setOnClickListener {
            addLocal()
        }
    }

    fun logOut() {
        Toast.makeText(context, "He mostrado datos en pantalla", Toast.LENGTH_LONG).show()
        listLocales.forEach {
            println(it)
        }
    }

    fun setAdapter() {

        adapterLocal = AdapterLocal(
            listLocales,
            { pos: Int -> delLocal(pos) },
            { pos: Int -> updateLocal(pos) }
        )

        contextFragment.getLocalFragmentBinding().recyclerViewLocal.adapter = adapterLocal

    }

    private fun updateLocal(pos: Int) {
        val localToUpdate = listLocales[pos]  // Obtener el local en la posición seleccionada

        val editDialog = AddLocalDFragment().apply {
            // Configurar el Bundle con los datos del local seleccionado
            arguments = Bundle().apply {
                putString(ArgumentsLocal.ARGUMENT_NAME, localToUpdate.nombre)
                putString(ArgumentsLocal.ARGUMENT_ADDRESS, localToUpdate.direccion)
                putString(ArgumentsLocal.ARGUMENT_PHONE, localToUpdate.contacto)
                putInt(ArgumentsLocal.ARGUMENT_RATE, localToUpdate.valoracion)
            }
        }

        // Mostrar el diálogo de edición
        val myActivity = context as MainScreenActivity
        editDialog.show(myActivity.supportFragmentManager, "Editamos el local")

        // Configurar una función de callback para actualizar el local después de editar
        editDialog.onUpdate = { updatedLocal ->
            okOnEditLocal(updatedLocal, pos)
        }
    }

    // Función para actualizar el local en la lista y notificar al adaptador
    private fun okOnEditLocal(editLocal: Local, pos: Int) {
        listLocales[pos] = editLocal
        adapterLocal.notifyItemChanged(pos)

        (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(pos, 20)
    }



    private fun delLocal(pos: Int) {
        Toast.makeText(context, "Local $pos borrado", Toast.LENGTH_LONG).show()
        listLocales.removeAt(pos)
        adapterLocal.notifyItemRemoved(pos)
    }



    private fun addLocal() {
        Toast.makeText(context, "Añadir nuevo local", Toast.LENGTH_SHORT)
        val dialog = AddLocalDFragment()/*{
            hotel -> okOnNewLocal(Local)
        }*/
        val myActivity = context as MainScreenActivity
        dialog.show(myActivity.supportFragmentManager, "Añadimos un nuevo local")
    }
}
