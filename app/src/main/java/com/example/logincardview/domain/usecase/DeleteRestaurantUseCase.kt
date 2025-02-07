package com.example.logincardview.domain.usecase

import com.example.logincardview.data.repository.RestaurantInMemoryRepository

class DeleteRestaurantUseCase(private val repository: RestaurantInMemoryRepository) {
    suspend operator fun invoke(restaurantId: Int) {
        repository.delete(restaurantId)
    }
}