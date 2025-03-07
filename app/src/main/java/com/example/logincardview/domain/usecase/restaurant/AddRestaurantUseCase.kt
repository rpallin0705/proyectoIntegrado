package com.example.logincardview.domain.usecase.restaurant

import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.data.repository.RestaurantRepository

class AddRestaurantUseCase(private val repository: RestaurantRepository) {
    suspend operator fun invoke(restaurant: Restaurant) {
        repository.add(restaurant)
    }
}