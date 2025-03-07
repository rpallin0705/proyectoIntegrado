package com.example.logincardview.domain.usecase.restaurant

import com.example.logincardview.data.repository.RestaurantInMemoryRepository

class DeleteRestaurantUseCase(private val repository: RestaurantInMemoryRepository) {
    suspend operator fun invoke(restaurantId: Long) {
        repository.delete(restaurantId)
    }
}