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

open class RestaurantFragment(private val isFavoritesScreen: Boolean = false) : Fragment(R.layout.fragment_restaurant) {

    private lateinit var binding: FragmentRestaurantBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: RestaurantAdapter

    private val restaurantViewModel: RestaurantViewModel by viewModels {
        RestaurantViewModelFactory(RestaurantRepository(RetrofitClient.restaurantApi))
    }

    private var isFirstLoad = true

    private val prefListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == "is_admin") {
            if (::sharedPreferences.isInitialized) {
                val isAdmin = sharedPreferences.getBoolean("is_admin", false)
                adapter.setAdminState(isAdmin)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantBinding.inflate(inflater, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener(prefListener)

        setupRecyclerView()
        observeViewModel()
        setupAddButton()

        val isAdmin = sharedPreferences.getBoolean("is_admin", false)

        binding.addButton.visibility = if (isAdmin && !isFavoritesScreen) View.VISIBLE else View.GONE

        adapter.setAdminState(isAdmin)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::sharedPreferences.isInitialized) {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(prefListener)
        }
    }

    open fun setupRecyclerView() {
        binding.recyclerViewLocal.layoutManager = LinearLayoutManager(requireContext())
        adapter = RestaurantAdapter(
            emptyList(),
            ::onDeleteRestaurant,
            ::onEditRestaurant,
            ::onFavoriteClick,
            restaurantViewModel.favoritesLiveData.value ?: emptySet()
        )
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
            restaurantViewModel.favoritesLiveData.observe(viewLifecycleOwner) { favorites ->
                adapter.updateList(restaurants, favorites)
            }
        }

        restaurantViewModel.getRestaurants()
        restaurantViewModel.getFavoriteRestaurants()
    }

    private fun updateRestaurantList(restaurants: List<Restaurant>) {
        if (adapter.restaurantList != restaurants) {
            val previousSize = adapter.restaurantList.size
            adapter.updateList(restaurants, restaurantViewModel.favoritesLiveData.value ?: emptySet())

            adapter.setAdminState(sharedPreferences.getBoolean("is_admin", false))

            if (!isFirstLoad && restaurants.size > previousSize) {
                binding.recyclerViewLocal.post {
                    binding.recyclerViewLocal.smoothScrollToPosition(restaurants.size - 1)
                }
            }
            isFirstLoad = false
        }
    }

    private fun onFavoriteClick(restaurantId: Long) {
        restaurantViewModel.toggleFavorite(restaurantId)
    }
}