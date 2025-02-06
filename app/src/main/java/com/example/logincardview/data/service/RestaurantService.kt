package com.example.logincardview.data.service

import com.example.logincardview.data.datasource.RestaurantDataSource
import com.example.logincardview.data.models.RestaurantDTO
import kotlinx.coroutines.delay

class RestaurantService {
    suspend fun getRestaurants(): List<RestaurantDTO> {
        val data = RestaurantDataSource().getRestaurants()
        return data
    }
}