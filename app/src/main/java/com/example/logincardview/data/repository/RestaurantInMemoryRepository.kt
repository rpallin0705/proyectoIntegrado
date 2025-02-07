package com.example.logincardview.data.repository

import RestaurantService
import com.example.logincardview.data.models.RestaurantDTO
import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.domain.repository.RepositoryInterface

/**
 * Clase que implementa la interfaz RepositoryInterface
 */
class RestaurantInMemoryRepository(
    private val restaurantService: RestaurantService = RestaurantService()
) : RepositoryInterface<Restaurant> {

    override suspend fun getAll(): List<Restaurant> {
        val restaurants = restaurantService.getRestaurants()
        return restaurants.map { restaurantDTO ->
            Restaurant(
                restaurantDTO.name,
                restaurantDTO.address,
                restaurantDTO.phone,
                restaurantDTO.rating,
                restaurantDTO.description
            )
        }
    }

    override suspend fun add(o: Restaurant) {
        val newRestaurantDTO = RestaurantDTO(
            o.name,
            o.address,
            o.phone,
            o.rating,
            o.description
        )
        restaurantService.addRestaurant(newRestaurantDTO)
    }

    override suspend fun delete(id: Int): Boolean {
        restaurantService.deleteRestaurant(id)
        return true;
    }
}
