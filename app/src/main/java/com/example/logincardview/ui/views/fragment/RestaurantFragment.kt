package com.example.logincardview.ui.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logincardview.R
import com.example.logincardview.databinding.FragmentRestaurantBinding
import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.ui.adapter.RestaurantAdapter
import com.example.logincardview.ui.modelview.RestaurantViewModel

class RestaurantFragment : Fragment(R.layout.fragment_restaurant) {

    private lateinit var binding: FragmentRestaurantBinding
    private lateinit var adapter: RestaurantAdapter
    private val restaurantViewModel: RestaurantViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeViewModel()
        setupAddButton()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerViewLocal.layoutManager = LinearLayoutManager(requireContext())

        adapter = RestaurantAdapter(emptyList(), ::onDeleteRestaurant, ::onEditRestaurant)
        binding.recyclerViewLocal.adapter = adapter
    }

    private fun setupAddButton() {
        binding.addButton.setOnClickListener {
            showAddRestaurantDialog()
        }
    }

    private fun onDeleteRestaurant(position: Int) {
        val restaurantName = adapter.restaurantList[position].name
        restaurantViewModel.deleteRestaurant(position)
        Toast.makeText(requireContext(), "Restaurante eliminado: $restaurantName", Toast.LENGTH_SHORT).show()
    }

    private fun onEditRestaurant(restaurant: Restaurant) {
        val dialog = RestaurantDialogFragmentCU().newInstance(restaurant)
        dialog.onUpdate = { updatedRestaurant ->
            restaurantViewModel.editRestaurant(restaurant, updatedRestaurant)
        }
        dialog.show(parentFragmentManager, "EditRestaurantDialogFragment")
    }

    private fun showAddRestaurantDialog() {
        val dialog = RestaurantDialogFragmentCU()
        dialog.onUpdate = { restaurant ->
            restaurantViewModel.addRestaurant(restaurant)
        }
        dialog.show(parentFragmentManager, "AddRestaurantDialoFg")
    }

    private fun observeViewModel() {
        restaurantViewModel.restaurantLiveData.observe(viewLifecycleOwner) { restaurants ->
            if (restaurants.isNotEmpty()) {
                updateRestaurantList(restaurants)
            }
        }

        loadData()
    }

    private fun loadData() {
        restaurantViewModel.getRestaurants()
    }

    private fun updateRestaurantList(restaurants: List<Restaurant>) {
        if (adapter.restaurantList != restaurants) {
            val previousSize = adapter.restaurantList.size
            adapter.restaurantList = restaurants
            adapter.notifyDataSetChanged()

            if (restaurants.size > previousSize) {
                binding.recyclerViewLocal.smoothScrollToPosition(restaurants.size - 1)
            }
        }
    }
}
