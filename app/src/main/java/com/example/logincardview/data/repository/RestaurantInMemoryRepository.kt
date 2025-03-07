package com.example.logincardview.data.repository

import com.example.logincardview.data.models.RestaurantDTO
import com.example.logincardview.data.service.RestaurantService
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
                restaurantDTO.id,
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
            o.id,
            o.name,
            o.address,
            o.phone,
            o.rating,
            o.description
        )
        restaurantService.addRestaurant(newRestaurantDTO)
    }

    override suspend fun delete(id: Long): Boolean {
        restaurantService.deleteRestaurant(id.toInt())
        return true;
    }



    override suspend fun edit(oldRestaurant: Restaurant, newRestaurant: Restaurant) {
        val oldRestaurantDTO = RestaurantDTO(
            oldRestaurant.id,
            oldRestaurant.name, oldRestaurant.address, oldRestaurant.phone,
            oldRestaurant.rating, oldRestaurant.description
        )

        val newRestaurantDTO = RestaurantDTO(
            newRestaurant.id,
            newRestaurant.name, newRestaurant.address, newRestaurant.phone,
            newRestaurant.rating, newRestaurant.description
        )
        restaurantService.editRestaurant(oldRestaurantDTO, newRestaurantDTO)
    }
}
