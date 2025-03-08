package com.example.logincardview.ui.views.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logincardview.R
import com.example.logincardview.data.repository.RestaurantRepository
import com.example.logincardview.databinding.FragmentRestaurantBinding
import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.network.RetrofitClient
import com.example.logincardview.ui.adapter.RestaurantAdapter
import com.example.logincardview.ui.modelview.RestaurantViewModel
import com.example.logincardview.ui.modelview.RestaurantViewModelFactory

class RestaurantFragment : Fragment(R.layout.fragment_restaurant) {

    private lateinit var binding: FragmentRestaurantBinding
    private lateinit var adapter: RestaurantAdapter
    private val restaurantViewModel: RestaurantViewModel by viewModels {
        RestaurantViewModelFactory(RestaurantRepository(RetrofitClient.restaurantApi))
    }
    private var isFirstLoad = true
    private lateinit var sharedPreferences: SharedPreferences

    private val prefListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == "is_admin") {
            val isAdmin = sharedPreferences.getBoolean("is_admin", false)
            adapter.setAdminState(isAdmin)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        sharedPreferences =
            requireActivity().getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener(prefListener)

        setupRecyclerView()
        observeViewModel()
        setupAddButton()

        val isAdmin = sharedPreferences.getBoolean("is_admin", false)

        if (isAdmin)
            binding.addButton.visibility = View.VISIBLE
        else
            binding.addButton.visibility = View.GONE

        adapter.setAdminState(sharedPreferences.getBoolean("is_admin", false))
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(prefListener)
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

    private fun onDeleteRestaurant(restaurant: Restaurant) {
        val restaurantId = restaurant.id
        restaurantViewModel.deleteRestaurant(restaurantId!!)

        Toast.makeText(
            requireContext(),
            "Restaurante eliminado: ${restaurant.name}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onEditRestaurant(restaurant: Restaurant) {
        val dialog = RestaurantDialogFragmentCU().newInstance(restaurant, isEdit = true)
        dialog.onUpdate = { updatedRestaurant ->
            restaurantViewModel.editRestaurant(restaurant, updatedRestaurant)
        }
        dialog.show(parentFragmentManager, "EditRestaurantDialogFragment")
    }

    private fun showAddRestaurantDialog() {
        val dialog = RestaurantDialogFragmentCU().newInstance(
            Restaurant(
                id = null,
                name = "",
                address = "",
                phone = "",
                rating = 0,
                description = ""
            ),
            isEdit = true
        )
        dialog.onUpdate = { restaurant ->
            restaurantViewModel.addRestaurant(restaurant)
        }
        dialog.show(parentFragmentManager, "AddRestaurantDialogFragment")
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
            adapter.updateList(restaurants)

            adapter.setAdminState(sharedPreferences.getBoolean("is_admin", false))

            if (!isFirstLoad && restaurants.size > previousSize) {
                binding.recyclerViewLocal.post {
                    binding.recyclerViewLocal.smoothScrollToPosition(restaurants.size - 1)
                }
            }
            isFirstLoad = false
        }
    }
}