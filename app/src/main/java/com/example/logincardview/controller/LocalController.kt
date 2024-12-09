package com.example.logincardview.controller

import LocalDialogFragmentCU
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.logincardview.MainActivity
import com.example.logincardview.adapter.LocalAdapter
import com.example.logincardview.dao.LocalDao
import com.example.logincardview.models.ArgumentsLocal
import com.example.logincardview.models.Local
import com.example.logincardview.ui.LocalFragment

class LocalController(private val context: Context, private val contextFragment: LocalFragment) {
    private lateinit var listLocales: MutableList<Local>
    private lateinit var localAdapter: LocalAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    init {
        initData()
    }

    private fun initData() {
        setScrollWithOffSetLinearLayout()
        listLocales = LocalDao.myDao.getDataLocals().toMutableList()
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
        val myActivity = context as MainActivity
        // listener de edit y delete
        myActivity.getMainScreenBinding().btnAdd.setOnClickListener {
            addLocal()
        }
    }

    private fun setAdapter() {

        localAdapter = LocalAdapter(
            listLocales,
            { pos: Int -> delLocal(pos) },
            { pos: Int -> updateLocal(pos) }
        )

        contextFragment.getLocalFragmentBinding().recyclerViewLocal.adapter = localAdapter

    }

    private fun updateLocal(pos: Int) {
        val localToUpdate = listLocales[pos]  // Obtener el local en la posición seleccionada

        val editDialog = LocalDialogFragmentCU().apply {
            // Configurar el Bundle con los datos del local seleccionado
            arguments = Bundle().apply {
                putString(ArgumentsLocal.ARGUMENT_NAME, localToUpdate.nombre)
                putString(ArgumentsLocal.ARGUMENT_ADDRESS, localToUpdate.direccion)
                putString(ArgumentsLocal.ARGUMENT_PHONE, localToUpdate.contacto)
                putInt(ArgumentsLocal.ARGUMENT_RATE, localToUpdate.valoracion)
                putString(ArgumentsLocal.ARGUMENT_DESCRIPTION, localToUpdate.descripcion)
            }
        }

        // Mostrar el diálogo de edición
        val myActivity = context as MainActivity
        editDialog.show(myActivity.supportFragmentManager, "Editamos el local")

        // Configurar una función de callback para actualizar el local después de editar
        editDialog.onUpdate = { updatedLocal ->
            okOnEditLocal(updatedLocal, pos)
        }
    }

    // Función para actualizar el local en la lista y notificar al adaptador
    private fun okOnEditLocal(editLocal: Local, pos: Int) {
        listLocales[pos] = editLocal
        localAdapter.notifyItemChanged(pos)

        (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(pos, 20)
    }



    private fun delLocal(pos: Int) {
        Toast.makeText(context, "Local $pos borrado", Toast.LENGTH_LONG).show()
        listLocales.removeAt(pos)
        localAdapter.notifyItemRemoved(pos)
    }



    private fun addLocal() {

        val newLocal = Local("", "", "", 5, "")

        val editDialog = LocalDialogFragmentCU().apply {
            // Configurar el Bundle con los datos del local seleccionado
            arguments = Bundle().apply {
                putString(ArgumentsLocal.ARGUMENT_NAME, newLocal.nombre)
                putString(ArgumentsLocal.ARGUMENT_ADDRESS, newLocal.direccion)
                putString(ArgumentsLocal.ARGUMENT_PHONE, newLocal.contacto)
                putInt(ArgumentsLocal.ARGUMENT_RATE, newLocal.valoracion)
                putString(ArgumentsLocal.ARGUMENT_DESCRIPTION, newLocal.descripcion)
            }
        }

        // Mostrar el diálogo de edición
        val myActivity = context as MainActivity
        editDialog.show(myActivity.supportFragmentManager, "Creamos el local")

        // Configurar una función de callback para actualizar el local después de editar
        editDialog.onUpdate = { updatedLocal ->
            okOnAddLocal(updatedLocal)
        }
    }

    private fun okOnAddLocal(newLocal: Local) {
        listLocales.add(newLocal)
        localAdapter.notifyItemInserted(listLocales.size + 1)

        (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(listLocales.size + 1, 20)
    }
}
