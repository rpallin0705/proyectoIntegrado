package com.example.logincardview.ui.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logincardview.R
import com.example.logincardview.databinding.FragmentRestaurantBinding
import com.example.logincardview.ui.adapter.RestaurantAdapter
import com.example.logincardview.ui.modelview.RestaurantViewModel
import com.example.logincardview.ui.views.fragment.RestaurantDialogFragmentCU

class RestaurantFragment : Fragment(R.layout.fragment_restaurant) {

    private lateinit var bindingFragment: FragmentRestaurantBinding
    lateinit var adapter: RestaurantAdapter
    val restaurantViewModel: RestaurantViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bindingFragment = FragmentRestaurantBinding.inflate(inflater, container, false)

        initRecyclerView()
        observeViewModel()  // Observando los cambios en el ViewModel

        // Configurar el botón para abrir el DialogFragment
        bindingFragment.addButton.setOnClickListener {
            // Crear el DialogFragment para añadir un restaurante
            val dialog = RestaurantDialogFragmentCU()

            // Configurar el callback onUpdate para añadir el restaurante
            dialog.onUpdate = { restaurant ->
                restaurantViewModel.addRestaurant(restaurant)
            }

            // Mostrar el DialogFragment
            dialog.show(parentFragmentManager, "AddRestaurantDialog")
        }

        return bindingFragment.root
    }

    private fun loadData() {
        restaurantViewModel.getRestaurants() // Llamada para obtener los datos
    }

    private fun initRecyclerView() {
        // Inicializar el RecyclerView con el LinearLayoutManager
        bindingFragment.recyclerViewLocal.layoutManager = LinearLayoutManager(requireContext())

        // Inicializar el adapter
        adapter = RestaurantAdapter()

        // Asignar el adapter al RecyclerView
        bindingFragment.recyclerViewLocal.adapter = adapter
    }

    private fun observeViewModel() {
        // Observamos los datos de los restaurantes en el LiveData
        restaurantViewModel.restaurantLiveData.observe(viewLifecycleOwner) { restaurants ->
            // Actualizamos la lista en el adapter cuando los datos cambian
            adapter.restaurantList = restaurants
            adapter.notifyDataSetChanged()  // Notificar al adapter que los datos han cambiado
        }

        // Cargar los datos desde el ViewModel
        loadData()
    }
}
