package com.example.logincardview.data.service

import com.example.logincardview.data.datasource.RestaurantDataSource
import com.example.logincardview.data.models.RestaurantDTO
import com.example.logincardview.domain.models.Restaurant

class RestaurantService {
    private val dataSource = RestaurantDataSource()

    suspend fun getRestaurants(): List<RestaurantDTO> {
        return dataSource.getRestaurants()
    }

    suspend fun addRestaurant(restaurant: RestaurantDTO) {
        dataSource.addRestaurant(restaurant)
    }

    suspend fun deleteRestaurant(restaurantId: Int) {
        dataSource.deleteRestaurant(restaurantId)
    }

    suspend fun editRestaurant(oldRestaurant: RestaurantDTO, newRestaurant: RestaurantDTO){
        dataSource.editRestaurant(oldRestaurant, newRestaurant)
    }
}
