package com.example.logincardview.data.repository

import com.example.logincardview.data.service.RestaurantService
import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.domain.repository.RepositoryInterface

/**
 * Clase que implementa la interfaz RepositoryInterface
 */
class RestaurantInMemoryRepository(
    private val restaurantService: RestaurantService = RestaurantService()
) : RepositoryInterface<Restaurant> {

    /**
     * Convierte los datos nativos a los datos del dominio de la aplicación
     */
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
}
