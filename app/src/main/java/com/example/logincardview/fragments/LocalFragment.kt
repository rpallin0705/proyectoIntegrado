package com.example.logincardview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.logincardview.R
import com.example.logincardview.adapter.AdapterLocal
import com.example.logincardview.models.Local
import com.example.logincardview.models.LocalRepository

class LocalFragment : Fragment(R.layout.fragment_local) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterLocal

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura el RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewLocal)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Obtiene los locales desde el LocalRepository
        val listLocal = LocalRepository.locales.toMutableList()

        // Configura el Adapter
        adapter = AdapterLocal(listLocal) { position ->
            // Acción de eliminar
            listLocal.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
        recyclerView.adapter = adapter
    }
}
