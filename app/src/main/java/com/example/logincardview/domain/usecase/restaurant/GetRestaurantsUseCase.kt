package com.example.logincardview.domain.usecase.restaurant

import com.example.logincardview.domain.models.Restaurant
import com.example.logincardview.data.repository.RestaurantRepository

class GetRestaurantsUseCase(private val repository: RestaurantRepository) {
    suspend operator fun invoke(): List<Restaurant> {
        return repository.getAll()
    }
}