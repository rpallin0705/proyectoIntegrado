package com.example.logincardview.domain.usecase.restaurant

import com.example.logincardview.data.repository.RestaurantRepository

class DeleteRestaurantUseCase(private val repository: RestaurantRepository) {
    suspend operator fun invoke(restaurantId: Long) {
        repository.delete(restaurantId)
    }
}