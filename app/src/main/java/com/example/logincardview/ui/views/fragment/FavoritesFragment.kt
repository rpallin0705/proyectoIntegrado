package com.example.logincardview.ui.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logincardview.data.repository.RestaurantRepository
import com.example.logincardview.databinding.FragmentRestaurantBinding
import com.example.logincardview.network.RetrofitClient
import com.example.logincardview.ui.adapter.RestaurantAdapter
import com.example.logincardview.ui.modelview.RestaurantViewModel
import com.example.logincardview.ui.modelview.RestaurantViewModelFactory

class FavoritesFragment : RestaurantFragment(isFavoritesScreen = true) {

    private lateinit var binding: FragmentRestaurantBinding
    private lateinit var adapter: RestaurantAdapter
    private val restaurantViewModel: RestaurantViewModel by viewModels {
        RestaurantViewModelFactory(RestaurantRepository(RetrofitClient.restaurantApi))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        binding.addButton.visibility = View.GONE
        setupRecyclerView()
        observeViewModel()
        return binding.root
    }

    override fun setupRecyclerView() {
        binding.recyclerViewLocal.layoutManager = LinearLayoutManager(requireContext())
        adapter = RestaurantAdapter(
            emptyList(),
            onDeleteClick = {},
            onEditClick = {},
            onFavoriteClick = { restaurantId, restaurantName ->
                restaurantViewModel.toggleFavorite(restaurantId, restaurantName) { message ->
                    requireActivity().runOnUiThread {
                        showToast(message)
                    }
                }
            },
            favoriteRestaurants = emptySet()
        )
        binding.recyclerViewLocal.adapter = adapter
    }

    private fun observeViewModel() {
        restaurantViewModel.getRestaurants()
        restaurantViewModel.getFavoriteRestaurants()

        restaurantViewModel.restaurantLiveData.observe(viewLifecycleOwner) { restaurants ->
            val favoriteIds = restaurantViewModel.favoritesLiveData.value ?: emptySet()
            val favoriteRestaurants = restaurants.filter { it.id in favoriteIds }

            adapter.updateList(favoriteRestaurants, favoriteIds)
        }

        restaurantViewModel.favoritesLiveData.observe(viewLifecycleOwner) { favorites ->
            val restaurants = restaurantViewModel.restaurantLiveData.value ?: emptyList()
            val favoriteRestaurants = restaurants.filter { it.id in favorites }

            adapter.updateList(favoriteRestaurants, favorites)
        }
    }
}
