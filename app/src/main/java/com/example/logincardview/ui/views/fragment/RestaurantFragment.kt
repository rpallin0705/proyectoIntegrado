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
import com.example.logincardview.ui.adapter.RestaurantAdapter
import com.example.logincardview.ui.modelview.RestaurantViewModel


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
        observeViewModel()

        bindingFragment.addButton.setOnClickListener {
            val dialog = RestaurantDialogFragmentCU()

            dialog.onUpdate = { restaurant ->
                restaurantViewModel.addRestaurant(restaurant)
            }

            dialog.show(parentFragmentManager, "AddRestaurantDialoFg")
        }

        return bindingFragment.root
    }

    private fun loadData() {
        restaurantViewModel.getRestaurants()
    }

    private fun initRecyclerView() {
        bindingFragment.recyclerViewLocal.layoutManager = LinearLayoutManager(requireContext())

        adapter = RestaurantAdapter(emptyList()) { position ->
            val restaurantName = adapter.restaurantList[position].name

            restaurantViewModel.deleteRestaurant(position)

            Toast.makeText(
                requireContext(),
                "Restaurante eliminado: $restaurantName",
                Toast.LENGTH_SHORT
            ).show()
        }

        bindingFragment.recyclerViewLocal.adapter = adapter
    }


    private fun observeViewModel() {
        restaurantViewModel.restaurantLiveData.observe(viewLifecycleOwner) { restaurants ->
            val previousSize = adapter.restaurantList.size

            adapter.restaurantList = restaurants
            adapter.notifyDataSetChanged()

            if (restaurants.size > previousSize) {
                bindingFragment.recyclerViewLocal.smoothScrollToPosition(restaurants.size - 1)
            }
        }

        loadData()
    }

}
