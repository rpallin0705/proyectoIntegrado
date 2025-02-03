package com.example.logincardview.data.service

import com.example.logincardview.data.models.RestaurantDTO
import kotlinx.coroutines.delay

class RestaurantService {
    suspend fun getRestaurants(): List<RestaurantDTO> {
        delay(1000)
        return getRestaurants()
    }
}